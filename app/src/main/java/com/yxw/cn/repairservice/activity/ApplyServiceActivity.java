package com.yxw.cn.repairservice.activity;


import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.fragment.ApplyServiceFragment;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 加入服务商申请
 * @author @author chenqm on 2018/1/15.
 */

public class ApplyServiceActivity extends BaseActivity{

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] mTitles = {"待审核","全部"};

    @Override
    protected int getLayoutResId() {
        return R.layout.act_apply_service;
    }

    @Override
    public void initView() {
//        OrderType orderType = (OrderType) getIntent().getSerializableExtra("type");
        titlebar.setTitle("申请记录");

        mFragmentList = new ArrayList<>();
        mFragmentList.add(ApplyServiceFragment.getInstance(0));
        mFragmentList.add(ApplyServiceFragment.getInstance(2));
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
