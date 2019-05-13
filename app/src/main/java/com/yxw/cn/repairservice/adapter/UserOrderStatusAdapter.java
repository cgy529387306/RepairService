package com.yxw.cn.repairservice.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.OrderStatusLineBean;

import java.util.List;

/**
 * Created by CY on 2018/11/25
 */
public class UserOrderStatusAdapter extends BaseQuickAdapter<OrderStatusLineBean, BaseViewHolder> {

    private int size;

    public UserOrderStatusAdapter(List<OrderStatusLineBean> data) {
        super(R.layout.item_order_status, data);
        size = data.size();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusLineBean item) {
        helper.setText(R.id.tv_status, item.getDescription())
                .setText(R.id.tv_time, item.getTime())
                .setTextColor(R.id.tv_status,helper.getLayoutPosition()==size-1? Color.parseColor("#FF3431")
                        :Color.parseColor("#717171"))
                .setTextColor(R.id.tv_time,helper.getLayoutPosition()==size-1? Color.parseColor("#FF3431")
                        :Color.parseColor("#717171"))
                .setVisible(R.id.shu, helper.getLayoutPosition() != 0);
    }
}
