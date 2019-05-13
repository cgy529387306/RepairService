package com.yxw.cn.repairservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseRefreshFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.ApplyServiceAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.ApplyItem;
import com.yxw.cn.repairservice.entity.ApplyListData;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.SpaceItemDecoration;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 加入服务商申请列表
 */
public class ApplyServiceFragment extends BaseRefreshFragment implements ApplyServiceAdapter.OnApplyServiceOperateListener{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String KEY_TYPE = "key_type";
    private ApplyServiceAdapter mAdapter;
    private int mPage = 2;
    private int mApplyType;
    private boolean isNext = false;
    /**
     * @param type 0:未通过  1:全部
     * @return
     */
    public static Fragment getInstance(int type) {
        Fragment fragment = new ApplyServiceFragment();
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
        mApplyType = (int) getArguments().get(KEY_TYPE);
        mAdapter = new ApplyServiceAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
        getApplyData(1);
    }

    private void getApplyData(int i) {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("serviceStatus",mApplyType);
        map.put("filter", requestMap);
        map.put("pageIndex", i);
        map.put("pageSize", loadCount);
        OkGo.<ResponseData<ApplyListData>>post(UrlConstant.FIND_ALL_APPLY)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<ApplyListData>>() {
                    @Override
                    public void onSuccess(ResponseData<ApplyListData> response) {
                        if (response!=null && response.getData()!=null){
                            if (response.isSuccess()) {
                                if (i == 1) {
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
                                if (i== 1) {
                                    mRefreshLayout.finishRefresh(false);
                                } else {
                                    mRefreshLayout.finishLoadMore(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<ApplyListData>> response) {
                        super.onError(response);
                        if (i == 1) {
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
        getApplyData(1);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        getApplyData(mPage);
    }

    @Override
    public void onApplyServiceAgree(ApplyItem applyItem) {
        checkApply(applyItem,1);
    }

    @Override
    public void onApplyServiceRefuse(ApplyItem applyItem) {
        checkApply(applyItem,2);
    }

    private void checkApply(ApplyItem applyItem,int type){
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("applyId",applyItem.getApplyId());
        map.put("applyStatus",type);
        OkGo.<ResponseData<String>>post(UrlConstant.EXAMINE_APPLY)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<String>>() {

                    @Override
                    public void onSuccess(ResponseData<String> response) {
                        dismissLoading();
                        if (response!=null){
                            if (response.isSuccess()){
                                EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_APPLY);
                                toast("审核成功");
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

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_APPLY:
                getApplyData(1);
                break;
        }
    }
}