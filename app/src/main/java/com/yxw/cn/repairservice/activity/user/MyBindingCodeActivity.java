package com.yxw.cn.repairservice.activity.user;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.TextView;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的绑定码
 */
public class MyBindingCodeActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;

    @BindView(R.id.tv_binding_code)
    TextView mTvBindingCode;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_my_binding_code;
    }

    @Override
    public void initView() {
        titleBar.setTitle("我的绑定码");
        if (CurrentUser.getInstance().isLogin()){
            mTvBindingCode.setText(CurrentUser.getInstance().getBindingCode());
        }
    }

    @OnClick({R.id.btn_confirm})
    public void click(View view) {
        if (view.getId() == R.id.btn_confirm){
            doConfirm();
        }
    }

    private void doConfirm(){
        ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("Label", CurrentUser.getInstance().getBindingCode());
        myClipboard.setPrimaryClip(myClip);
        toast("复制成功");
    }
}
