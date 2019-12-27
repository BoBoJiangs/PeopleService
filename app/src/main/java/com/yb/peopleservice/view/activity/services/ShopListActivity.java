package com.yb.peopleservice.view.activity.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.model.presenter.user.service.ShopDetailsPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.ServiceDetailsActivity;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺服务列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopListActivity extends BaseListActivity implements ShopDetailsPresenter.IShopCallback {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.collectTV)
    TextView collectTV;
    private ServiceListPresenter presenter;
    private ServiceListAdapter adapter;
    private ServiceListBean bean;
    private ShopDetailsPresenter detailsPresenter;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ServiceListAdapter();
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_shop_list;
    }

    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    protected void initData() {
        bean = getIntent().getParcelableExtra(ServiceListBean.class.getName());
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
                .putExtra(ServiceListBean.class.getName(), serviceListBean));
    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter<ShopInfo> queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout, this);

        presenter = new ServiceListPresenter(bean.getShopId(), this, queryListUI, true);
        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        detailsPresenter = new ShopDetailsPresenter(this, this);
        detailsPresenter.getShopDetails(bean.getShopId());
        initQueryListUI();
        return presenter;
    }


    @Override
    public void getDataSuccess(ShopInfo data) {
        nameTV.setText(data.getName());
        ImageLoaderUtil.loadServerImage(this,data.getHeadImg(),imageView);
    }

    @Override
    public void getDataFail() {

    }


    @OnClick(R.id.collectTV)
    public void onViewClicked() {
    }
}
