package com.yb.peopleservice.view.fragment.user.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.order.OrderListPresenter;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

public class OrderListFragment extends BaseListFragment {

    private OrderListAdapter adapter;
    private OrderListPresenter presenter;
    private int status;

    public static Fragment getInstanceFragment(int status) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKeyConstant.DATA_KEY,status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new OrderListAdapter(getActivity());
    }

    @Override
    protected void initData() {
        if (getArguments()!=null){
            status = getArguments().getInt(IntentKeyConstant.DATA_KEY);
        }
        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter<OrderBean> queryListUI =
                new ServiceListUIPresenter<OrderBean>(adapter, swipeRefreshLayout, getContext());

        presenter = new OrderListPresenter(getContext(),status, queryListUI);
        presenter.refreshList(true);

    }

    @Override
    public void onPullRefresh() {
        super.onPullRefresh();
        presenter.refreshList(false);
    }

    @Override
    public void onLoadMoreRequest() {
        super.onLoadMoreRequest();
        presenter.loadMoreList();
    }
}
