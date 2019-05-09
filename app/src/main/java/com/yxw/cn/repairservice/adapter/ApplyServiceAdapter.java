package com.yxw.cn.repairservice.adapter;

import android.view.View;

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

    OnApplyServiceOperateListener mOperateListener;
    public ApplyServiceAdapter( OnApplyServiceOperateListener mOperateListener) {
        super(R.layout.item_apply_service, new ArrayList<>());
        this.mOperateListener = mOperateListener;
    }
    public interface OnApplyServiceOperateListener {
        void onApplyServiceAgree(ApplyItem applyItem);//同意

        void onApplyServiceRefuse(ApplyItem applyItem);//拒绝
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyItem item) {
        helper.setText(R.id.tv_name,item.getRealName());
        helper.setText(R.id.tv_real_name,item.getRealName());
        helper.setText(R.id.tv_apply_state,item.getServiceStatusName());
        helper.setText(R.id.tv_mobile,item.getMobile());
        if (item.getServiceStatus() == 0){
            helper.setVisible(R.id.lly_operate,true);
        }else {
            helper.setVisible(R.id.lly_operate,false);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(item.getApplyTime());
        helper.setText(R.id.tv_apply_time,simpleDateFormat.format(date));
        helper.setOnClickListener(R.id.tv_refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperateListener.onApplyServiceRefuse(item);

            }
        });
        helper.setOnClickListener(R.id.tv_agree, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperateListener.onApplyServiceAgree(item);
            }
        });
    }
}
