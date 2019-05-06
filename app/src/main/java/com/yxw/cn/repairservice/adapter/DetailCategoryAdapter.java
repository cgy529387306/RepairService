package com.yxw.cn.repairservice.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.entity.CategorySub;

import java.util.List;

import com.yxw.cn.repairservice.util.EventBusUtil;

public class DetailCategoryAdapter extends BaseQuickAdapter<CategorySub, BaseViewHolder> {

    private String selectId;

    public DetailCategoryAdapter(String selectId, @Nullable List<CategorySub> data) {
        super(R.layout.item_detail_category,data);
        this.selectId = selectId;
    }

    @Override
    protected void convert(BaseViewHolder helper, CategorySub item) {
        helper.setText(R.id.tv_title,item.getName());
        RecyclerView rv= helper.getView(R.id.rv_sub);
        rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        DetailCategorySubAdapter adapter =new DetailCategorySubAdapter(selectId, item.getSub());
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBusUtil.post(MessageConstant.CHOOSE_CATEGORY_SERVICE, item.getSub().get(position));
            }
        });
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }
}
