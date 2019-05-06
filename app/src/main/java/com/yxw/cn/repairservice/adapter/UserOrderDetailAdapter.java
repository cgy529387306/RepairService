package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.UserOrder;

import java.util.List;

import com.yxw.cn.repairservice.util.DoubleUtil;

/**
 * Created by CY on 2018/11/25
 */
public class UserOrderDetailAdapter extends BaseQuickAdapter<UserOrder.ListBean.OrderItemsBean, BaseViewHolder> {


    public UserOrderDetailAdapter(List<UserOrder.ListBean.OrderItemsBean> data) {
        super(R.layout.item_order_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserOrder.ListBean.OrderItemsBean item) {
        helper.setText(R.id.name, item.getDescription())
                .setText(R.id.price, "￥"+DoubleUtil.getTwoDecimal(item.getPrice()));
    }
}
