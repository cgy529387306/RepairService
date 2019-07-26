package com.yxw.cn.repairservice.fragment;

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
import com.yxw.cn.repairservice.activity.order.InServiceActivity;
import com.yxw.cn.repairservice.activity.order.OrderDetailActivity;
import com.yxw.cn.repairservice.adapter.OrderAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.OperateResult;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.entity.OrderListData;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.pop.ConfirmOrderPop;
import com.yxw.cn.repairservice.pop.ContactPop;
import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.util.PreferencesHelper;
import com.yxw.cn.repairservice.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 订单列表（订单池）
 */
public class OrderFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnItemClickListener , OrderAdapter.OnOrderOperateListener, ContactPop.SelectListener,ConfirmOrderPop.SelectListener{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String KEY_TYPE = "key_type";
    private static final String KEY_STATE = "key_state";
    private OrderAdapter mAdapter;
    private int mPage = 2;
    private boolean isNext = false;
    private int mOrderType;
    private String mBookingTime;
    private ConfirmOrderPop mConfirmOrderPop;
    private int mState;

    private String mLocationLat,mLocationLng;
    /**
     * @param type 0:今天 1:明天 2:全部
     * @return
     */
    public static Fragment getInstance(int type,int state) {
        Fragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        bundle.putInt(KEY_STATE, state);
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
        mState = (int) getArguments().get(KEY_STATE);
        if (mOrderType==0){
            mBookingTime = Helper.date2String(new Date(),"yyyy-MM-dd");
        }else if (mOrderType==1){
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
            mBookingTime = Helper.date2String(calendar.getTime(),"yyyy-MM-dd");
        }else{
            mBookingTime = "";
        }

        mAdapter = new OrderAdapter(new ArrayList<>(),this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
        initLatLng();
        getOrderData(1);
    }


    private void getOrderData(int p) {
        Map<String, Object> requestMap = new HashMap<>();
        if (mOrderType!=2){
            requestMap.put("customerBookingTime",mBookingTime);
        }
        if (mState==0){
            requestMap.put("locationLat",mLocationLat);
            requestMap.put("locationLng",mLocationLng);
        }
        requestMap.put("status",mState);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", requestMap);
        map.put("pageIndex", p);
        map.put("pageSize", loadCount);
        OkGo.<ResponseData<OrderListData>>post(UrlConstant.ORDER_SERVICE_LIST)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<OrderListData>>() {
                    @Override
                    public void onSuccess(ResponseData<OrderListData> response) {
                        if (response!=null){
                            if (response.isSuccess() && response.getData()!=null) {
                                isNext = response.getData().isHasNext();
                                if (p == 1) {
                                    mAdapter.setNewData(response.getData().getItems());
                                    mAdapter.setEmptyView(R.layout.empty_order, (ViewGroup) mRecyclerView.getParent());
                                    mRefreshLayout.finishRefresh();
                                    if (isNext){
                                        mPage = 2;
                                    }else{
                                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                                    }
                                } else {
                                    mAdapter.addData(response.getData().getItems());
                                    if (isNext) {
                                        mPage++;
                                        mRefreshLayout.finishLoadMore();
                                    } else {
                                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                                    }
                                }
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
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_ORDER:
                getOrderData(1);
                break;
            case MessageConstant.MY_LOCATION:
                initLatLng();
                break;
        }
    }


    @Override
    public void onOrderCancel(OrderItem orderItem) {

    }

    @Override
    public void onOrderCancelSer(OrderItem orderItem) {

    }

    @Override
    public void onOrderAppoint(OrderItem orderItem) {

    }

    @Override
    public void onOrderTaking(OrderItem orderItem) {
        if (mConfirmOrderPop==null){
            mConfirmOrderPop = new ConfirmOrderPop(getActivity(),orderItem,this);
        }
        mConfirmOrderPop.showPopupWindow(mRecyclerView);
    }

    @Override
    public void onAbnormal(OrderItem orderItem,int type) {
    }

    @Override
    public void onContact(OrderItem orderItem) {
    }

    @Override
    public void onTurnReservation(OrderItem orderItem) {
    }

    @Override
    public void onSign(OrderItem orderItem) {
    }

    @Override
    public void onFinish(OrderItem orderItem) {
    }

    @Override
    public void onView(OrderItem orderItem) {
    }

    @Override
    public void onCall(OrderItem orderItem) {
    }

    @Override
    public void onTime(OrderItem orderItem) {
    }

    @Override
    public void onConfirm(OrderItem orderItem) {
    }

    @Override
    public void onOrderComfirm(OrderItem orderItem) {
        showLoading();
        OkGo.<ResponseData<OperateResult>>post(UrlConstant.ORDER_RECEIVE+orderItem.getOrderId())
                .execute(new JsonCallback<ResponseData<OperateResult>>() {
                             @Override
                             public void onSuccess(ResponseData<OperateResult> response) {
                                 dismissLoading();
                                 if (response!=null){
                                     if (response.isSuccess()) {
                                         toast("抢单成功");
                                         startActivity(InServiceActivity.class);
                                         getActivity().finish();
                                     }else{
                                         toast(response.getMsg());
                                     }
                                 }
                             }

                             @Override
                             public void onError(Response<ResponseData<OperateResult>> response) {
                                 super.onError(response);
                                 dismissLoading();
                             }
                         }
                );
    }

    private void initLatLng(){
        mLocationLat = PreferencesHelper.getInstance().getString("latitude","26.088114");
        mLocationLng = PreferencesHelper.getInstance().getString("longitude","119.310492");
    }
}