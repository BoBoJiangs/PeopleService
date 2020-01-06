package com.yb.peopleservice.view.fragment.user.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.FavoritePresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.activity.services.ServiceDetailsActivity;
import com.yb.peopleservice.view.activity.services.ShopListActivity;
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
    public final static String SERVICE_TYPE = "1";//服务
    public final static String SHOP_TYPE = "2";//店铺
    public final static String CANCEL_TYPE = "3";
    private FavoriteShopAdapter adapter;
    private FavoritePresenter<FavoriteBean> presenter;
    private String type;// 收藏类型 1:服务 2：店铺

    public static Fragment getInstanceFragment(String type) {
        FavoriteServiceFragment fragment = new FavoriteServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new FavoriteShopAdapter();
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }

        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter a, View view, int position) {
                FavoriteBean favoriteBean = adapter.getItem(position);
                ShopInfo shopInfo = favoriteBean.getShop();
                ServiceListBean serviceListBean = favoriteBean.getData();
                if (shopInfo != null) {
                    startActivity(new Intent(getActivity(), ShopListActivity.class)
                            .putExtra(ShopInfo.class.getName(),shopInfo));
                }
                if (serviceListBean != null) {
                    startActivity(new Intent(getActivity(), ServiceDetailsActivity.class)
                            .putExtra(ServiceListBean.class.getName(),shopInfo));
                }
            }
        });
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

    @Override
    public void onClickItem(BaseQuickAdapter a, View view, int position) {

    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {

        ServiceListUIPresenter<FavoriteBean> queryListUI =
                new ServiceListUIPresenter<FavoriteBean>(adapter, swipeRefreshLayout, getContext());

        presenter = new FavoritePresenter<FavoriteBean>(getContext(), queryListUI, type);
        presenter.refreshList(true);

    }
}
