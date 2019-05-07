package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;

import java.util.List;

/**
 * Created by CY on 2018/11/25
 */
public class ApplyServiceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ApplyServiceAdapter(List<String> data) {
        super(R.layout.item_apply_service, data);
//        this.mOperateListener = orderOperateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
