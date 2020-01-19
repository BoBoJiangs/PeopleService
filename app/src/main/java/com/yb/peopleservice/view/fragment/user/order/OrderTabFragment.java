package com.yb.peopleservice.view.fragment.user.order;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class OrderTabFragment extends BaseViewPagerFragment {
    protected String[] mTitles = {"全部", "代付款", "进行中", "已完成", "待评价"};
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
        OrderTabFragment fragment = new OrderTabFragment();
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
        allFragment = OrderListFragment.getInstanceFragment(OrderBean.WAITING);
        paymentFragment = OrderListFragment.getInstanceFragment(OrderBean.WAITING);
        doFragment = OrderListFragment.getInstanceFragment(OrderBean.WAITING);
        finishFragment = OrderListFragment.getInstanceFragment(OrderBean.WAITING);
        evaluateFragment = OrderListFragment.getInstanceFragment(OrderBean.WAITING);
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
