package com.yb.peopleservice.view.fragment.user.details;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.EvaluateBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.EvaluatePresenter;
import com.yb.peopleservice.model.presenter.user.service.FavoritePresenter;
import com.yb.peopleservice.view.adapter.EvaluateAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务评价
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/2 16:56
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EvaluateFragment extends BaseListFragment implements OnTabSelectListener {
    private String[] mTitles = {"全部", "好评", "中评","差评","有图"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    private EvaluateAdapter adapter;
    private EvaluatePresenter presenter;

    ServiceListBean serviceInfo;

    public static Fragment getInstanceFragment(ServiceListBean serviceInfo) {
        EvaluateFragment fragment = new EvaluateFragment();
        if (serviceInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ServiceListBean.class.getName(), serviceInfo);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new EvaluateAdapter();
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_evaluate;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            serviceInfo = getArguments().getParcelable(ServiceListBean.class.getName());
        }
        super.initView();
    }

    @Override
    protected void initData() {
        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
        initTabLayout();
    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity> titleList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            TabEntity tabEntity = new TabEntity(mTitles[i]);
            titleList.add(tabEntity);
        }
        commonTabLayout.setTabData(titleList);
        commonTabLayout.setOnTabSelectListener(this);
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

        ServiceListUIPresenter<EvaluateBean> queryListUI =
                new ServiceListUIPresenter<>(adapter, swipeRefreshLayout, getContext());

        presenter = new EvaluatePresenter(getContext(), queryListUI, serviceInfo.getId());
        presenter.refreshList(true);

    }

    @Override
    public void onTabSelect(int position) {
        switch (position){
            case 0:
                presenter.setLevel("");
                break;
            case 1:
                presenter.setLevel("5");
                break;
            case 2:
                presenter.setLevel("3");
                break;
            case 3:
                presenter.setLevel("1");
                break;
            case 4:
                presenter.setLevel("10");
                break;
        }
    }

    @Override
    public void onTabReselect(int position) {

    }
}
