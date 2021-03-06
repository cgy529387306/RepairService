package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class EngineerInfo implements Serializable {


    /**
     * avatar : http://jxdj2.oss-cn-shenzhen.aliyuncs.com/fixuser/20190502/00410552166e09.jpeg
     * mobile : 18650480850
     * realName : 张三
     * star : 3
     */

    private String avatar;
    private String mobile;
    private String realName;
    private int star;
    private String userId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
