package com.yb.peopleservice.view.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.order.MyCouponBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.personal.CouponsPresenter;
import com.yb.peopleservice.model.presenter.user.service.FavoritePresenter;
import com.yb.peopleservice.view.activity.services.ServiceDetailsActivity;
import com.yb.peopleservice.view.adapter.user.CouponAdapter;
import com.yb.peopleservice.view.adapter.user.FavoriteServiceAdapter;
import com.yb.peopleservice.view.adapter.user.MyCouponAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

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
public class MyCouponListActivity extends BaseListActivity {
    private MyCouponAdapter adapter;
    private CouponsPresenter<MyCouponBean> presenter;


    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new MyCouponAdapter(false);
    }

    @Override
    protected void initData() {
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

    @Override
    public void onClickItem(BaseQuickAdapter a, View view, int position) {

    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {

        ServiceListUIPresenter<MyCouponBean> queryListUI =
                new ServiceListUIPresenter<>(adapter, swipeRefreshLayout, this);

        presenter = new CouponsPresenter(this, queryListUI);
        presenter.refreshList(true);

    }

    @Override
    public String getTitleName() {
        return "我的优惠券";
    }
}
