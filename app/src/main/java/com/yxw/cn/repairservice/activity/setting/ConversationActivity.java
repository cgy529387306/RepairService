package com.yxw.cn.repairservice.activity.setting;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;

/**
 * 匠修客服
 */
public class ConversationActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_customer_service1;
    }

    @Override
    public void initView() {
        titlebar.setTitle("匠修客服");
//        if (RongIM.getInstance() != null) {
//            RongIM.getInstance().startPrivateChat(this, "KEFU154510079986682", "标题");
//        }
    }
}
