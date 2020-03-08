package com.yb.peopleservice.view.activity.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.view.activity.services.order.ConfirmOrderActivity;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.user.details.EvaluateFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceContentFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceFragment;
import com.yb.peopleservice.view.fragment.user.details.ServiceGroupFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.ScrollViewPager;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;

public class ServiceDetailsActivity extends BaseViewPagerActivity {
    @BindView(R.id.shopTV)
    TextView shopTV;
    @BindView(R.id.customerTV)
    TextView customerTV;
    @BindView(R.id.groupBtn)
    TextView groupBtn;
    @BindView(R.id.orderBtn)
    TextView orderBtn;
    private String[] mTitles = {"服务", "详情", "评价"};
    @BindView(R.id.commonTabLayout)
    public CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ScrollViewPager viewPager;
    private ServiceFragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private ServiceListBean serviceInfo;
    private float payMoney;

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
//        fragment2 = ServiceContentFragment.getInstanceFragment(serviceInfo);
        fragment3 = EvaluateFragment.getInstanceFragment(serviceInfo);
        fragment4 = ServiceGroupFragment.getInstanceFragment(serviceInfo);
        if (serviceInfo.isGrop()) {
            fragmentList.add(fragment4);
        } else {
            fragmentList.add(fragment1);
        }
//        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        return fragmentList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_service_details;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        viewPager.setScroll(true);
        payMoney = serviceInfo.getPrice();
        setBottomText();
    }

    @Override
    public void onPageSelected(int position) {
        if (position==1){
            commonTabLayout.setCurrentTab(2);
        }else{
            commonTabLayout.setCurrentTab(0);
        }
    }

    private void setBottomText() {
        if (serviceInfo.isGrop()) {
            groupBtn.setVisibility(View.VISIBLE);
            groupBtn.setText("¥" + payMoney + "\n单独购买");
            orderBtn.setText("¥" + serviceInfo.getGroupBuyPrice() + "\n发起拼单");
        } else {
            groupBtn.setVisibility(View.GONE);
            if (serviceInfo.isDistance()) {
                orderBtn.setText("立即预约");
            } else {
                orderBtn.setText("¥" + payMoney + "\n立即购买");
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CouponBean bean) {
        if (bean != null) {
            serviceInfo.setCoupons(bean);

            if (bean.getType() == 1) {
                payMoney = serviceInfo.getPrice() - bean.getMoney();
            } else {
                payMoney = serviceInfo.getPrice() * bean.getDiscount() / 10;
            }
            setBottomText();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GroupBean bean) {
        serviceInfo.setGroupType(1);
        serviceInfo.setCoupons(null);
        serviceInfo.setPayMoney(serviceInfo.getGroupBuyPrice());
        serviceInfo.setGroupId(bean.getId());
        Intent intent = new Intent();
        intent.setClass(this, ConfirmOrderActivity.class);
        intent.putExtra(ServiceListBean.class.getName(), serviceInfo);
        startActivity(intent);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return null;
    }

    @OnClick({R.id.shopTV, R.id.groupBtn, R.id.orderBtn,R.id.customerTV})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.shopTV:
                intent.setClass(this, ShopListActivity.class);

                break;
            case R.id.groupBtn:
                intent.setClass(this, ConfirmOrderActivity.class);
                serviceInfo.setPayMoney(payMoney);
                serviceInfo.setGroupType(0);
                break;
            case R.id.orderBtn:
                intent.setClass(this, ConfirmOrderActivity.class);
                if (serviceInfo.isGrop()) {
                    serviceInfo.setGroupType(1);
                    serviceInfo.setCoupons(null);
                    serviceInfo.setPayMoney(serviceInfo.getGroupBuyPrice());
                } else {
                    serviceInfo.setGroupType(0);
                    serviceInfo.setPayMoney(payMoney);
                }
                break;
            case R.id.customerTV:
                if (serviceInfo!=null){
                    intent.setClass(this, ChatActivity.class);
                    intent.putExtra(JGApplication.TARGET_ID, AppUtils.formatID(serviceInfo.getShopId()));
                    intent.putExtra(JGApplication.TARGET_APP_KEY, AppConstant.JPUSH_KEY);
                    intent.putExtra(JGApplication.CONV_TITLE,"店铺");
                }else {
                    ToastUtils.showLong("未获取到店铺信息");
                }
                break;
        }
        serviceInfo.setGroupId("");
        intent.putExtra(ServiceListBean.class.getName(), serviceInfo);
        startActivity(intent);

    }

}
