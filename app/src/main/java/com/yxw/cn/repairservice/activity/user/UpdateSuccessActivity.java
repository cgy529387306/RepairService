package com.yxw.cn.repairservice.activity.user;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.main.MainActivity;

import butterknife.OnClick;

/**
 * 修改维修项目成功
 */
public class UpdateSuccessActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.act_update_success;
    }

    @OnClick({R.id.btn_perfect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_perfect:
                startActivityFinish(MainActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

}
