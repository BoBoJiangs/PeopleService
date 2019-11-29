package com.yb.peopleservice.view.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.ClassifyListBean;
import com.yb.peopleservice.view.adapter.ClassifyAdapter;
import com.yb.peopleservice.view.adapter.ClassifyChildAdapter;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;
import com.yb.peopleservice.view.fragment.order.OrderChildListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;
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
