package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class OperateResult implements Serializable {

    /**
     * acceptId : 7818417497285132288
     * orderStatus : 40
     */
    private String acceptId;
    private String serviceId;
    private int orderStatus;

    public String getAcceptId() {
        return acceptId == null ? "" : acceptId;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getServiceId() {
        return serviceId == null ? "" : serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
