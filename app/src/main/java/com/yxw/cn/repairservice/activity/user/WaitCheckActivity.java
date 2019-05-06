package com.yxw.cn.repairservice.activity.user;

import android.view.View;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.main.MainActivity;
import com.yxw.cn.repairservice.util.ActivityManager;

import butterknife.OnClick;

/**
 * 等待审核
 */
public class WaitCheckActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.act_wait_check;
    }

    @OnClick({R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                startActivityFinish(MainActivity.class);
                ActivityManager.getInstance().closeAllActivityExceptOne(MainActivity.class.getName());
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

}
