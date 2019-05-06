package com.yxw.cn.repairservice.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.order.OrderActivity;
import com.yxw.cn.repairservice.adapter.CategoryTitleAdapter;
import com.yxw.cn.repairservice.adapter.DetailCategoryAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.Category;
import com.yxw.cn.repairservice.entity.CategorySub;
import com.yxw.cn.repairservice.entity.CategorySubItem;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 找服务
 */
public class FindServiceFragment extends BaseFragment {

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.rv_sub)
    RecyclerView mRvSub;

    private CategoryTitleAdapter titleAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private List<CategorySub> categorySubList = new ArrayList<>();
    private DetailCategoryAdapter detailCategoryAdapter;
    private String selectedId = "";

    @Override
    protected int getLayout() {
        return R.layout.frg_find_service;
    }

    @Override
    protected void initView() {
        titlebar.setTitle("找服务");
        titlebar.setLeftVisible(false);
        mRvCategory.setLayoutManager(new LinearLayoutManager(mContext));
        titleAdapter = new CategoryTitleAdapter(categoryList);
        mRvCategory.setAdapter(titleAdapter);
        titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                titleAdapter.setSelectIndex(position);

                notifyDetailCategory(position);
            }
        });

        mRvSub.setLayoutManager(new LinearLayoutManager(mContext));
        detailCategoryAdapter = new DetailCategoryAdapter(selectedId, categorySubList);
        mRvSub.setAdapter(detailCategoryAdapter);
    }

    public void notifyDetailCategory(int index) {
        categorySubList.clear();
        if (categoryList.get(index).getChildList() != null && categoryList.get(index).getChildList().size() > 0) {
//            categorySubList.addAll(categoryList.get(index).getChildList());
        }
        detailCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void getData() {
        if (AppUtil.categoryItemList != null && AppUtil.categoryItemList.size() > 0) {
            categoryList.clear();
            categoryList.addAll(AppUtil.categoryItemList);
            titleAdapter.setSelectIndex(0);
            titleAdapter.notifyDataSetChanged();

            notifyDetailCategory(0);
        } else {
            OkGo.<ResponseData<List<Category>>>get(UrlConstant.GET_ALL_CATEGORY)
                    .tag(this)
                    .execute(new JsonCallback<ResponseData<List<Category>>>() {

                        @Override
                        public void onSuccess(ResponseData<List<Category>> response) {
                            AppUtil.categoryItemList.clear();
                            AppUtil.categoryItemList.addAll(response.getData());
                            categoryList.clear();
                            categoryList.addAll(response.getData());
                            titleAdapter.setSelectIndex(0);

                            notifyDetailCategory(0);
                        }

                        @Override
                        public void onError(Response<ResponseData<List<Category>>> response) {
                            super.onError(response);
                        }
                    });
        }
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.CHOOSE_CATEGORY_SERVICE:
                CategorySubItem categorySubItem = (CategorySubItem) event.getData();
                selectedId =categorySubItem.getId()+"";
                detailCategoryAdapter.setSelectId(selectedId);
                startActivity(OrderActivity.class,categorySubItem);
                break;
        }
    }
}
