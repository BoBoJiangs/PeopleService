package com.yb.peopleservice.view.activity.services;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.ShopInfo;
import com.yb.peopleservice.model.presenter.shop.EntryListPresenter;
import com.yb.peopleservice.view.adapter.shop.EntryListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.presenter.QueryListUIPresenter;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员选择入驻店铺
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class StoreEntryActivity extends BaseListActivity {
    private EntryListPresenter presenter;
    private EntryListAdapter adapter;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new EntryListAdapter();
    }

    @Override
    public String getTitleName() {
        return null;
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


    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        QueryListUIPresenter<ShopInfo> queryListUI =
                new QueryListUIPresenter<>(adapter, swipeRefreshLayout);

        presenter = new EntryListPresenter(this, queryListUI);
        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        initQueryListUI();
        return presenter;
    }
}
