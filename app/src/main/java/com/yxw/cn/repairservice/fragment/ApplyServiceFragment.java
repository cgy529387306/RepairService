package com.yxw.cn.repairservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yxw.cn.repairservice.BaseRefreshFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.ApplyServiceAdapter;
import com.yxw.cn.repairservice.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 加入服务商申请列表
 */
public class ApplyServiceFragment extends BaseRefreshFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String KEY_STATE = "key_state";
    private static final String KEY_TYPE = "key_type";
    private ApplyServiceAdapter mAdapter;
    /**
     * @param state 0:未通过  1:全部
     * @return
     */
    public static Fragment getInstance(int state,int type) {
        Fragment fragment = new ApplyServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_STATE, state);
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
        mAdapter = new ApplyServiceAdapter(getList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
    }
    private List<String> getList(){
        List<String> dataLsit = new ArrayList<>();
        dataLsit.add("陈秋梅");
        dataLsit.add("蔡桂有");
        return dataLsit;
    }

}