package com.yxw.cn.repairservice.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.NoticeBean;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class HomeMsgAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {

    public HomeMsgAdapter(List data) {
        super(R.layout.item_home_msg, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, NoticeBean item) {
        helper.setText(R.id.tv_time,item.getCreateTime());
        helper.setText(R.id.tv_content,item.getContent());
        TextView tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(item.getTitle());
        tvTitle.setTextColor(item.getIsRead()==1 ? Color.parseColor("#999999"):Color.parseColor("#333333"));
    }
}




