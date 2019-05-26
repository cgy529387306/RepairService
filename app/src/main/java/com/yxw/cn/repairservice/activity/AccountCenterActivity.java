package com.yxw.cn.repairservice.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.AccountCenterAdapter;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.entity.SettlementData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 结算中心
 */
public class AccountCenterActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_month)
    TextView mTvMonth;
    private AccountCenterAdapter mAdapter;
    private int mPage = 2;
    private boolean isNext = false;
    public static final int loadCount = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算中心");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mAdapter = new AccountCenterAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle webBundle = new Bundle();
                webBundle.putSerializable("userInfo",mAdapter.getData().get(position));
                startActivity(AccountCenterDetailsActivity.class,webBundle);
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        getSettlementCenterData(1);
    }


    private void getSettlementCenterData(int p) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("settlementDate", "2019-05");
        requestMap.put("bindingCode", CurrentUser.getInstance().getBindingCode());

        Map<String, Object> map = new HashMap<>();
        map.put("filter", requestMap);
        map.put("pageIndex", p);
        map.put("pageSize", loadCount);
        OkGo.<ResponseData<SettlementData>>post(UrlConstant.USER_SETTLEMENT)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<SettlementData>>() {
                    @Override
                    public void onSuccess(ResponseData<SettlementData> response) {
                        if (response!=null){
                            if (response.isSuccess() && response.getData()!=null) {
                                if (p == 1) {
                                    mPage = 2;
                                    mAdapter.setNewData(response.getData().getItems());
                                    mRefreshLayout.finishRefresh();
                                } else {
                                    mAdapter.addData(response.getData().getItems());
                                    isNext = response.getData().isHasNext();
                                    if (isNext) {
                                        mPage++;
                                        mRefreshLayout.finishLoadMore();
                                    } else {
                                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                                    }
                                }
                                mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                            } else {
                                toast(response.getMsg());
                                if (p == 1) {
                                    mRefreshLayout.finishRefresh(false);
                                } else {
                                    mRefreshLayout.finishLoadMore(false);
                                }
                            }
                        }
                    }
                    @Override
                    public void onError(Response<ResponseData<SettlementData>> response) {
                        super.onError(response);
                        if (p == 1) {
                            mRefreshLayout.finishRefresh(false);
                        } else {
                            mRefreshLayout.finishLoadMore(false);
                        }
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getSettlementCenterData(mPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getSettlementCenterData(1);
    }
}
