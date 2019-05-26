package com.yxw.cn.repairservice.entity;

import java.util.ArrayList;
import java.util.List;

public class SettlementDetailData extends BaseListResp{
    private List<SettlementDetail> items;

    public List<SettlementDetail> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<SettlementDetail> items) {
        this.items = items;
    }
}
