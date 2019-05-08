package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class ApplyItem implements Serializable {


    /**
     * applyId : 7814293018608472064
     * applyTime : 1557282851000
     * bindingCode : 123212
     * mobile : 15394414300
     * realName : 阿伟
     * serviceStatus : 1
     * serviceStatusName : 通过
     * userId : 7812231520260390912
     */

    private String applyId;
    private long applyTime;
    private String bindingCode;
    private String mobile;
    private String realName;
    private int serviceStatus;
    private String serviceStatusName;
    private String userId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public String getBindingCode() {
        return bindingCode;
    }

    public void setBindingCode(String bindingCode) {
        this.bindingCode = bindingCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceStatusName() {
        return serviceStatusName;
    }

    public void setServiceStatusName(String serviceStatusName) {
        this.serviceStatusName = serviceStatusName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
