package com.yxw.cn.repairservice.activity.order;


import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.fragment.InServiceFragment;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 服务中
 * @author @author chenqm on 2018/1/15.
 */

public class InServiceActivity extends BaseActivity{

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] mTitles = {"待指派","待预约","待上门","待完成"};

    @Override
    protected int getLayoutResId() {
        return R.layout.act_my_order;
    }

    @Override
    public void initView() {
        titlebar.setTitle("服务中");
        //0（待接单） 1（待分派） 2（待预约） 3（待上门） 4（待完成） 5（已完成） 6（已退单）
        mFragmentList = new ArrayList<>();
        mFragmentList.add(InServiceFragment.getInstance(1));
        mFragmentList.add(InServiceFragment.getInstance(2));
        mFragmentList.add(InServiceFragment.getInstance(3));
        mFragmentList.add(InServiceFragment.getInstance(4));
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
