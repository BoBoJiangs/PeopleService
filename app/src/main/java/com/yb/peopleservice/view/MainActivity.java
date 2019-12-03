package com.yb.peopleservice.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.ClassifyFragment;
import com.yb.peopleservice.view.fragment.HomeFragment;
import com.yb.peopleservice.view.fragment.LifeRadarMapFragment;
import com.yb.peopleservice.view.fragment.OrderListFragment;
import com.yb.peopleservice.view.fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;

public class MainActivity extends BaseViewPagerActivity {

    private String[] mTitles = {"首页", "分类", "生活雷达", "订单", "个人中心"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_class_unselect,
            R.mipmap.tab_map_unselect, R.mipmap.tab_order_unselect, R.mipmap.tab_center_unselect};
        private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_class_select,
            R.mipmap.tab_map_select, R.mipmap.tab_order_select,R.mipmap.tab_center_select};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;

    @Override
    public int contentViewResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        viewPager.setOffscreenPageLimit(5);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return "首页";
    }

    @Override
    protected View getTabLayout() {
        return commonTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        return mTitles;
    }

    @Override
    protected ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntityList.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntityList;
    }

    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.getInstanceFragment());
        fragmentList.add(ClassifyFragment.getInstanceFragment());
        fragmentList.add(OrderListFragment.getInstanceFragment());
        fragmentList.add(LifeRadarMapFragment.getInstanceFragment());
        fragmentList.add(PersonalFragment.getInstanceFragment());

        return fragmentList;
    }
}
