package com.yxw.cn.repairservice.entity;

import java.util.ArrayList;
import java.util.List;

public class InServiceData extends BaseListResp{

    private List<InServiceInfo> items;

    public List<InServiceInfo> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<InServiceInfo> items) {
        this.items = items;
    }
}
