package com.yxw.cn.repairservice.activity.order;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.OrderAbnormalAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.ReasonBean;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.listerner.OnChooseDateListener;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.util.TimePickerUtil;
import com.yxw.cn.repairservice.util.TimeUtil;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 预约异常
 */
public class AppointAbnormalActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_reason)
    RecyclerView mRvReason;
    @BindView(R.id.et_remark)
    EditText etRemark;

    private OrderAbnormalAdapter mAdapter;
    private String orderId;
    private String exceptionIds;

    private String startTime;
    private String endTime;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_appoint_abnormal;
    }

    @Override
    public void initView() {
        titleBar.setTitle("预约异常反馈");
        orderId = getIntent().getStringExtra("orderId");
        mAdapter = new OrderAbnormalAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(this);
        mRvReason.setLayoutManager(new GridLayoutManager(this, 2));
        mRvReason.setAdapter(mAdapter);
    }

    @Override
    public void getData() {
        getReservationReasonData();
    }

    private void getReservationReasonData(){
        if (AppUtil.reservationReasonList != null && AppUtil.reservationReasonList.size() > 0) {
            exceptionIds = AppUtil.signReasonList.get(0).getDictId();
            mAdapter.setNewData(AppUtil.reservationReasonList);
        } else{
            showLoading();
            HashMap<String,String> map = new HashMap<>();
            map.put("dictKey","TURN_RESERVATION_REASON");
            OkGo.<ResponseData<List<ReasonBean>>>post(UrlConstant.GET_EXCEPTION_REASON)
                    .upJson(gson.toJson(map))
                    .execute(new JsonCallback<ResponseData<List<ReasonBean>>>() {

                        @Override
                        public void onSuccess(ResponseData<List<ReasonBean>> response) {
                            dismissLoading();
                            if (response!=null){
                                if (response.isSuccess() && response.getData()!=null){
                                    if (AppUtil.reservationReasonList != null && AppUtil.reservationReasonList.size() > 0) {
                                        exceptionIds = AppUtil.signReasonList.get(0).getDictId();
                                        mAdapter.setNewData(AppUtil.reservationReasonList);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Response<ResponseData<List<ReasonBean>>> response) {
                            super.onError(response);
                            dismissLoading();
                        }
                    });
        }
    }


    @OnClick({R.id.rl_time, R.id.cancel, R.id.confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_time:
                TimePickerUtil.showYearPicker(this, new OnChooseDateListener() {
                    @Override
                    public void getDate(Date date) {
                        startTime = TimeUtil.dateToString(date, "yyyy-MM-dd HH:mm:00");
                        endTime = TimeUtil.getAfterHourTime(date);
                        tvTime.setText(startTime);
                    }
                });
                break;
            case R.id.confirm:
                String desc = etRemark.getText().toString().trim();
                if (Helper.isEmpty(startTime)) {
                    toast("请先选择再次预约时间！");
                } else if (Helper.isEmpty(exceptionIds)) {
                    toast("请先选择异常原因");
                } else {
                    showLoading();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("orderId", orderId);
                    map.put("bookingStartTime", startTime);
                    map.put("bookingEndTime", endTime);
                    map.put("ids", exceptionIds);
                    if (Helper.isNotEmpty(desc)){
                        map.put("fixDesc", desc);
                    }
                    OkGo.<ResponseData<String>>post(UrlConstant.ORDER_EXEPTION_APPOINT)
                            .upJson(gson.toJson(map))
                            .execute(new JsonCallback<ResponseData<String>>() {
                                @Override
                                public void onSuccess(ResponseData<String> response) {
                                    dismissLoading();
                                    if (response!=null){
                                        if (response.isSuccess()) {
                                            toast("异常反馈成功");
                                            AppointAbnormalActivity.this.finish();
                                            EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
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
                            });
                }
                break;
            case R.id.cancel:
                this.finish();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setSelect(position);
        mAdapter.notifyDataSetChanged();
        if (Helper.isNotEmpty(mAdapter.getData()) && mAdapter.getData().size()>position){
            exceptionIds = mAdapter.getData().get(position).getDictId();
        }
    }
}
