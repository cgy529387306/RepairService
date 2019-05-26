package com.yxw.cn.repairservice.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.SettlementBean;
import com.yxw.cn.repairservice.util.AppUtil;

import java.util.ArrayList;


/**
 * Created by necer on 2017/6/7.
 */
public class AccountCenterAdapter extends BaseQuickAdapter<SettlementBean, BaseViewHolder> {

    public AccountCenterAdapter() {
        super(R.layout.item_account_center, new ArrayList<>());
    }
    @Override
    protected void convert(BaseViewHolder helper, SettlementBean item) {
        ImageView mIvAvatar = helper.getView(R.id.iv_avatar);
        AppUtil.showPic(mContext, mIvAvatar, item.getAvatar());
        helper.setText(R.id.tv_name,item.getRealName());
        helper.setText(R.id.tv_order_count,item.getOrderCount());
        helper.setText(R.id.tv_total_money, String.valueOf(item.getTotalMoney()));
    }


}




