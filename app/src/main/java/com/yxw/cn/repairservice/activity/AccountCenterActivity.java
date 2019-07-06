package com.yxw.cn.repairservice.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.yxw.cn.repairservice.listerner.OnChooseMonthListener;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.util.MonthPickerUtil;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    @BindView(R.id.tv_order_count)
    TextView mTvOrderCount;
    @BindView(R.id.iv_sort_count)
    ImageView mIvCount;
    @BindView(R.id.iv_sort_money)
    ImageView mIvMoney;
    private AccountCenterAdapter mAdapter;
    private int mPage = 2;
    private boolean isNext = false;
    public static final int loadCount = 10;

    private String filterDate;
    private int filterType;
    private boolean isCountDesc = true;
    private boolean isMoneyDesc = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算中心");
        filterDate = Helper.date2String(new Date(),"yyyy-MM");
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
        requestMap.put("settlementDate", filterDate);
        requestMap.put("bindingCode", CurrentUser.getInstance().getBindingCode());
        if(filterType==1){
            requestMap.put("sorter", isCountDesc?"orderCount DESC":"orderCount ASC");
        }else if (filterType==2){
            requestMap.put("sorter", isMoneyDesc?"totalMoney DESC":"totalMoney ASC");
        }

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

    @OnClick({R.id.ll_month, R.id.ll_order_count,R.id.ll_total_money})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_month:
                MonthPickerUtil.showPicker(AccountCenterActivity.this,filterDate, new OnChooseMonthListener() {
                    @Override
                    public void getDateTime(Date date) {
                        filterDate = Helper.date2String(date,"yyyy-MM");
                        mTvMonth.setText(MonthPickerUtil.getMonthStr(date));
                        mRefreshLayout.autoRefresh();
                    }
                });
                break;
            case R.id.ll_order_count:
                initTabType(1);
                break;
            case R.id.ll_total_money:
                initTabType(2);
                break;
        }
    }

    private void initTabType(int type){
        filterType = type;
        mTvOrderCount.setTextColor(type==1?Color.parseColor("#E82B2D"):Color.parseColor("#666666"));
        mTvTotalMoney.setTextColor(type==2?Color.parseColor("#E82B2D"):Color.parseColor("#666666"));
        if (type == 1){
            isCountDesc = !isCountDesc;
            mIvCount.setImageResource(isCountDesc?R.drawable.icon_down:R.drawable.icon_up);
        }else if (type == 2){
            isMoneyDesc = !isMoneyDesc;
            mIvMoney.setImageResource(isMoneyDesc?R.drawable.icon_down:R.drawable.icon_up);
        }
        mRefreshLayout.autoRefresh();
    }
}
