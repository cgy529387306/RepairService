package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.ApplyItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        helper.setText(R.id.tv_real_name,item.getRealName());
        helper.setText(R.id.tv_apply_state,item.getServiceStatusName());
        helper.setText(R.id.tv_mobile,item.getMobile());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(item.getApplyTime());
        helper.setText(R.id.tv_apply_time,simpleDateFormat.format(date));
    }
}
