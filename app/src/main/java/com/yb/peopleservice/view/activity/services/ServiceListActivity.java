package com.yb.peopleservice.view.activity.services;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.activity.ServiceDetailsActivity;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.presenter.QueryListUIPresenter;

/**
 * 类描述:服务列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/24  14:49
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListActivity extends BaseListActivity {
    private ServiceListPresenter presenter;
    private ServiceListAdapter adapter;
    private ClassifyListBean bean;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ServiceListAdapter();
    }

    @Override
    public String getTitleName() {
        return bean.getName();
    }

    @Override
    protected void initData() {
        bean = getIntent().getParcelableExtra(ClassifyListBean.class.getName());
        if (bean == null) {
            return;
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
        startActivity(new Intent(this, ServiceDetailsActivity.class)
                .putExtra(ServiceListBean.class.getName(),serviceListBean));
    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter<ShopInfo> queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout,this);

        presenter = new ServiceListPresenter(bean.getId(), this, queryListUI);
        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        initQueryListUI();
        return presenter;
    }
}
