package com.yxw.cn.repairservice.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.AccountCenterAdapter;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 结算中心
 */
public class AccountCenterActivity extends BaseActivity{

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    LinearLayout mLlyEdit;
    LinearLayout mLlyApply;
    TextView mTvEdit;
    TextView mTvApply;
    AccountCenterAdapter mAccountCenterAdapter;
    boolean is_edit = true;
    boolean is_delete = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算中心");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mAccountCenterAdapter = new AccountCenterAdapter(getList());
        mRecyclerView.setAdapter(mAccountCenterAdapter);
        mAccountCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(AccountCenterDetailsActivity.class);
            }
        });
    }
    private List<String> getList(){
        List<String> dataLsit = new ArrayList<>();
        dataLsit.add("陈秋梅");
        dataLsit.add("蔡桂有");
        return dataLsit;
    }

    @OnClick({})
    public void click(View view) {

    }
}
