package com.yxw.cn.repairservice.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.ChooseEngineerAdapter;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.entity.LoginInfo;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择工程师
 */
public class ChooseEngineerActivity extends BaseActivity{

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    ChooseEngineerAdapter mChooseEngineerAdapter;
    private LoginInfo loginInfo;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_my_engineer;
    }

    @Override
    public void initView() {
        titleBar.setTitle("订单指派");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mChooseEngineerAdapter = new ChooseEngineerAdapter();
        mRecyclerView.setAdapter(mChooseEngineerAdapter);
        mChooseEngineerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("engineer",mChooseEngineerAdapter.getData().get(position));
                setResult(RESULT_OK,intent);
                finish();
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
        loginInfo = CurrentUser.getInstance();
        OkGo.<ResponseData<List<EngineerInfo>>>post(UrlConstant.CHILD_SERVICE+loginInfo.getBindingCode())
                .tag(this)
                .execute(new JsonCallback<ResponseData<List<EngineerInfo>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<EngineerInfo>> response) {
                        dismissLoading();
                        if (response!=null){
                            if (response.isSuccess() && response.getData()!=null){
                                mChooseEngineerAdapter.setNewData(response.getData());
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

}
