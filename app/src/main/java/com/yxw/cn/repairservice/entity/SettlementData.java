package com.yxw.cn.repairservice.entity;

import java.util.ArrayList;
import java.util.List;

public class SettlementData extends BaseListResp{
    private List<SettlementBean> items;

    public List<SettlementBean> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<SettlementBean> items) {
        this.items = items;
    }
}
