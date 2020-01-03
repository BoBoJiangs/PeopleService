package com.yb.peopleservice.view.fragment.user.favorite;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.FavoritePresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.adapter.order.OrderListAdapter;
import com.yb.peopleservice.view.adapter.user.FavoriteShopAdapter;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 类描述: 收藏的服务
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/2  14:55
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FavoriteServiceFragment extends BaseListFragment {

    private FavoriteShopAdapter adapter;
    private FavoritePresenter<FavoriteBean> presenter;

    public static Fragment getInstanceFragment() {
        FavoriteServiceFragment fragment = new FavoriteServiceFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new FavoriteShopAdapter();
    }

    @Override
    protected void initData() {
        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
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

        ServiceListUIPresenter<FavoriteBean> queryListUI =
                new ServiceListUIPresenter<FavoriteBean>(adapter, swipeRefreshLayout, getContext());

        presenter = new FavoritePresenter<FavoriteBean>(getContext(), queryListUI, "2");
        presenter.refreshList(true);

    }
}
