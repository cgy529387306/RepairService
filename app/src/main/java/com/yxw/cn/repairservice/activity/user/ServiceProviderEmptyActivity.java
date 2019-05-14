package com.yxw.cn.repairservice.activity.user;


import android.view.View;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 隶属服务商
 */
public class ServiceProviderEmptyActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_service_provider_empty;
    }

    @Override
    public void initView() {
        titleBar.setTitle("隶属服务商");

    }

    @OnClick({R.id.btn_join})
    public void click(View view) {
        if (view.getId() == R.id.btn_join){
            startActivityFinish(JoinServiceProviderActivity.class);
        }
    }
}
