package com.yxw.cn.repairservice.activity.user;


import android.text.TextUtils;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.view.ClearEditText;
import com.yxw.cn.repairservice.view.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 加入服务商
 */
public class JoinServiceProviderActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;

    @BindView(R.id.et_parent_id)
    ClearEditText mEtParentId;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_join_service_provider;
    }

    @Override
    public void initView() {
        titleBar.setTitle("加入服务商");

    }

    @OnClick({R.id.btn_confirm})
    public void click(View view) {
        if (view.getId() == R.id.btn_confirm){
            doConfirm();
        }
    }

    private void doConfirm(){
        String parentId = mEtParentId.getText().toString().trim();
        if (TextUtils.isEmpty(parentId)){
            toast("请输入要加入的服务商编号");
            return;
        }
        OkGo.<ResponseData<String>>post(UrlConstant.JOIN_SERVICE+parentId)
                .execute(new JsonCallback<ResponseData<String>>() {
                             @Override
                             public void onSuccess(ResponseData<String> response) {
                                 dismissLoading();
                                 if (response!=null){
                                     if (response.isSuccess()) {
                                         toast("申请成功");
                                         startActivityFinish(ApplyServiceProviderActivity.class);
                                     }else{
                                         toast(response.getMsg());
                                     }
                                 }
                             }

                             @Override
                             public void onError(Response<ResponseData<String>> response) {
                                 super.onError(response);
                                 dismissLoading();
                             }
                         }
                );
    }
}
