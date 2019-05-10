package com.yxw.cn.repairservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseRefreshFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.order.AppointOrderActivity;
import com.yxw.cn.repairservice.activity.order.OrderDetailActivity;
import com.yxw.cn.repairservice.adapter.InServiceAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.InServiceData;
import com.yxw.cn.repairservice.entity.InServiceInfo;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.pop.ApplyCancelOrderPop;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 订单列表
 */
public class InServiceFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnItemClickListener, InServiceAdapter.OnInServiceOperateListener,ApplyCancelOrderPop.SelectListener{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String KEY_STATE = "key_state";
    private static final String KEY_TYPE = "key_type";
    private InServiceAdapter mAdapter;
    private int mPage = 2;
    private boolean isNext = false;
    private int mOrderType;
    private ApplyCancelOrderPop mApplyCancelOrderPop;
    /**
     * @param type 0待指派 1待预约 2待上门 3待完成 4全部
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
        mAdapter = new InServiceAdapter(mOrderType,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
//        getOrderData(1);
    }

    private List<String> getList() {
        List<String> dataLsit = new ArrayList<>();
        dataLsit.add("手机维修/苹果");
        dataLsit.add("手机维修/安卓");
        return dataLsit;
    }

    private void getOrderData(int p) {
        Map<String, Object> requestMap = new HashMap<>();
        if (mOrderType!=4){
            requestMap.put("orderStatus",mOrderType);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("filter", requestMap);
        map.put("pageIndex", p);
        map.put("pageSize", loadCount);
        map.put("sorter", "");
        OkGo.<ResponseData<InServiceData>>post(UrlConstant.ORDER_LIST)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<InServiceData>>() {
                    @Override
                    public void onSuccess(ResponseData<InServiceData> response) {
                        if (response!=null && response.getData()!=null){
                            if (response.isSuccess()) {
                                if (p == 1) {
                                    mPage = 2;
//                                    mAdapter.setNewData(response.getData().getItems());
                                    mRefreshLayout.finishRefresh();
                                } else {
//                                    mAdapter.addData(response.getData().getItems());
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
                    public void onError(Response<ResponseData<InServiceData>> response) {
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
    public void onAppointEngineer(InServiceInfo item) { //指派工程师
        startActivity(AppointOrderActivity.class);
    }

    @Override
    public void onApplyCancelOrder(InServiceInfo orderItem) {
        if (mApplyCancelOrderPop==null){
            mApplyCancelOrderPop = new ApplyCancelOrderPop(getActivity(),orderItem,this);
        }
        mApplyCancelOrderPop.showPopupWindow(mRecyclerView);
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_ORDER:
                getOrderData(1);
                break;
        }
    }

    @Override
    public void onComfirm(InServiceInfo orderItem) { //申请退单
        OkGo.<ResponseData<List<InServiceInfo>>>post(UrlConstant.DELETE_ENGINEER + orderItem.getMobile())
                .tag(this)
                .execute(new JsonCallback<ResponseData<List<InServiceInfo>>>() {

                    @Override
                    public void onSuccess(ResponseData<List<InServiceInfo>> response) {
                        if (response!=null){
                            if (response.isSuccess() && response.getData()!=null){
                                mAdapter.setNewData(response.getData());
                            }else{
                                toast(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<List<InServiceInfo>>> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mOrderType == 0){
            if (mAdapter.getItem(position)!=null){
                startActivity(OrderDetailActivity.class, mAdapter.getData().get(position));
            }
        }
    }
}