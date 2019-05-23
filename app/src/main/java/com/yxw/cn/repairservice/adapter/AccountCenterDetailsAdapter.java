package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.SettlementCenterDetail;

import java.util.ArrayList;


/**
 * Created by necer on 2017/6/7.
 */
public class AccountCenterDetailsAdapter extends BaseQuickAdapter<SettlementCenterDetail, BaseViewHolder> {

    public AccountCenterDetailsAdapter() {
        super(R.layout.item_account_center_details, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, SettlementCenterDetail item) {
        helper.setText(R.id.tv_name,item.getRealName());
        helper.setText(R.id.tv_date,item.getSettlementDate());
    }


}




