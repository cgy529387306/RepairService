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
import com.yxw.cn.repairservice.entity.SettlementCenterData;
import com.yxw.cn.repairservice.entity.SettlementCenterDetail;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.List;

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
    AccountCenterDetailsAdapter mAccountCenterDetailsAdapter;
    private SettlementCenterData userInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center_details;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算明细");
        userInfo = (SettlementCenterData) getIntent().getSerializableExtra("userInfo");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mAccountCenterDetailsAdapter = new AccountCenterDetailsAdapter();
        mRecyclerView.setAdapter(mAccountCenterDetailsAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void getData() {
        super.getData();
        getDetails();
    }

    private void getDetails() {
        OkGo.<ResponseData<List<SettlementCenterDetail>>>post(UrlConstant.USER_SETTLEMENT_DETAIL+userInfo.getUserId())
                .tag(this)
                .execute(new JsonCallback<ResponseData<List<SettlementCenterDetail>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<SettlementCenterDetail>> response) {
                        dismissLoading();
                        mRefreshLayout.finishRefresh(true);
                        if (response!=null){
                            if (response.isSuccess() && response.getData()!=null){
                                addHeader();
                                mAccountCenterDetailsAdapter.setNewData(response.getData());
                                mAccountCenterDetailsAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                            }else{
                                toast(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<List<SettlementCenterDetail>>> response) {
                        super.onError(response);
                        dismissLoading();
                        mRefreshLayout.finishRefresh(false);
                    }
                });
    }

    @OnClick({})
    public void click(View view) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getDetails();
    }

    private void addHeader(){
        if (userInfo!=null){
            //添加Header
            View header = LayoutInflater.from(this).inflate(R.layout.item_account_center_details_head, mRecyclerView, false);
            mIvAvatar = header.findViewById(R.id.iv_avatar);
            mTvName = header.findViewById(R.id.tv_name);
            mAccountCenterDetailsAdapter.addHeaderView(header);
            AppUtil.showPic(AccountCenterDetailsActivity.this, mIvAvatar,userInfo.getAvatar());
            mTvName.setText(userInfo.getRealName());
        }
    }
}
