package com.yxw.cn.repairservice.activity;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.AccountCenterDetailsAdapter;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.entity.SettlementBean;
import com.yxw.cn.repairservice.entity.SettlementDetailData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的工程师
 */
public class AccountCenterDetailsActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    CircleImageView mIvAvatar;
    TextView mTvName;
    AccountCenterDetailsAdapter mAdapter;
    private SettlementBean userInfo;
    private int mPage = 2;
    private boolean isNext = false;
    public static final int loadCount = 10;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center_details;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算明细");
        userInfo = (SettlementBean) getIntent().getSerializableExtra("userInfo");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mAdapter = new AccountCenterDetailsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        addHeader();
    }

    @Override
    public void getData() {
        super.getData();
        getDetails(1);
    }

    private void getDetails(int p) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userInfo.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("filter", requestMap);
        map.put("pageIndex", p);
        map.put("pageSize", loadCount);
        OkGo.<ResponseData<SettlementDetailData>>post(UrlConstant.USER_SETTLEMENT_DETAIL)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<SettlementDetailData>>() {
                    @Override
                    public void onSuccess(ResponseData<SettlementDetailData> response) {
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
                    public void onError(Response<ResponseData<SettlementDetailData>> response) {
                        super.onError(response);
                        if (p == 1) {
                            mRefreshLayout.finishRefresh(false);
                        } else {
                            mRefreshLayout.finishLoadMore(false);
                        }
                    }
                });
    }

    @OnClick({})
    public void click(View view) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getDetails(mPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getDetails(1);
    }

    private void addHeader(){
        if (userInfo!=null){
            //添加Header
            View header = LayoutInflater.from(this).inflate(R.layout.item_account_center_details_head, mRecyclerView, false);
            mIvAvatar = header.findViewById(R.id.iv_avatar);
            mTvName = header.findViewById(R.id.tv_name);
            mAdapter.addHeaderView(header);
            AppUtil.showPic(AccountCenterDetailsActivity.this, mIvAvatar,userInfo.getAvatar());
            mTvName.setText(userInfo.getRealName());
        }
    }
}
