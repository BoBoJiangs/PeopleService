package com.yb.peopleservice.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
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

public class MainActivity extends BaseToolbarActivity implements OnTabSelectListener {

    private String[] mTitles = {"首页", "分类", "生活雷达", "订单", "个人中心"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_class_unselect,
            R.mipmap.tab_map_unselect, R.mipmap.tab_order_unselect, R.mipmap.tab_center_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_class_select,
            R.mipmap.tab_map_select, R.mipmap.tab_order_select, R.mipmap.tab_center_select};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @Override
    public int contentViewResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        toolbar.setVisibility(View.GONE);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.base_text_color).init();
    }

    @Override
    protected void initData() {
        commonTabLayout.setTabData(getTabEntityList(), this, R.id.frameLayout,
                getFragmentList());
        commonTabLayout.setOnTabSelectListener(this);
    }


    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return "首页";
    }

    protected ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntityList.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntityList;
    }

    private ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.getInstanceFragment());
        fragmentList.add(ClassifyFragment.getInstanceFragment());
        fragmentList.add(LifeRadarMapFragment.getInstanceFragment());
        fragmentList.add(OrderListFragment.getInstanceFragment());
        fragmentList.add(PersonalFragment.getInstanceFragment());

        return fragmentList;
    }

    @Override
    public void onTabSelect(int position) {
        if (position == 0 || position == 4) {
            ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarDarkFont(false, 0.2f)
                    .statusBarColor(R.color.base_text_color).init();
        } else {
            ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarDarkFont(true, 0.2f)
                    .statusBarColor(R.color.white).init();
        }
    }
}
