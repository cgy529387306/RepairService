package com.yxw.cn.repairservice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MyEngineerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    boolean is_delete;
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
    }


}




