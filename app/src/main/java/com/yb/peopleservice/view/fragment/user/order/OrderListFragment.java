package com.yb.peopleservice.view.fragment.user.order;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

public class OrderListFragment extends BaseListFragment {

    private OrderListAdapter adapter;

    public static Fragment getInstanceFragment() {
        OrderListFragment fragment = new OrderListFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new OrderListAdapter(getActivity());
    }

    @Override
    protected void initData() {
        List<String> listData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listData.add("");
        }
        adapter.setNewData(listData);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
