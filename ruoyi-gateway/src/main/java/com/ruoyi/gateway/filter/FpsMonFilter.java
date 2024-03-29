package com.ruoyi.gateway.filter;

import com.ruoyi.common.rabbitmq.model.FpsMonVo;
import com.ruoyi.common.rabbitmq.service.RabbitmqService;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.TraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class FpsMonFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(FpsMonFilter.class);

    @Autowired
    private RabbitmqService rabbitmqService;

    private final String FPS_MON_REQ_TIME = "fps_mon_req_time";
    private final String FPS_MON_REQ_TRACEID = "fps_mon_traceid";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 解析请求，产生初始监控信息
        //ServerHttpRequest request = exchange.getRequest();

        log.info("在过滤器中检测到交易请求...");
        // 1. 将url、请求时间等信息存入mon
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();


        // 交易请求时间
        Date reqDate = new Date();
        long reqTime = reqDate.getTime();


        String traceId = rabbitmqService.startMonMessage(url, reqDate);
        log.info("traceId: {}", traceId);
        log.info("Trace ID: {},   request thread id: {}", traceId,Thread.currentThread().getId());


        //
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                log.info("response thread id: {}", Thread.currentThread().getId());

                // 请求和响应时异步非阻塞的，
                // 通过实验，只能通过以下方式从全局context获取traceid，直接使用tracer的话，会异常
                return Mono.subscriberContext().flatMap(context -> {
                    System.out.println("context: " + context.toString());
                    String traceId = "";
                    long reqTime = 0;
                    if (context.hasKey(FPS_MON_REQ_TRACEID)){
                        traceId = (String) context.get(FPS_MON_REQ_TRACEID);
                    }
                    if (context.hasKey(FPS_MON_REQ_TIME)){
                        reqTime = (long) context.get(FPS_MON_REQ_TIME);
                    }
                    // 结束时间
                    Date respDate = new Date();
                    long respTime = respDate.getTime();
                    long processTime = respTime-reqTime;


                    // 在这里使用traceId，例如记录日志
                    System.out.println("Trace ID: " + traceId);
                    log.info("Trace ID: {},  response thread id: {}", traceId, Thread.currentThread().getId());

                    if (traceId != null && traceId.length() > 0){
                        rabbitmqService.endMonMessage(traceId, respDate, processTime);
                    }
                    return super.writeWith(body);
                });

                // 将响应时间等信息存入mon
                //rabbitmqService.endMonMessage();
                //return super.writeWith(body);
            }
        };

        // 因为请求和响应是异步非阻塞的，因此使用context在请求和响应间传值
        return chain.filter(exchange.mutate().response(decoratedResponse).build())
                .contextWrite(context -> context.put(FPS_MON_REQ_TIME, reqTime))
                .contextWrite(context -> context.put(FPS_MON_REQ_TRACEID, traceId));
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
