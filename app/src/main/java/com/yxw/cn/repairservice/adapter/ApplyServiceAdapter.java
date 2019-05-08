package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.ApplyItem;

import java.util.ArrayList;

/**
 * Created by CY on 2018/11/25
 */
public class ApplyServiceAdapter extends BaseQuickAdapter<ApplyItem, BaseViewHolder> {


    public ApplyServiceAdapter() {
        super(R.layout.item_apply_service, new ArrayList<>());
//        this.mOperateListener = orderOperateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyItem item) {
        helper.setText(R.id.tv_name,item.getRealName());
    }
}
