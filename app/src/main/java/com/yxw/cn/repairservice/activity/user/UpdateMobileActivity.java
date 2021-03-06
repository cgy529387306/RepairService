package com.yxw.cn.repairservice.activity.user;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改电话
 */
public class UpdateMobileActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.et_name)
    EditText mEtName;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_update_name;
    }

    @Override
    public void initView() {
        titleBar.setTitle("修改电话");
        if (CurrentUser.getInstance().isLogin()) {
            CurrentUser currentUser = CurrentUser.getInstance();
            if (!TextUtils.isEmpty(currentUser.getMobile())) {
                mEtName.setText(currentUser.getMobile());
                mEtName.setSelection(currentUser.getMobile().length());
            }
        }
    }

    @OnClick({R.id.confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (TextUtils.isEmpty(mEtName.getText().toString().trim())) {
                    toast("您还未输入电话号码！");
                    return;
                }
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("mobile", mEtName.getText().toString().trim());
                OkGo.<ResponseData<Object>>post(UrlConstant.CHANGE_USERINFO)
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<ResponseData<Object>>() {
                                     @Override
                                     public void onSuccess(ResponseData<Object> response) {
                                         dismissLoading();
                                         if (response!=null){
                                             if (response.isSuccess()) {
                                                 toast("修改成功");
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
}
