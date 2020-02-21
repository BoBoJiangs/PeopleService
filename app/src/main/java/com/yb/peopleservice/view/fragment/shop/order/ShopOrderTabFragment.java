package com.yb.peopleservice.view.fragment.shop.order;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.view.base.BaseViewPagerFragment;
import com.yb.peopleservice.view.fragment.user.order.OrderListFragment;
import com.yb.peopleservice.view.fragment.user.order.OrderTabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class ShopOrderTabFragment extends OrderTabFragment {
    private Fragment allFragment;
    private Fragment paymentFragment;
    private Fragment doFragment;
    private Fragment finishFragment;

    public static Fragment getInstanceFragment() {
        ShopOrderTabFragment fragment = new ShopOrderTabFragment();
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected String[] getTabTitles() {
        mTitles = new String[]{"全部", "待指派", "进行中", "已完成"};
        return mTitles;
    }

    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        allFragment = OrderListFragment.getInstanceFragment(OrderBean.ALL);
        paymentFragment = OrderListFragment.getInstanceFragment(OrderBean.ASSIGN);
        doFragment = OrderListFragment.getInstanceFragment(OrderBean.UNDER_WAY);
        finishFragment = OrderListFragment.getInstanceFragment(OrderBean.COMPLETED);
        fragmentList.add(allFragment);
        fragmentList.add(paymentFragment);
        fragmentList.add(doFragment);
        fragmentList.add(finishFragment);
        return fragmentList;
    }
}
