package com.yxw.cn.repairservice.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.pop.DeleteEngineerPop;
import com.yxw.cn.repairservice.util.AppUtil;

import java.util.ArrayList;


/**
 * Created by necer on 2017/6/7.
 */
public class MyEngineerAdapter extends BaseQuickAdapter<EngineerInfo, BaseViewHolder> {

    boolean is_delete;
    DeleteEngineerPop mDeleteEngineerPop;
    private OnDeleteEngineerOperateListener mOnDeleteEngineerOperateListener;
    public MyEngineerAdapter(OnDeleteEngineerOperateListener mOnDeleteEngineerOperateListener) {
        super(R.layout.item_my_engineer, new ArrayList<>());
        this.mOnDeleteEngineerOperateListener = mOnDeleteEngineerOperateListener;
    }

    public void deleteStatus(Boolean is_delete){
        this.is_delete = is_delete;
    }

    public interface OnDeleteEngineerOperateListener{
        void onDeleteEngineer(EngineerInfo item);//删除工程师
    }

    @Override
    protected void convert(BaseViewHolder helper, EngineerInfo item) {
        RatingBar ratingBar = helper.getView(R.id.ratingbar);
        ImageView mIvAvatar = helper.getView(R.id.iv_avatar);
        helper.setText(R.id.tv_name,item.getRealName());
        helper.setText(R.id.tv_phone,item.getMobile());
        AppUtil.showPic(mContext, mIvAvatar, item.getAvatar());
        helper.setVisible(R.id.iv_delete,is_delete);
        helper.setVisible(R.id.ratingbar,!is_delete);
        ratingBar.setRating(item.getStar());
        helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteEngineerOperateListener.onDeleteEngineer(item);
            }
        });
    }


}




