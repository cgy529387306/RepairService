package com.yxw.cn.repairservice.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.AccountCenterDetailsAdapter;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的工程师
 */
public class AccountCenterDetailsActivity extends BaseActivity{

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
    AccountCenterDetailsAdapter mAccountCenterDetailsAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_center_details;
    }

    @Override
    public void initView() {
        titleBar.setTitle("结算明细");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mAccountCenterDetailsAdapter = new AccountCenterDetailsAdapter(getList());
        mRecyclerView.setAdapter(mAccountCenterDetailsAdapter);
        //添加Header
        View header = LayoutInflater.from(this).inflate(R.layout.item_account_center_details_head, mRecyclerView, false);
//        mLlyEdit = header.findViewById(R.id.lly_edit);
//        mLlyApply = header.findViewById(R.id.lly_apply);
//        mTvEdit = header.findViewById(R.id.tv_edit);
//        mTvApply = header.findViewById(R.id.tv_apply);
        mAccountCenterDetailsAdapter.addHeaderView(header);
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
