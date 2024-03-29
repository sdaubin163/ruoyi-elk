package com.ruoyi.common.rabbitmq.service;

import com.ruoyi.common.rabbitmq.model.FpsMonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.TraceContext;
import org.springframework.stereotype.Component;
import org.springframework.cloud.sleuth.Tracer;
import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Component
public class RabbitmqService {

    @Resource(name = "fpsRabbitmqTemplate")
    private RabbitTemplate rabbitTemplate;

    @Value("${mon.queue}")
    private String monQueue;
    @Autowired
    private Tracer tracer;

    private static final Logger log = LoggerFactory.getLogger(RabbitmqService.class);


    class FpsMonObject{
        private String url;
        private Date requestTime;
        private Date responseTime;
        private String id;
        private String traceId;

        private long processTime;

        private FpsMonVo fpsMonVo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public FpsMonVo getFpsMonVo() {
            return fpsMonVo;
        }

        public void setFpsMonVo(FpsMonVo fpsMonVo) {
            this.fpsMonVo = fpsMonVo;
        }

        public Date getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(Date requestTime) {
            this.requestTime = requestTime;
        }

        public Date getResponseTime() {
            return responseTime;
        }

        public void setResponseTime(Date responseTime) {
            this.responseTime = responseTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getProcessTime() {
            return processTime;
        }

        public void setProcessTime(long processTime) {
            this.processTime = processTime;
        }
    }

    public String startMonMessage(String url, Date requestTime){
        log.info("start1");
        String traceId = tracer.currentSpan().context().traceId();
        FpsMonObject fpsMonObject = new FpsMonObject();
        fpsMonObject.setId(traceId);
        fpsMonObject.setTraceId(traceId);
        fpsMonObject.setUrl(url);
        fpsMonObject.setRequestTime(requestTime);

        // 指定队列名和消息
        rabbitTemplate.convertAndSend(monQueue, fpsMonObject);
        return traceId;
    }

    public void endMonMessage(String traceId, Date responseTime, long processTime){
        log.info("start12");

        FpsMonObject fpsMonObject = new FpsMonObject();
        fpsMonObject.setId(traceId);
        fpsMonObject.setTraceId(traceId);
        fpsMonObject.setResponseTime(responseTime);
        fpsMonObject.setProcessTime(processTime);

        // 指定队列名和消息
        rabbitTemplate.convertAndSend(monQueue, fpsMonObject);
    }

    public void putMonMessage(FpsMonVo monMessage) {
        log.info("putMonMessage start13");

        String traceId = tracer.currentSpan().context().traceId();

        FpsMonObject fpsMonObject = new FpsMonObject();
        fpsMonObject.setId(traceId);
        fpsMonObject.setTraceId(traceId);
        fpsMonObject.setFpsMonVo(monMessage);

        log.info("将监控信息存入mq的队列 {} 中：{}", monQueue, monMessage.toString());
        // 指定队列名和消息
        rabbitTemplate.convertAndSend(monQueue, fpsMonObject);

        log.info("监控信息存入mq的队列 {} 成功", monQueue);
    }
}