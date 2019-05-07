package com.yxw.cn.repairservice.entity;

public class EngineerInfo {

    /**
     * star : 3
     * userName : 18650480850
     */
    private int star;
    private String userName;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
