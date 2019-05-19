package com.yxw.cn.repairservice.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseRefreshFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.order.AppointAbnormalActivity;
import com.yxw.cn.repairservice.activity.order.AppointOrderActivity;
import com.yxw.cn.repairservice.activity.order.OrderDetailActivity;
import com.yxw.cn.repairservice.activity.order.OrderSignInActivity;
import com.yxw.cn.repairservice.activity.order.SignAbnormalActivity;
import com.yxw.cn.repairservice.adapter.OrderAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.entity.OrderListData;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.listerner.OnChooseDateListener;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.pop.ApplyCancelOrderPop;
import com.yxw.cn.repairservice.pop.ContactPop;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.SpaceItemDecoration;
import com.yxw.cn.repairservice.util.TimePickerUtil;
import com.yxw.cn.repairservice.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 订单列表
 */
public class InServiceFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnItemClickListener,OrderAdapter.OnOrderOperateListener, ContactPop.SelectListener,ApplyCancelOrderPop.SelectListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String KEY_STATE = "key_state";
    private static final String KEY_TYPE = "key_type";
    private OrderAdapter mAdapter;
    private int mPage = 2;
    private boolean isNext = false;
    private int mOrderType;
    private ApplyCancelOrderPop mApplyCancelOrderPop;
    private ContactPop mContactPop;
    /**
     * @param type 0（待接单） 1（待分派） 2（待预约） 3（待上门） 4（待完成） 5（已完成） 6（已退单）
     * @return
     */
    public static Fragment getInstance(int type) {
        Fragment fragment = new InServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initView() {
        mOrderType = (int) getArguments().get(KEY_TYPE);
        mAdapter = new OrderAdapter(new ArrayList<>(),this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
        getOrderData(1);
    }

    private void getOrderData(int p) {
        Map<String, Object> requestMap = new HashMap<>();
        if (mOrderType!=5){
            requestMap.put("status",mOrderType);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("filter", requestMap);
        map.put("pageIndex", p);
        map.put("pageSize", loadCount);
        OkGo.<ResponseData<OrderListData>>post(UrlConstant.ORDER_SERVICE_LIST)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<OrderListData>>() {
                    @Override
                    public void onSuccess(ResponseData<OrderListData> response) {
                        if (response!=null && response.getData()!=null){
                            if (response.isSuccess()) {
                                if (p == 1) {
                                    mPage = 2;
                                    mAdapter.setNewData(response.getData().getItems());
                                    mRefreshLayout.finishRefresh();
                                } else {
                                    mAdapter.addData(response.getData().getItems());
                                    isNext = response.getData().isHasNext();
                                    if (isNext) {
                                        mPage++;
                                        mRefreshLayout.finishLoadMore();
                                    } else {
                                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                                    }
                                }
                                mAdapter.notifyDataSetChanged();
                                mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                                EventBusUtil.post(MessageConstant.WORKER_ORDERED_COUNT, mAdapter.getData().size());
                            } else {
                                toast(response.getMsg());
                                if (p == 1) {
                                    mRefreshLayout.finishRefresh(false);
                                } else {
                                    mRefreshLayout.finishLoadMore(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<OrderListData>> response) {
                        super.onError(response);
                        if (p == 1) {
                            mRefreshLayout.finishRefresh(false);
                        } else {
                            mRefreshLayout.finishLoadMore(false);
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getOrderData(1);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        getOrderData(mPage);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getItem(position)!=null){
            startActivity(OrderDetailActivity.class, mAdapter.getData().get(position));
        }
    }

    @Override
    public void onOrderCancel(OrderItem orderItem) {
        if (mApplyCancelOrderPop==null){
            mApplyCancelOrderPop = new ApplyCancelOrderPop(getActivity(),orderItem,this);
        }
        mApplyCancelOrderPop.showPopupWindow(mRecyclerView);
    }

    @Override
    public void onOrderAppoint(OrderItem orderItem) {
        //TODO  指派工程师
        startActivity(AppointOrderActivity.class,orderItem);
    }

    @Override
    public void onOrderTaking(OrderItem orderItem) {
    }

    @Override
    public void onAbnormal(OrderItem orderItem,int type) {
        if (type==0){
            Bundle bundle = new Bundle();
            bundle.putString("acceptId",orderItem.getAcceptId());
            startActivity(AppointAbnormalActivity.class,bundle);
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("acceptId",orderItem.getAcceptId());
            startActivity(SignAbnormalActivity.class,bundle);
        }
    }

    @Override
    public void onContact(OrderItem orderItem) {
        if (mContactPop==null){
            mContactPop = new ContactPop(getActivity(),this,orderItem);
        }
        mContactPop.showPopupWindow(mRecyclerView);
    }

    @Override
    public void onTurnReservation(OrderItem orderItem) {
        TimePickerUtil.showYearPicker(getActivity(), new OnChooseDateListener() {
            @Override
            public void getDate(Date date) {
                String startTime = TimeUtil.dateToString(date, "yyyy-MM-dd HH:mm:00");
                String endTime = TimeUtil.getAfterHourTime(date);
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("orderId", orderItem.getOrderId());
                map.put("bookingStartTime", startTime);
                map.put("bookingEndTime", endTime);
                OkGo.<ResponseData<Object>>post(UrlConstant.ORDER_TURN_RESERVATION)
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<ResponseData<Object>>() {
                            @Override
                            public void onSuccess(ResponseData<Object> response) {
                                dismissLoading();
                                if (response!=null){
                                    if (response.isSuccess()) {
                                        toast("预约成功");
                                        EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
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
                        });

            }
        });
    }

    @Override
    public void onSign(OrderItem orderItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order",orderItem);
        bundle.putInt("type",0);
        startActivity(OrderSignInActivity.class,bundle);
    }

    @Override
    public void onFinish(OrderItem orderItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order",orderItem);
        bundle.putInt("type",1);
        startActivity(OrderSignInActivity.class,bundle);
    }

    @Override
    public void onView(OrderItem orderItem) {
        startActivity(OrderDetailActivity.class, orderItem);
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_ORDER:
                getOrderData(1);
                break;
            case MessageConstant.NOTIFY_DETAIL_STATUS:
                getOrderData(1);
                break;
        }
    }

    @Override
    public void onCall(OrderItem orderItem) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + orderItem.getMobile());
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onTime(OrderItem orderItem) {
        TimePickerUtil.showYearPicker(getActivity(), new OnChooseDateListener() {
            @Override
            public void getDate(Date date) {
                String startTime = TimeUtil.dateToString(date, "yyyy-MM-dd HH:mm:00");
                String endTime = TimeUtil.getAfterHourTime(date);
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("orderId", orderItem.getOrderId());
                map.put("bookingStartTime", startTime);
                map.put("bookingEndTime", endTime);
                OkGo.<ResponseData<Object>>post(UrlConstant.ORDER_RESERVATION)
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<ResponseData<Object>>() {
                            @Override
                            public void onSuccess(ResponseData<Object> response) {
                                dismissLoading();
                                if (response!=null){
                                    if (response.isSuccess()) {
                                        toast("预约成功");
                                        EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
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
                        });

            }
        });
    }

    @Override
    public void onConfirm(OrderItem orderItem) {
        TimePickerUtil.showYearPicker(getActivity(), new OnChooseDateListener() {
            @Override
            public void getDate(Date date) {
                String startTime = TimeUtil.dateToString(date, "yyyy-MM-dd HH:mm:00");
                String endTime = TimeUtil.getAfterHourTime(date);
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("orderId", orderItem.getOrderId());
                map.put("bookingStartTime", startTime);
                map.put("bookingEndTime", endTime);
                OkGo.<ResponseData<Object>>post(UrlConstant.ORDER_RESERVATION)
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<ResponseData<Object>>() {
                            @Override
                            public void onSuccess(ResponseData<Object> response) {
                                dismissLoading();
                                if (response!=null){
                                    if (response.isSuccess()) {
                                        toast("预约成功");
                                        EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
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
                        });

            }
        });
    }

    @Override
    public void onComfirm(OrderItem orderItem) { //申请退单
        showLoading();
        OkGo.<ResponseData<List<OrderItem>>>post(UrlConstant.ORDER_SERVICE_RETURN + orderItem.getServiceId())
                .tag(this)
                .execute(new JsonCallback<ResponseData<List<OrderItem>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<OrderItem>> response) {
                        dismissLoading();
                        if (response!=null){
                            if (response.isSuccess()){
                                toast("退单成功");
                                EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
                            }else{
                                toast(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<List<OrderItem>>> response) {
                        super.onError(response);
                        dismissLoading();
                    }
                });
    }
}