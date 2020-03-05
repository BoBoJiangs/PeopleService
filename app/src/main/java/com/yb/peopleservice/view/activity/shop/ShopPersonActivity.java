package com.yb.peopleservice.view.activity.shop;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.shop.PersonListBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.shop.PersonPresenter;
import com.yb.peopleservice.view.adapter.shop.ShopPersonAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

import cn.sts.base.presenter.AbstractPresenter;

/**
 * 类描述:店铺下的服务人员列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/24  14:49
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopPersonActivity extends BaseListActivity {
    private PersonPresenter presenter;
    private ShopPersonAdapter adapter;
    private OrderBean bean;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ShopPersonAdapter(this,bean.getId());
    }

    @Override
    public String getTitleName() {
        return "指派人员";
    }

    @Override
    public void initView() {
        bean = getIntent().getParcelableExtra(OrderBean.class.getName());
        if (bean == null) {
            return;
        }
        super.initView();
    }

    @Override
    protected void initData() {

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
        PersonListBean serviceListBean = adapter.getItem(position);

    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout,this);

        presenter = new PersonPresenter(this, queryListUI);
        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        initQueryListUI();
        return presenter;
    }
}
