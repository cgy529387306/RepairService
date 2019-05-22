package com.yxw.cn.repairservice.activity;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.yxw.cn.repairservice.entity.SettlementCenterData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.List;
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
    private AccountCenterAdapter mAccountCenterAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算中心");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mAccountCenterAdapter = new AccountCenterAdapter();
        mRecyclerView.setAdapter(mAccountCenterAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        mAccountCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(AccountCenterDetailsActivity.class);
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        getSettlementCenterData();
    }


    private void getSettlementCenterData() {
        Map<String, Object> map = new HashMap<>();
        map.put("settlementDate", "2019-05");
        map.put("bindingCode", CurrentUser.getInstance().getBindingCode());
        OkGo.<ResponseData<List<SettlementCenterData>>>post(UrlConstant.USER_SETTLEMENT)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<List<SettlementCenterData>>>() {
                    @Override
                    public void onSuccess(ResponseData<List<SettlementCenterData>> response) {
                        mRefreshLayout.finishRefresh(true);
                        if (response!=null && response.getData()!=null) {
                            if (response.isSuccess()) {
                                    mAccountCenterAdapter.setNewData(response.getData());
                            } else {
                                toast(response.getMsg());
                            }
                        }
                    }
                    @Override
                    public void onError(Response<ResponseData<List<SettlementCenterData>>> response) {
                        super.onError(response);
                        mRefreshLayout.finishRefresh(false);
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getSettlementCenterData();
    }
}
