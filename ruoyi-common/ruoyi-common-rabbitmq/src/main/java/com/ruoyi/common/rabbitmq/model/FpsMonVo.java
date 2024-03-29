package com.ruoyi.common.rabbitmq.model;

import java.util.Date;

public class FpsMonVo {

    private String sysId;
    private String sysName;
    private String transId;
    private String transName;
    private String transStatus;
    private String additionMsg;
    private String remark1;



    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getAdditionMsg() {
        return additionMsg;
    }

    public void setAdditionMsg(String additionMsg) {
        this.additionMsg = additionMsg;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    @Override
    public String toString() {
        return "FpsMonVo{" +
                "  sysId='" + sysId + '\'' +
                ", sysName='" + sysName + '\'' +
                ", transId='" + transId + '\'' +
                ", transName='" + transName + '\'' +
                ", transStatus=" + transStatus +
                ", additionMsg='" + additionMsg + '\'' +
                ", remark1='" + remark1 + '\'' +
                '}';
    }
}
