package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.rabbitmq.model.FpsMonVo;
import com.ruoyi.common.rabbitmq.service.RabbitmqService;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.model.TestTraceIdVo;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.domain.FpsMon;
import com.ruoyi.system.service.IFpsMonService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * fpsmonitorController
 * 
 * @author sunbin
 * @date 2024-03-29
 */
@RestController
@RequestMapping("/mon")
public class FpsMonController extends BaseController
{
    @Autowired
    private IFpsMonService fpsMonService;


    @Autowired
    private RemoteFileService remoteFileService;

    @Autowired
    private RabbitmqService rabbitmqService;

    /**
     * 查询fpsmonitor列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FpsMon fpsMon)
    {
        startPage();
        List<FpsMon> list = fpsMonService.selectFpsMonList(fpsMon);
        return getDataTable(list);
    }

    /**
     * 导出fpsmonitor列表
     */
    @Log(title = "fpsmonitor", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FpsMon fpsMon)
    {
        List<FpsMon> list = fpsMonService.selectFpsMonList(fpsMon);
        ExcelUtil<FpsMon> util = new ExcelUtil<FpsMon>(FpsMon.class);
        util.exportExcel(response, list, "fpsmonitor数据");
    }

    /**
     * 获取fpsmonitor详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(fpsMonService.selectFpsMonById(id));
    }

    /**
     * 新增fpsmonitor
     */
    @Log(title = "fpsmonitor", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FpsMon fpsMon)
    {
        return toAjax(fpsMonService.insertFpsMon(fpsMon));
    }

    /**
     * 修改fpsmonitor
     */
    @Log(title = "fpsmonitor", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FpsMon fpsMon)
    {
        return toAjax(fpsMonService.updateFpsMon(fpsMon));
    }

    /**
     * 删除fpsmonitor
     */
    @Log(title = "fpsmonitor", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(fpsMonService.deleteFpsMonByIds(ids));
    }


    @PostMapping("/test11")
    public AjaxResult test11(){
        logger.info("模拟发起的交易");
        logger.info("模拟存放监控信息");
        FpsMonVo fpsMonVo = new FpsMonVo();
        fpsMonVo.setSysId("house");
        fpsMonVo.setSysName("预售房资金监管业务");
        fpsMonVo.setTransId("9001");
        fpsMonVo.setTransName("济宁预售房签约交易");
        rabbitmqService.putMonMessage(fpsMonVo);
        logger.info("模拟存放监控信息完成：{}", fpsMonVo.toString());

        logger.info("开始模拟记账");
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        logger.info("设置服务间跟踪id", uuid);
        TestTraceIdVo traceIdVo = new TestTraceIdVo();
        traceIdVo.setTest_traceid(uuid);
        remoteFileService.test_traceid(traceIdVo);
        logger.info("结束模拟记账");

        logger.info("更新监控信息状态");
        FpsMonVo fpsMonVo1 = new FpsMonVo();
        if (new Random().nextInt()/2 == 0) {
            fpsMonVo1.setTransStatus("0");
        }else {
            fpsMonVo1.setTransStatus("1");
        }
        rabbitmqService.putMonMessage(fpsMonVo1);
        logger.info("更新监控信息状态完成：{}", fpsMonVo1.getTransStatus());

        return success();
    }

    @PostMapping("/test12")
    public AjaxResult test12(){
        FpsMonVo fpsMonVo = new FpsMonVo();
        fpsMonVo.setSysId("agent");
        fpsMonVo.setSysName("代缴水电费");
        fpsMonVo.setTransId("1001");
        fpsMonVo.setTransName("淄博缴费");
        fpsMonVo.setTransStatus("0");
        fpsMonVo.setAdditionMsg("{}");
        rabbitmqService.putMonMessage(fpsMonVo);

        return success();
    }
}
