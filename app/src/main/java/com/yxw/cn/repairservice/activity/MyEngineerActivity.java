package com.yxw.cn.repairservice.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.MyEngineerAdapter;
import com.yxw.cn.repairservice.adapter.ServiceProdiverAdapter;
import com.yxw.cn.repairservice.view.RecycleViewDivider;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的工程师
 */
public class MyEngineerActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener{

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    LinearLayout mLlyEdit;
    LinearLayout mLlyApply;
    TextView mTvEdit;
    TextView mTvApply;
    MyEngineerAdapter mMyEngineerAdapter;
    boolean is_edit = true;
    boolean is_delete = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_my_engineer;
    }

    @Override
    public void initView() {
        titleBar.setTitle("我的工程师");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL,1,getResources().getColor(R.color.gray_divider)));
        mMyEngineerAdapter = new MyEngineerAdapter(getList());
        mRecyclerView.setAdapter(mMyEngineerAdapter);
        //添加Header
        View header = LayoutInflater.from(this).inflate(R.layout.item_my_engineer_head, mRecyclerView, false);
        mLlyEdit = header.findViewById(R.id.lly_edit);
        mLlyApply = header.findViewById(R.id.lly_apply);
        mTvEdit = header.findViewById(R.id.tv_edit);
        mTvApply = header.findViewById(R.id.tv_apply);
        mMyEngineerAdapter.addHeaderView(header);
        mMyEngineerAdapter.setOnItemChildClickListener(this);
        mLlyEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_edit) {
                    is_edit = false;
                    is_delete = true;
                    mLlyEdit.setBackgroundResource(R.drawable.myengineer_border_red);
                    mTvEdit.setText("完成");
                    mTvEdit.setTextColor(getResources().getColor(R.color.white));
                }else {
                    is_edit = true;
                    is_delete = false;
                    mLlyEdit.setBackgroundResource(R.drawable.myengineer_border_gray);
                    mTvEdit.setText("编辑");
                    mTvEdit.setTextColor(getResources().getColor(R.color.black_my_engineer));
                }
                mMyEngineerAdapter.deleteStatus(is_delete);
                mMyEngineerAdapter.notifyDataSetChanged();
            }
        });
    }
    private List<String> getList(){
        List<String> dataLsit = new ArrayList<>();
        dataLsit.add("陈秋梅");
        dataLsit.add("蔡桂有");
        return dataLsit;
    }

    @OnClick({})
    public void click(View view) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iv_delete){

        }
    }
}
