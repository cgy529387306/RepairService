package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class AccountCenterDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AccountCenterDetailsAdapter(List data) {
        super(R.layout.item_account_center_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }


}




