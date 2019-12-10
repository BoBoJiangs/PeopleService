package com.yb.peopleservice.view.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;
import com.yb.peopleservice.view.fragment.order.OrderChildListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class OrderListFragment extends BaseViewPagerFragment {
    private String[] mTitles = {"全部", "代付款", "进行中", "已完成", "待评价"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private Fragment allFragment;
    private Fragment paymentFragment;
    private Fragment doFragment;
    private Fragment finishFragment;
    private Fragment evaluateFragment;

    public static Fragment getInstanceFragment() {
        OrderListFragment fragment = new OrderListFragment();
        return fragment;
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
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        allFragment = OrderChildListFragment.getInstanceFragment();
        paymentFragment = OrderChildListFragment.getInstanceFragment();
        doFragment = OrderChildListFragment.getInstanceFragment();
        finishFragment = OrderChildListFragment.getInstanceFragment();
        evaluateFragment = OrderChildListFragment.getInstanceFragment();
        fragmentList.add(allFragment);
        fragmentList.add(paymentFragment);
        fragmentList.add(doFragment);
        fragmentList.add(finishFragment);
        fragmentList.add(evaluateFragment);
        return fragmentList;
    }

    @Override
    protected void initData() {
        viewPager.setScroll(true);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
