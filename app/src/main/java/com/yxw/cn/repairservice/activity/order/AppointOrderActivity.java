package com.yxw.cn.repairservice.activity.order;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.ChooseEngineerActivity;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.LoginInfo;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.view.ClearEditText;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 订单指派
 */
public class AppointOrderActivity extends BaseActivity{

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.et_fee)
    ClearEditText mEtFee;
    private OrderItem orderItem;
    private Gson mGson = new Gson();
    private String userId = "";
    @Override
    protected int getLayoutResId() {
        return R.layout.act_appoint_order;
    }

    @Override
    public void initView() {
        orderItem = (OrderItem) getIntent().getSerializableExtra("data");
        titleBar.setTitle("订单指派");
        LoginInfo loginInfo = CurrentUser.getInstance();
        mTvName.setText(loginInfo.getRealName());
        AppUtil.showPic(this, mIvAvatar, loginInfo.getAvatar());
        userId = loginInfo.getUserId();
    }

    @OnClick({R.id.lly_choose_engineer,R.id.confirm})
    public void click(View view) {
        int id = view.getId();
        if (id == R.id.lly_choose_engineer){
            startActivityForResult(new Intent(this, ChooseEngineerActivity.class),600);
        }else if (id == R.id.confirm){
            doAppointEngineer();
        }
    }

    private void doAppointEngineer() {
        String fee = mEtFee.getText().toString().trim();
        if (TextUtils.isEmpty(fee)){
            toast("金额不能为空");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("acceptServiceId",orderItem.getAcceptId());
        map.put("fee",fee);
        map.put("userId",userId);
        OkGo.<ResponseData<List<OrderItem>>>post(UrlConstant.ORDER_SERVICE_ASSIGN)
                .upJson(mGson.toJson(map))
                .execute(new JsonCallback<ResponseData<List<OrderItem>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<OrderItem>> response) {
                        AppointOrderActivity.this.finish();
                        EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
                    }

                    @Override
                    public void onError(Response<ResponseData<List<OrderItem>>> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 600) {
                String name = data.getExtras().getString("realName");
                int star = data.getExtras().getInt("star");
                String avatar = data.getExtras().getString("avatar");
                userId = data.getExtras().getString("userId");
                mTvName.setText(name);
                ratingbar.setRating(star);
                AppUtil.showPic(this, mIvAvatar, avatar);
            }
        }
    }
}
