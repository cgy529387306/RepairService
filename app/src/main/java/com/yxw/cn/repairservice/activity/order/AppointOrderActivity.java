package com.yxw.cn.repairservice.activity.order;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.ApplyServiceActivity;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.InServiceInfo;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单指派
 */
public class AppointOrderActivity extends BaseActivity{

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    private Gson mGson = new Gson();
    @Override
    protected int getLayoutResId() {
        return R.layout.act_appoint_order;
    }

    @Override
    public void initView() {
        titleBar.setTitle("订单指派");
    }

    @OnClick({})
    public void click(View view) {

    }

    @Override
    public void getData() {
        super.getData();
        HashMap<String, Object> map = new HashMap<>();
        map.put("acceptServiceId","");
        map.put("userId","");
        OkGo.<ResponseData<List<InServiceInfo>>>post(UrlConstant.ORDER_SERVICE_ASSIGN)
                .upJson(mGson.toJson(map))
                .execute(new JsonCallback<ResponseData<List<InServiceInfo>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<InServiceInfo>> response) {
                    }

                    @Override
                    public void onError(Response<ResponseData<List<InServiceInfo>>> response) {
                        super.onError(response);
                    }
                });

    }
}
