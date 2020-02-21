package com.yb.peopleservice.view.activity.services;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.view.activity.services.ShopListActivity;
import com.yb.peopleservice.view.activity.services.order.ConfirmOrderActivity;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.user.details.EvaluateFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceContentFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;

public class ServiceDetailsActivity extends BaseViewPagerActivity {
    private String[] mTitles = {"服务", "详情", "评价"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private ServiceFragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private ServiceListBean serviceInfo;

    @Override
    protected View getTabLayout() {
        return commonTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        return mTitles;
    }

    @Override
    protected List<Fragment> getFragmentList() {
        serviceInfo = getIntent().getParcelableExtra(ServiceListBean.class.getName());
        List<Fragment> fragmentList = new ArrayList<>();
        fragment1 = (ServiceFragment) ServiceFragment.getInstanceFragment(serviceInfo);
        fragment2 = ServiceContentFragment.getInstanceFragment(serviceInfo);
        fragment3 = EvaluateFragment.getInstanceFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        return fragmentList;
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_service_details;
    }

    @Override
    protected void initData() {
        viewPager.setScroll(true);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return null;
    }

    @OnClick({R.id.shopTV, R.id.orderBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopTV:
                startActivity(new Intent(this, ShopListActivity.class)
                        .putExtra(ServiceListBean.class.getName(), serviceInfo));
                break;
            case R.id.orderBtn:

                startActivity(new Intent(this, ConfirmOrderActivity.class)
                        .putExtra(ServiceListBean.class.getName(), serviceInfo));
                break;
        }


    }
}
