package com.yxw.cn.repairservice.activity.user;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.main.MainActivity;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.SpConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.LoginInfo;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.SpUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 验证码登录
 */
public class QuickLoginActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_tel)
    EditText mEtTel;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_quick_login;
    }

    @Override
    public void initView() {
        titlebar.setTitle("验证码登录");
        mEtTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (AppUtil.isPhone(s.toString())) {
                    mTvGetCode.setBackgroundResource(R.drawable.corner_red);
                } else {
                    mTvGetCode.setBackgroundResource(R.drawable.corner_gray);
                }
            }
        });
        mEtTel.setText(SpUtil.getStr(SpConstant.LOGIN_MOBILE));
    }

    @OnClick({R.id.tv_get_code, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (AppUtil.isPhone(mEtTel.getText().toString())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("mobile", mEtTel.getText().toString());
                    OkGo.<ResponseData<String>>post(UrlConstant.GET_CODE)
                            .upJson(gson.toJson(map))
                            .execute(new JsonCallback<ResponseData<String>>() {
                                         @Override
                                         public void onSuccess(ResponseData<String> response) {
                                             toast(response.getMsg());
                                         }
                                     }
                            );
                } else {
                    toast("请输入正确的手机号！");
                }
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(mEtTel.getText().toString().trim())) {
                    toast("手机号不能为空！");
                } else if (TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
                    toast("验证码不能为空！");
                } else {
                    showLoading();
                    Map<String, Object> map = new HashMap<>();
                    map.put("userName", mEtTel.getText().toString());
                    map.put("smsCode", mEtPassword.getText().toString().trim());
                    map.put("appSign", UrlConstant.mRoleSign);
                    OkGo.<ResponseData<LoginInfo>>post(UrlConstant.QUICK_LOGIN)
                            .upJson(gson.toJson(map))
                            .execute(new JsonCallback<ResponseData<LoginInfo>>() {
                                         @Override
                                         public void onSuccess(ResponseData<LoginInfo> response) {
                                             dismissLoading();
                                             if (response!=null){
                                                 if (response.isSuccess()) {
                                                     toast("登录成功");
                                                     SpUtil.putStr(SpConstant.LOGIN_MOBILE, mEtTel.getText().toString().trim());
                                                     CurrentUser.getInstance().login(response.getData());
                                                     HttpHeaders headers = new HttpHeaders();
                                                     headers.put("Authorization", "Bearer "+response.getData().getToken());
                                                     OkGo.getInstance().addCommonHeaders(headers);
                                                     startActivityFinish(MainActivity.class);
                                                     EventBusUtil.post(MessageConstant.LOGIN);
                                                 }else {
                                                     toast(response.getMsg());
                                                 }
                                             }
                                         }

                                         @Override
                                         public void onError(Response<ResponseData<LoginInfo>> response) {
                                             super.onError(response);
                                             dismissLoading();
                                         }
                                     }

                            );
                }
                break;
        }
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.LOGIN:
                finish();
                break;
        }
    }
}
