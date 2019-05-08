package com.yxw.cn.repairservice.entity;

import java.util.ArrayList;
import java.util.List;

public class ApplyListData extends BaseListResp{

    private List<ApplyItem> items;

    public List<ApplyItem> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<ApplyItem> items) {
        this.items = items;
    }
}
