package com.yxw.cn.repairservice.activity;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.MyEngineerAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.pop.DeleteEngineerPop;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的工程师
 */
public class MyEngineerActivity extends BaseActivity implements MyEngineerAdapter.OnDeleteEngineerOperateListener,DeleteEngineerPop.SelectListener ,OnRefreshListener, OnLoadMoreListener {

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
    MyEngineerAdapter mMyEngineerAdapter;
    boolean is_edit = true;
    boolean is_delete = false;
    private DeleteEngineerPop mDeleteEngineerPop;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_my_engineer;
    }

    @Override
    public void initView() {
        titleBar.setTitle("我的工程师");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mMyEngineerAdapter = new MyEngineerAdapter(this);
        mRecyclerView.setAdapter(mMyEngineerAdapter);
        //添加Header
        View header = LayoutInflater.from(this).inflate(R.layout.item_my_engineer_head, mRecyclerView, false);
        mLlyEdit = header.findViewById(R.id.lly_edit);
        mLlyApply = header.findViewById(R.id.lly_apply);
        mTvEdit = header.findViewById(R.id.tv_edit);
        mTvApply = header.findViewById(R.id.tv_apply);
        mMyEngineerAdapter.addHeaderView(header);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        mLlyEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_edit) {
                    is_edit = false;
                    is_delete = true;
                    mLlyEdit.setBackgroundResource(R.drawable.myengineer_border_red);
                    mTvEdit.setText("完成");
                    mTvEdit.setTextColor(getResources().getColor(R.color.white));
                }else {
                    is_edit = true;
                    is_delete = false;
                    mLlyEdit.setBackgroundResource(R.drawable.myengineer_border_gray);
                    mTvEdit.setText("编辑");
                    mTvEdit.setTextColor(getResources().getColor(R.color.black_my_engineer));
                }
                mMyEngineerAdapter.deleteStatus(is_delete);
                mMyEngineerAdapter.notifyDataSetChanged();
            }
        });
        mLlyApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ApplyServiceActivity.class);
            }
        });
    }

    @OnClick({})
    public void click(View view) {

    }

    @Override
    public void getData() {
        super.getData();
        getEngineerData();
    }
    private void getEngineerData() {
        OkGo.<ResponseData<List<EngineerInfo>>>post(UrlConstant.CHILD_SERVICE+CurrentUser.getInstance().getBindingCode())
                .tag(this)
                .execute(new JsonCallback<ResponseData<List<EngineerInfo>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<EngineerInfo>> response) {
                        mRefreshLayout.finishRefresh(true);
                        if (response!=null){
                            if (response.isSuccess() && response.getData()!=null){
                                mMyEngineerAdapter.setNewData(response.getData());
                            }else{
                                toast(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<List<EngineerInfo>>> response) {
                        super.onError(response);
                        dismissLoading();
                    }
                });
    }

    @Override
    public void onDeleteEngineer(EngineerInfo item) {
        if (mDeleteEngineerPop==null){
            mDeleteEngineerPop = new DeleteEngineerPop(MyEngineerActivity.this,item,this);
        }
        mDeleteEngineerPop.showPopupWindow(mRecyclerView);
    }

    @Override
    public void onComfirm(EngineerInfo mEngineerInfo) {
        showLoading();
        OkGo.<ResponseData<Object>>post(UrlConstant.DELETE_ENGINEER+mEngineerInfo.getUserId())
                .tag(this)
                .execute(new JsonCallback<ResponseData<Object>>() {

                    @Override
                    public void onSuccess(ResponseData<Object> response) {
                        dismissLoading();
                        if (response!=null){
                            if (response.isSuccess()){
                                toast("删除成功");
                                getEngineerData();
                            }else{
                                toast(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<Object>> response) {
                        super.onError(response);
                        dismissLoading();
                    }
                });
    }
    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_APPLY:
                getEngineerData();
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getEngineerData();
    }

}
