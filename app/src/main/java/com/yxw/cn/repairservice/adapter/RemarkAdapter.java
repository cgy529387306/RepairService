package com.yxw.cn.repairservice.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.RemarkBean;

import java.util.List;

public class RemarkAdapter extends BaseQuickAdapter<RemarkBean, BaseViewHolder> {

    public RemarkAdapter(@Nullable List<RemarkBean> data) {
        super(R.layout.item_remark, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RemarkBean item) {
        helper.setText(R.id.tv_remark,(helper.getAdapterPosition()+1)+"."+item.getOrderContent());
    }

}
