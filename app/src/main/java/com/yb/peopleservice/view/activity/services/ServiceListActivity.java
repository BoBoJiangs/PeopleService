package com.yb.peopleservice.view.activity.services;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;

import static com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter.SERVICE_TYPE;

/**
 * 类描述:服务列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/24  14:49
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceListActivity extends BaseListActivity implements OnTabSelectListener {
    private String[] mTitles = {"默认", "价格", "销量"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    private ServiceListPresenter presenter;
    private ServiceListAdapter adapter;
    private ClassifyListBean bean;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ServiceListAdapter();
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_service_list;
    }

    @Override
    public String getTitleName() {
        if (bean == null) {
            return "团购列表";
        }
        return bean.getName();
    }

    @Override
    protected void initData() {
        bean = getIntent().getParcelableExtra(ClassifyListBean.class.getName());
        if (bean != null) {
            commonTabLayout.setVisibility(View.VISIBLE);
        } else {
            commonTabLayout.setVisibility(View.GONE);
        }
        initTabLayout();

        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity> titleList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            TabEntity tabEntity;
            if (i == 1) {
                tabEntity = new TabEntity(mTitles[i], R.mipmap.icon_price_jia, R.mipmap.icon_price_jian);
            } else {
                tabEntity = new TabEntity(mTitles[i]);
            }
            titleList.add(tabEntity);
        }
        commonTabLayout.setIconGravity(Gravity.RIGHT);
        commonTabLayout.setTabData(titleList);
        commonTabLayout.setOnTabSelectListener(this);
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
        if (bean == null) {
            presenter = new ServiceListPresenter("", this,
                    queryListUI, getIntent().getIntExtra("type", ServiceListPresenter.SERVICE_TYPE));
        } else {
            presenter = new ServiceListPresenter(bean.getId(), this,
                    queryListUI, getIntent().getIntExtra("type", ServiceListPresenter.SERVICE_TYPE));
        }

        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        initQueryListUI();
        return presenter;
    }

    @Override
    public void onTabSelect(int position) {
        switch (position){
            case 0:
                presenter.setOrder(ServiceListPresenter.DEFAULT);
                break;
            case 1:
                presenter.setOrder(ServiceListPresenter.PRICE_LESS);
                break;
            case 2:
                presenter.setOrder(ServiceListPresenter.NUMBER);
                break;
        }
        presenter.refreshList(false);
    }

    @Override
    public void onTabReselect(int position) {
        if(position==1){
            if (presenter.getOrder().equals(ServiceListPresenter.PRICE_LESS)){
                presenter.setOrder(ServiceListPresenter.PRICE_MORE);
            }else{
                presenter.setOrder(ServiceListPresenter.PRICE_LESS);
            }
            presenter.refreshList(false);
        }
    }
}
