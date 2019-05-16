package com.yxw.cn.repairservice.activity.order;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.ChooseEngineerActivity;
import com.yxw.cn.repairservice.adapter.OrderUrgencyAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.entity.LoginInfo;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.entity.UrgencyBean;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.view.ClearEditText;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 订单指派
 */
public class AppointOrderActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{

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
    @BindView(R.id.rv_urgency)
    RecyclerView mRvUrgency;
    @BindView(R.id.lly_engineer)
    LinearLayout mLlyEngineer;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    private OrderItem orderItem;
    private String userId = "";
    private String urgency = "";
    private OrderUrgencyAdapter mOrderUrgencyAdapter;
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
        mOrderUrgencyAdapter = new OrderUrgencyAdapter(new ArrayList<>());
        mOrderUrgencyAdapter.setOnItemClickListener(this);
        mRvUrgency.setLayoutManager(new GridLayoutManager(this, 3));
        mRvUrgency.setAdapter(mOrderUrgencyAdapter);

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
            toast("请输入金额");
            return;
        }
        if (TextUtils.isEmpty(urgency)){
            toast("请选择紧急程度");
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("acceptServiceId",orderItem.getAcceptId());
        map.put("fee",fee);
        map.put("userId",userId);
        map.put("urgency",urgency);
        map.put("remark",mEtRemark.getText().toString().trim());

        OkGo.<ResponseData<List<OrderItem>>>post(UrlConstant.ORDER_SERVICE_ASSIGN)
                .upJson(gson.toJson(map))
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
    public void getData() {
        super.getData();
        getReservationUrgencyData();
    }

    private void getReservationUrgencyData() {
        if (AppUtil.reservationReasonList != null && AppUtil.reservationUrgencyList.size() > 0) {
            urgency = AppUtil.reservationUrgencyList.get(0).getDictId();
            mOrderUrgencyAdapter.setNewData(AppUtil.reservationUrgencyList);
        } else {
            showLoading();
            HashMap<String,String> map = new HashMap<>();
            map.put("dictKey","URGENCY");
            OkGo.<ResponseData<List<UrgencyBean>>>post(UrlConstant.GET_EXCEPTION_REASON)
                    .upJson(gson.toJson(map))
                    .execute(new JsonCallback<ResponseData<List<UrgencyBean>>>() {

                        @Override
                        public void onSuccess(ResponseData<List<UrgencyBean>> response) {
                            dismissLoading();
                            if (response!=null){
                                if (response.isSuccess() && response.getData()!=null){
                                    if (AppUtil.reservationUrgencyList != null && AppUtil.reservationUrgencyList.size() > 0) {
                                        urgency = AppUtil.reservationUrgencyList.get(0).getDictId();
                                        mOrderUrgencyAdapter.setNewData(AppUtil.reservationUrgencyList);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Response<ResponseData<List<UrgencyBean>>> response) {
                            super.onError(response);
                            dismissLoading();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 600) {
                EngineerInfo engineerInfo = (EngineerInfo) data.getExtras().getSerializable("engineer");
                if (engineerInfo!=null){
                    mTvName.setText(engineerInfo.getRealName());
                    ratingbar.setRating(engineerInfo.getStar());
                    AppUtil.showPic(this, mIvAvatar, engineerInfo.getAvatar());
                    userId = engineerInfo.getUserId();
                    if (!TextUtils.isEmpty(userId)){
                        mLlyEngineer.setVisibility(View.VISIBLE);
                    } else {
                        mLlyEngineer.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mOrderUrgencyAdapter.setSelect(position);
        mOrderUrgencyAdapter.notifyDataSetChanged();
        if (Helper.isNotEmpty(mOrderUrgencyAdapter.getData()) && mOrderUrgencyAdapter.getData().size()>position){
            urgency = mOrderUrgencyAdapter.getData().get(position).getDictId();
        }
    }
}
