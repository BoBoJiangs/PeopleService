package com.yb.peopleservice.view.fragment.user.order;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class OrderTabFragment extends BaseViewPagerFragment {
    protected String[] mTitles = {"全部", "未付款", "进行中", "待评价", "已完成"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private Fragment allFragment;
    private Fragment paymentFragment;
    private Fragment doFragment;
    private Fragment finishFragment;
    private Fragment evaluateFragment;
    private int index;

    public static Fragment getInstanceFragment(int index) {
        OrderTabFragment fragment = new OrderTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKeyConstant.DATA_KEY, index);
        fragment.setArguments(bundle);
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
    protected void initView() {
        super.initView();
    }

    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        allFragment = OrderListFragment.getInstanceFragment(OrderBean.ALL);
        paymentFragment = OrderListFragment.getInstanceFragment(OrderBean.NO_PAY);
        doFragment = OrderListFragment.getInstanceFragment(OrderBean.UNDER_WAY);
        evaluateFragment = OrderListFragment.getInstanceFragment(OrderBean.ASSESS);
        finishFragment = OrderListFragment.getInstanceFragment(OrderBean.COMPLETED);
        fragmentList.add(allFragment);
        fragmentList.add(paymentFragment);
        fragmentList.add(doFragment);
        fragmentList.add(evaluateFragment);
        fragmentList.add(finishFragment);
        return fragmentList;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            index = getArguments().getInt(IntentKeyConstant.DATA_KEY, 0);
        }
        viewPager.setScroll(true);
        viewPager.setCurrentItem(index);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public void fetchData() {
        super.initView();
    }
}
