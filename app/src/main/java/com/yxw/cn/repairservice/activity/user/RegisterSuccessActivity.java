package com.yxw.cn.repairservice.activity.user;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;

import butterknife.OnClick;

/**
 * 注册成功
 */
public class RegisterSuccessActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.act_register_success;
    }

    @OnClick({R.id.btn_perfect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_perfect:
                startActivity(IdCardInfoActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

}
