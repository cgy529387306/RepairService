package com.yxw.cn.repairservice.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.InServiceInfo;

import java.util.ArrayList;

/**
 * Created by CY on 2018/11/25
 */
public class InServiceAdapter extends BaseQuickAdapter<InServiceInfo, BaseViewHolder> {

    private OnInServiceOperateListener mOperateListener;
    private int type;

    public interface OnInServiceOperateListener{
        void onAppointEngineer(InServiceInfo item);//指派工程师
        void onApplyCancelOrder(InServiceInfo item);//申请退单
    }

    public InServiceAdapter(int type,OnInServiceOperateListener mOperateListener) {
        super(R.layout.item_in_service,new ArrayList<>());
        this.type = type;
        this.mOperateListener = mOperateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, InServiceInfo item) {
        TextView tvOperate0 = helper.getView(R.id.tv_operate0);
        TextView tvOperate1 = helper.getView(R.id.tv_operate1);
        TextView tvOperate2 = helper.getView(R.id.tv_operate2);
        TextView tvOrderState = helper.getView(R.id.tv_order_state);
        helper.setText(R.id.tv_ordre_name, "");

        if (type == 0){
            tvOperate0.setVisibility(View.GONE);
            tvOperate1.setVisibility(View.VISIBLE);
            tvOperate1.setText("申请退单");
            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("指派工程师");
            tvOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onApplyCancelOrder(item);
                }
            });
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOperateListener.onAppointEngineer(item);
                }
            });
        }else if(type == 1){
            tvOperate0.setVisibility(View.VISIBLE);
            tvOperate0.setText("改派");
            tvOperate1.setVisibility(View.VISIBLE);
            tvOperate1.setText("催单");
            tvOperate2.setVisibility(View.VISIBLE);
            tvOperate2.setText("预约");
            tvOperate0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            tvOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            tvOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }


    }
}
