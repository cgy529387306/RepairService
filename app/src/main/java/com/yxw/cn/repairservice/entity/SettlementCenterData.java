package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class SettlementCenterData implements Serializable {

    /**
     * avatar : http://jxdj2.oss-cn-shenzhen.aliyuncs.com/fixuser/20190502/00410552166e09.jpeg
     * orderCount : 2
     * realName : 张三
     * totalMoney : 70
     * userId:
     */

    private String avatar;
    private String orderCount;
    private String realName;
    private String totalMoney;
    private String userId;

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOrderCount() {
        return orderCount == null ? "0" : orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getRealName() {
        return realName == null ? "" : realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTotalMoney() {
        return totalMoney == null ? "0" : totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
