package com.yb.peopleservice.view.fragment.shop.order;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
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
        mTitles = new String[]{"全部", "代付款", "进行中", "已完成"};
    }

    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        allFragment = OrderListFragment.getInstanceFragment();
        paymentFragment = OrderListFragment.getInstanceFragment();
        doFragment = OrderListFragment.getInstanceFragment();
        finishFragment = OrderListFragment.getInstanceFragment();
        fragmentList.add(allFragment);
        fragmentList.add(paymentFragment);
        fragmentList.add(doFragment);
        fragmentList.add(finishFragment);
        return fragmentList;
    }
}
