package com.yxw.cn.repairservice.adapter;

import android.widget.ImageView;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.util.AppUtil;

import java.util.ArrayList;


/**
 * Created by necer on 2017/6/7.
 */
public class ChooseEngineerAdapter extends BaseQuickAdapter<EngineerInfo, BaseViewHolder> {

    public ChooseEngineerAdapter() {
        super(R.layout.item_choose_engineer, new ArrayList<>());
    }


    @Override
    protected void convert(BaseViewHolder helper, EngineerInfo item) {
        RatingBar ratingBar = helper.getView(R.id.ratingbar);
        ImageView mIvAvatar = helper.getView(R.id.iv_avatar);
        helper.setText(R.id.tv_name,item.getRealName());
        AppUtil.showPic(mContext, mIvAvatar, item.getAvatar());
        ratingBar.setRating(item.getStar());
    }


}




