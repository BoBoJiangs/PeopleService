package com.yb.peopleservice.view.fragment.user.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.eventbean.EventRecorderBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.order.OrderListPresenter;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;
import com.yb.peopleservice.view.base.LazyLoadFragment;
import com.yb.peopleservice.view.base.LazyLoadListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

public class OrderListFragment extends LazyLoadListFragment {

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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventOrderBean event) {
        presenter.refreshList(false);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter<OrderListBean> queryListUI =
                new ServiceListUIPresenter<OrderListBean>(adapter, swipeRefreshLayout, getContext());

        presenter = new OrderListPresenter(getContext(),status, queryListUI);


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

    @Override
    public void fetchData() {
        presenter.refreshList(true);
    }
}
