package com.yb.peopleservice.view.fragment.user.classify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.activity.services.ServiceDetailsActivity;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;
import com.yb.peopleservice.view.base.LazyLoadListFragment;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 类描述:服务列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/24  14:49
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListFragment extends LazyLoadListFragment {
    private ServiceListPresenter presenter;
    private ServiceListAdapter adapter;
    private ClassifyListBean bean;

    public static Fragment getInstanceFragment(ClassifyListBean bean) {
        ServiceListFragment fragment = new ServiceListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ClassifyListBean.class.getName(), bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ServiceListAdapter();
    }



    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            bean = getArguments().getParcelable(ClassifyListBean.class.getName());
            if (bean == null) {
                return;
            }
        }

        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
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
    public void onClickItem(BaseQuickAdapter a, View view, int position) {
        ServiceListBean serviceListBean = adapter.getItem(position);
        startActivity(new Intent(getContext(), ServiceDetailsActivity.class)
                .putExtra(ServiceListBean.class.getName(), serviceListBean));
    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter<ShopInfo> queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout, getContext());

        presenter = new ServiceListPresenter(bean.getId(), getContext(), queryListUI,
                ServiceListPresenter.SERVICE_TYPE);


    }

    @Override
    protected AbstractPresenter createPresenter() {
        initQueryListUI();
        return presenter;
    }

    @Override
    public void fetchData() {
        presenter.refreshList(true);
    }
}
