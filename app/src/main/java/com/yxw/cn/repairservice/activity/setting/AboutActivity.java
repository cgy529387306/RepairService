package com.yxw.cn.repairservice.activity.setting;

import android.widget.TextView;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_about;
    }

    @Override
    public void initView() {
        titleBar.setTitle("关于智匠");
        tvVersion.setText(String.format("智匠 V%s", AppUtil.getVerName()));
    }


}
