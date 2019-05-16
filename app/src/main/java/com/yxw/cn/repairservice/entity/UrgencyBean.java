package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class UrgencyBean implements Serializable{

    /**
     * createId : null
     * createTime : null
     * dictId : 13
     * mark : URGENCY
     * markName : 紧急程度
     * name : 一般
     * open : null
     * parentId : null
     * remark :
     * sort : null
     * type : null
     * updateId : null
     * updateTime : null
     * value : null
     */

    private String dictId;
    private String mark;
    private String markName;
    private String name;
    private String remark;

    public String getDictId() {
        return dictId == null ? "" : dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getMark() {
        return mark == null ? "" : mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMarkName() {
        return markName == null ? "" : markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
