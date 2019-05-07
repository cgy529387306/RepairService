package com.yxw.cn.repairservice.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.order.OrderDetailActivity;
import com.yxw.cn.repairservice.pop.ContactPop;
import com.yxw.cn.repairservice.pop.DeleteEngineerPop;
import com.yxw.cn.repairservice.util.ToastUtil;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MyEngineerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    boolean is_delete;
    DeleteEngineerPop mDeleteEngineerPop;
    public MyEngineerAdapter(List data) {
        super(R.layout.item_my_engineer, data);
    }

    public void deleteStatus(Boolean is_delete){
        this.is_delete = is_delete;

    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
        helper.setVisible(R.id.iv_delete,is_delete ? true:false);
        helper.setVisible(R.id.ratingbar,is_delete ? false:true);
        helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteEngineerPop==null){
                    mDeleteEngineerPop = new DeleteEngineerPop((Activity) mContext,item);
                }
                mDeleteEngineerPop.showPopupWindow(v);
            }
        });
    }


}




