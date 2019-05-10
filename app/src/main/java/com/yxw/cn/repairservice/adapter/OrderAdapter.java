package com.yxw.cn.repairservice.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.util.AppUtil;

import java.util.List;

/**
 * Created by CY on 2018/11/25
 */
public class OrderAdapter extends BaseQuickAdapter<OrderItem, BaseViewHolder> {

    private OnOrderOperateListener mOperateListener;

    public interface OnOrderOperateListener{
        void onOrderTaking(OrderItem orderItem);//接单
        void onAbnormal(OrderItem orderItem);//异常反馈
        void onContact(OrderItem orderItem);//联系用户
        void onTurnReservation(OrderItem orderItem);//改约
        void onSign(OrderItem orderItem);//签到
        void onFinish(OrderItem orderItem);//服务完成
        void onView(OrderItem orderItem);//查看
    }

    public OrderAdapter(List<OrderItem> data,OnOrderOperateListener orderOperateListener) {
        super(R.layout.item_worker_ordered, data);
        this.mOperateListener = orderOperateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItem item) {
        TextView tvOperate2 = helper.getView(R.id.tv_operate2);
        helper.setText(R.id.tv_ordre_name, item.getName())
                .setText(R.id.tv_order_no,item.getOrderSn())
                .setText(R.id.tv_order_time, item.getBookingStartTime() + " " + item.getBookingEndTime())
                .setText(R.id.tv_order_address, item.getAddress())
                .setText(R.id.tv_order_content, item.getFixDesc())
                .setText(R.id.tv_price,String.valueOf(item.getTotalPrice()));
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onOrderTaking(item);
                }
            });
    }
}
