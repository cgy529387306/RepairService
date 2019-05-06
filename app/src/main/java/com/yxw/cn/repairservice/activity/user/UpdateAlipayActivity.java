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

    private Gson gson = new Gson();
    private LoginInfo loginInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_update_name;
    }

    @Override
    public void initView() {
        titleBar.setTitle("修改支付宝");
        if (CurrentUser.getInstance().isLogin()) {
            loginInfo = CurrentUser.getInstance();
            if (!TextUtils.isEmpty(loginInfo.getAliplayAccount())) {
                mEtName.setText(loginInfo.getAliplayAccount());
                mEtName.setSelection(loginInfo.getAliplayAccount().length());
            }
        } else {
            loginInfo = new LoginInfo();
        }
    }

    @OnClick({R.id.confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (TextUtils.isEmpty(mEtName.getText().toString().trim())) {
                    toast("您还未输入支付宝账号！");
                    return;
                }
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("aliplayAccount", mEtName.getText().toString().trim());
                OkGo.<ResponseData<String>>post(UrlConstant.UPDATE_ALIPAY_ACCOUNT)
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<ResponseData<String>>() {
                                     @Override
                                     public void onSuccess(ResponseData<String> response) {
                                         dismissLoading();
                                         toast(response.getMsg());
                                         if (response.isSuccess()) {
                                             loginInfo.setAliplayAccount(mEtName.getText().toString().trim());
                                             CurrentUser.getInstance().login(loginInfo);
                                             EventBusUtil.post(MessageConstant.NOTIFY_GET_INFO);
                                             finish();
                                         }
                                     }

                                     @Override
                                     public void onError(Response<ResponseData<String>> response) {
                                         super.onError(response);
                                         dismissLoading();
                                     }
                                 }
                        );
                break;
        }

    }
}
