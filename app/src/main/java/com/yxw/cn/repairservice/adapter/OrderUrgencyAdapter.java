package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.UrgencyBean;

import java.util.List;

/**
 * Created by CY on 2018/11/25
 */
public class OrderUrgencyAdapter extends BaseQuickAdapter<UrgencyBean, BaseViewHolder> {

    private int select=0;

    public void setSelect(int select) {
        this.select = select;
    }

    public OrderUrgencyAdapter(List<UrgencyBean> data) {
        super(R.layout.item_worker_abnormal, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UrgencyBean item) {
        helper.setBackgroundRes(R.id.name,select == helper.getLayoutPosition()?R.drawable.corner_stroke_red1:R.drawable.corner_stroke_gray1)
        .setTextColor(R.id.name,select!=helper.getLayoutPosition()?mContext.getResources().getColor(R.color.text_gray)
                :mContext.getResources().getColor(R.color.white))
        .setText(R.id.name,item.getName());
    }
}
