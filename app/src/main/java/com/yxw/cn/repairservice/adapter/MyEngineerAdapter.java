package com.yxw.cn.repairservice.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.order.OrderDetailActivity;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.pop.ContactPop;
import com.yxw.cn.repairservice.pop.DeleteEngineerPop;
import com.yxw.cn.repairservice.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MyEngineerAdapter extends BaseQuickAdapter<EngineerInfo, BaseViewHolder> {

    boolean is_delete;
    DeleteEngineerPop mDeleteEngineerPop;
    public MyEngineerAdapter() {
        super(R.layout.item_my_engineer, new ArrayList<>());
    }

    public void deleteStatus(Boolean is_delete){
        this.is_delete = is_delete;
    }


    @Override
    protected void convert(BaseViewHolder helper, EngineerInfo item) {
        RatingBar ratingBar = helper.getView(R.id.ratingbar);
        helper.setText(R.id.tv_name,item.getUserName());
        helper.setVisible(R.id.iv_delete,is_delete ? true:false);
        helper.setVisible(R.id.ratingbar,is_delete ? false:true);
        ratingBar.setStarCount(item.getStar());
        helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteEngineerPop==null){
                    mDeleteEngineerPop = new DeleteEngineerPop((Activity) mContext,item.getUserName());
                }
                mDeleteEngineerPop.showPopupWindow(v);
            }
        });
    }


}




