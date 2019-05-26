package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class SettlementDetail implements Serializable {

    /**
     * amountAfterSettlement : null
     * amountBeforeSettlement : null
     * avatar : http://jxdj2.oss-cn-shenzhen.aliyuncs.com/fixuser/20190502/00410552166e09.jpeg
     * money : 60
     * orderId : 4
     * realName : 张三
     * settlementDate : 2019-05-17 10:35:41
     * settlementId : 4
     * userId : 7811945468413018112
     */

    private double amountAfterSettlement;
    private double amountBeforeSettlement;
    private String avatar;
    private String money;
    private String orderId;
    private String realName;
    private String settlementDate;
    private String settlementId;
    private String userId;

    public double getAmountAfterSettlement() {
        return amountAfterSettlement;
    }

    public void setAmountAfterSettlement(double amountAfterSettlement) {
        this.amountAfterSettlement = amountAfterSettlement;
    }

    public double getAmountBeforeSettlement() {
        return amountBeforeSettlement;
    }

    public void setAmountBeforeSettlement(double amountBeforeSettlement) {
        this.amountBeforeSettlement = amountBeforeSettlement;
    }

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMoney() {
        return money == null ? "0" : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderId() {
        return orderId == null ? "" : orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRealName() {
        return realName == null ? "" : realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSettlementDate() {
        return settlementDate == null ? "" : settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSettlementId() {
        return settlementId == null ? "" : settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
