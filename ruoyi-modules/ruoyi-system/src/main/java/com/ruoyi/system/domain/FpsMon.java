package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * fpsmonitor对象 fps_mon
 * 
 * @author sunbin
 * @date 2024-03-29
 */
public class FpsMon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一索引 */
    private String id;

    /** 全局跟踪id */
    @Excel(name = "全局跟踪id")
    private String traceid;

    /** 请求时间 */
    private String requestTimestamp;

    /** 响应时间 */
    private String responseTimestamp;

    /** 处理时间，以毫秒为单位 */
    @Excel(name = "处理时间，以毫秒为单位")
    private String processTime;

    /** 请求日期 */
    @Excel(name = "请求日期")
    private String requestDate;

    /** 请求时间 */
    @Excel(name = "请求时间")
    private String requestTime;

    /** 响应日期 */
    @Excel(name = "响应日期")
    private String responseDate;

    /** 响应时间 */
    @Excel(name = "响应时间")
    private String responseTime;

    /** 请求url */
    @Excel(name = "请求url")
    private String url;

    /** 系统标识 */
    @Excel(name = "系统标识")
    private String sysId;

    /** 系统名称 */
    @Excel(name = "系统名称")
    private String sysName;

    /** 交易标识 */
    @Excel(name = "交易标识")
    private String transId;

    /** 交易名称 */
    @Excel(name = "交易名称")
    private String transName;

    /** 交易状态，0-成功，其他-失败 */
    @Excel(name = "交易状态，0-成功，其他-失败")
    private String transStatus;

    /** 附属信息，存储各业务特别关注的业务信息，以json格式存储 */
    @Excel(name = "附属信息，存储各业务特别关注的业务信息，以json格式存储")
    private String additionMsg;

    /** 备注 */
    @Excel(name = "备注")
    private String remark1;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTraceid(String traceid) 
    {
        this.traceid = traceid;
    }

    public String getTraceid() 
    {
        return traceid;
    }
    public void setRequestTimestamp(String requestTimestamp) 
    {
        this.requestTimestamp = requestTimestamp;
    }

    public String getRequestTimestamp() 
    {
        return requestTimestamp;
    }
    public void setResponseTimestamp(String responseTimestamp) 
    {
        this.responseTimestamp = responseTimestamp;
    }

    public String getResponseTimestamp() 
    {
        return responseTimestamp;
    }
    public void setProcessTime(String processTime) 
    {
        this.processTime = processTime;
    }

    public String getProcessTime() 
    {
        return processTime;
    }
    public void setRequestDate(String requestDate) 
    {
        this.requestDate = requestDate;
    }

    public String getRequestDate() 
    {
        return requestDate;
    }
    public void setRequestTime(String requestTime) 
    {
        this.requestTime = requestTime;
    }

    public String getRequestTime() 
    {
        return requestTime;
    }
    public void setResponseDate(String responseDate) 
    {
        this.responseDate = responseDate;
    }

    public String getResponseDate() 
    {
        return responseDate;
    }
    public void setResponseTime(String responseTime) 
    {
        this.responseTime = responseTime;
    }

    public String getResponseTime() 
    {
        return responseTime;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setSysId(String sysId) 
    {
        this.sysId = sysId;
    }

    public String getSysId() 
    {
        return sysId;
    }
    public void setSysName(String sysName) 
    {
        this.sysName = sysName;
    }

    public String getSysName() 
    {
        return sysName;
    }
    public void setTransId(String transId) 
    {
        this.transId = transId;
    }

    public String getTransId() 
    {
        return transId;
    }
    public void setTransName(String transName) 
    {
        this.transName = transName;
    }

    public String getTransName() 
    {
        return transName;
    }
    public void setTransStatus(String transStatus) 
    {
        this.transStatus = transStatus;
    }

    public String getTransStatus() 
    {
        return transStatus;
    }
    public void setAdditionMsg(String additionMsg) 
    {
        this.additionMsg = additionMsg;
    }

    public String getAdditionMsg() 
    {
        return additionMsg;
    }
    public void setRemark1(String remark1) 
    {
        this.remark1 = remark1;
    }

    public String getRemark1() 
    {
        return remark1;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("traceid", getTraceid())
            .append("requestTimestamp", getRequestTimestamp())
            .append("responseTimestamp", getResponseTimestamp())
            .append("processTime", getProcessTime())
            .append("requestDate", getRequestDate())
            .append("requestTime", getRequestTime())
            .append("responseDate", getResponseDate())
            .append("responseTime", getResponseTime())
            .append("url", getUrl())
            .append("sysId", getSysId())
            .append("sysName", getSysName())
            .append("transId", getTransId())
            .append("transName", getTransName())
            .append("transStatus", getTransStatus())
            .append("additionMsg", getAdditionMsg())
            .append("remark1", getRemark1())
            .toString();
    }
}
