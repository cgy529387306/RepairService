package com.yxw.cn.repairservice.activity.user;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.LoginInfo;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更改阿里账号
 */
public class UpdateAlipayActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.et_name)
    EditText mEtName;

    private LoginInfo loginInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_update_aliaccount;
    }

    @Override
    public void initView() {
        titleBar.setTitle("绑定支付宝账号");
        if (CurrentUser.getInstance().isLogin()) {
            loginInfo = CurrentUser.getInstance();
            if (!TextUtils.isEmpty(loginInfo.getAliplayAccount())) {
                titleBar.setTitle("修改支付宝账号");
                mEtName.setText(loginInfo.getAliplayAccount());
                mEtName.setSelection(loginInfo.getAliplayAccount().length());
            }else{
                titleBar.setTitle("绑定支付宝账号");
            }
        } else {
            loginInfo = new LoginInfo();
        }
    }

    @OnClick({R.id.confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                String aliAccount = mEtName.getText().toString().trim();
                if (TextUtils.isEmpty(aliAccount)) {
                    toast("请输入支付宝账号！");
                    return;
                }
                showLoading();
                OkGo.<ResponseData<Object>>post(UrlConstant.UPDATE_ALIPAY_ACCOUNT+aliAccount)
                        .execute(new JsonCallback<ResponseData<Object>>() {
                                     @Override
                                     public void onSuccess(ResponseData<Object> response) {
                                         dismissLoading();
                                         if (response!=null){
                                             if (response.isSuccess()){
                                                 toast("保存成功");
                                                 loginInfo.setAliplayAccount(aliAccount);
                                                 CurrentUser.getInstance().login(loginInfo);
                                                 EventBusUtil.post(MessageConstant.NOTIFY_GET_INFO);
                                                 finish();
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
                                 }
                        );
                break;
        }
    }
    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_INFO:
                initData();
                break;
        }
    }

}
