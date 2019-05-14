package com.yxw.cn.repairservice.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.Helper;

import java.util.List;

/**
 * Created by CY on 2018/11/25
 */
public class OrderAdapter extends BaseQuickAdapter<OrderItem, BaseViewHolder> {

    private OnOrderOperateListener mOperateListener;

    public interface OnOrderOperateListener{
        void onOrderCancel(OrderItem orderItem);//取消接单
        void onOrderAppoint(OrderItem orderItem);//订单指派
        void onOrderTaking(OrderItem orderItem);//接单
        void onAbnormal(OrderItem orderItem, int type);//异常反馈
        void onContact(OrderItem orderItem);//联系用户
        void onTurnReservation(OrderItem orderItem);//改约
        void onSign(OrderItem orderItem);//签到
        void onFinish(OrderItem orderItem);//服务完成
        void onView(OrderItem orderItem);//查看
    }

    public OrderAdapter(List<OrderItem> data, OnOrderOperateListener orderOperateListener) {
        super(R.layout.item_worker_ordered, data);
        this.mOperateListener = orderOperateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItem item) {
        TextView tvOperate0 = helper.getView(R.id.tv_operate0);
        TextView tvOperate1 = helper.getView(R.id.tv_operate1);
        TextView tvOperate2 = helper.getView(R.id.tv_operate2);
        helper.setText(R.id.tv_ordre_name, item.getCategoryCName())
                .setText(R.id.tv_order_no,item.getOrderSn())
                .setText(R.id.tv_order_time, item.getBookingStartTime() + " " + item.getBookingEndTime())
                .setText(R.id.tv_order_address, item.getAddress())
                .setText(R.id.tv_order_content, item.getFixDesc())
                .setText(R.id.tv_order_state, AppUtil.getOrderStatus(item.getOrderStatus()))
                .setText(R.id.tv_price,String.valueOf(item.getTotalPrice()));
        int orderStatus = item.getOrderStatus();
        boolean isShowOperate = orderStatus<40 || (Helper.isNotEmpty(item.getOperaterId()) && item.getOperaterId().equals(CurrentUser.getInstance().getUserId()));
        helper.getView(R.id.lly_operate).setVisibility(isShowOperate?View.VISIBLE:View.GONE);

        if (orderStatus<=20){
            //待接单
            tvOperate0.setVisibility(View.GONE);
            tvOperate1.setVisibility(View.GONE);
            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("我要接单");
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onOrderTaking(item);
                }
            });
        }else if (orderStatus<=30){
            //待分派
            tvOperate0.setVisibility(View.GONE);
            tvOperate1.setVisibility(View.VISIBLE);
            tvOperate1.setText("申请退单");
            tvOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onOrderCancel(item);
                }
            });

            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("指派工程师");
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onOrderAppoint(item);
                }
            });
        }else if (orderStatus<=40){
            //待预约
            tvOperate0.setVisibility(View.GONE);
            tvOperate1.setVisibility(View.VISIBLE);
            tvOperate1.setText("异常反馈");
            tvOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onAbnormal(item,0);
                }
            });

            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("联系用户");
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onContact(item);
                }
            });
        }else if (orderStatus<=55){
            //待上门
            tvOperate0.setVisibility(View.VISIBLE);
            tvOperate0.setText("改约");
            tvOperate0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onTurnReservation(item);
                }
            });

            tvOperate1.setVisibility(View.VISIBLE);
            tvOperate1.setText("异常反馈");
            tvOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onAbnormal(item,1);
                }
            });

            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("签到");
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onSign(item);
                }
            });
        }else if (orderStatus<90){
            //待完成
            tvOperate0.setVisibility(View.GONE);
            tvOperate1.setVisibility(View.GONE);
            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("服务完成");
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onFinish(item);
                }
            });
        }else{
            //已完成
            tvOperate0.setVisibility(View.GONE);
            tvOperate1.setVisibility(View.GONE);
            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("查看");
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onView(item);
                }
            });
        }
    }
}
