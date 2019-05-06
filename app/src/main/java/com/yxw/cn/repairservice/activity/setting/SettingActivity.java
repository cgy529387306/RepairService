package com.yxw.cn.repairservice.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.user.UpdatePasswordActivity;
import com.yxw.cn.repairservice.activity.WebActivity;
import com.yxw.cn.repairservice.activity.user.LoginActivity;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import com.yxw.cn.repairservice.util.EventBusUtil;

/**
 * Created by CY on 2018/11/24
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_setting;
    }

    @Override
    public void initView() {
        titleBar.setTitle("设置");
        mTvVersion.setText(AppUtil.getVerName());
    }

    @OnClick({R.id.rl_change_password, R.id.rl_about,R.id.rl_clear_chche, R.id.btn_logout})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_change_password:
                startActivity(UpdatePasswordActivity.class);
                break;
            case R.id.rl_about:
                Bundle webBundle = new Bundle();
                webBundle.putString("url","http://jx.bdelay.com/worker/system/index.html");
                webBundle.putString("title","关于匠修");
                startActivity(WebActivity.class,webBundle);
                break;
            case R.id.rl_clear_chche:
                startActivity(ClearCacheActivity.class);
                break;
            case R.id.btn_logout:
                CurrentUser.getInstance().loginOut();
                Bundle bundle = new Bundle();
                bundle.putBoolean("back",false);
                startActivity(LoginActivity.class,bundle);
                EventBusUtil.post(MessageConstant.LOGOUT);
                break;
        }
    }

}
