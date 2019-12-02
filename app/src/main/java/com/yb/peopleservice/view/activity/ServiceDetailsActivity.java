package com.yb.peopleservice.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseActivity;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.details.EvaluateFragment;
import com.yb.peopleservice.view.fragment.details.ServiceFragment;
import com.yb.peopleservice.view.fragment.order.OrderChildListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class ServiceDetailsActivity extends BaseViewPagerActivity {
    private String[] mTitles = {"服务", "详情", "评价"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    @Override
    protected View getTabLayout() {
        return commonTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        return mTitles;
    }

    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragment1 = ServiceFragment.getInstanceFragment();
        fragment2 = ServiceFragment.getInstanceFragment();
        fragment3 = EvaluateFragment.getInstanceFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        return fragmentList;
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_service_details;
    }

    @Override
    protected void initData() {
        viewPager.setScroll(true);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return null;
    }
}
