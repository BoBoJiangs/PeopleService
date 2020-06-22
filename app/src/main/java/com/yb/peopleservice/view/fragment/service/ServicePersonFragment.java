package com.yb.peopleservice.view.fragment.service;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.presenter.shop.ServiceInfoPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.common.AboutActivity;
import com.yb.peopleservice.view.activity.common.MessageListActivity;
import com.yb.peopleservice.view.activity.common.ShopDetailsActivity;
import com.yb.peopleservice.view.activity.personal.UpdatePasswordActivity;
import com.yb.peopleservice.view.activity.services.ApplyServiceActivity;
import com.yb.peopleservice.view.activity.services.EditServiceInfoActivity;
import com.yb.peopleservice.view.activity.services.ServiceApplyDetailsActivity;
import com.yb.peopleservice.view.activity.services.ShopEntryActivity;
import com.yb.peopleservice.view.activity.services.StoreEntryActivity;
import com.yb.peopleservice.view.base.LazyLoadFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员个人主页
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 10:33
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServicePersonFragment extends LazyLoadFragment implements ServiceInfoPresenter.IServiceInfoCallback {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.photoIV)
    ImageView photoIV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.applyUV)
    UtilityView applyUV;
    @BindView(R.id.shopInUV)
    UtilityView shopInUV;
    //    @BindView(R.id.emptyLL)
//    LinearLayout emptyLL;
    @BindView(R.id.rootLL)
    LinearLayout rootLL;
    @BindView(R.id.barView)
    TextView barView;
    private ServiceInfoPresenter presenter;
    //    private MyShop myShop;
    private ServiceInfo serviceInfo;

    public static Fragment getInstanceFragment() {
        ServicePersonFragment fragment = new ServicePersonFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int viewResID() {
        return R.layout.service_personal_fragment;
    }

    @Override
    protected void initView() {
        setOnRefreshListener();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        rootLL.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        presenter = new ServiceInfoPresenter(getContext(), this);
        presenter.getServiceInfo();
//        presenter.getServiceMyShop();
        barView.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ServiceInfo infoBean) {
        presenter.getServiceInfo();
    }

    @Override
    public void fetchData() {

    }

    /**
     * 设置下拉刷新
     */
    public void setOnRefreshListener() {
        if (swipeRefreshLayout != null) {
            //打开下拉刷新
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    presenter.getServiceInfo();
//                    presenter.getServiceMyShop();
                }
            });
            //设置下拉刷新旋转颜色
            swipeRefreshLayout.setColorSchemeColors(Color.rgb(30, 144, 255));
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    @OnClick({R.id.applyUV, R.id.shopInUV, R.id.exitBtn, R.id.editCl,
            R.id.aboutUV, R.id.updateUV, R.id.noticeUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editCl:
                startActivity(new Intent(getContext(), EditServiceInfoActivity.class)
                        .putExtra(ServiceInfo.class.getName(), serviceInfo));
                break;
            case R.id.applyUV:
                applyStatus();
                break;
            case R.id.shopInUV:
                if (serviceInfo.getStatus() != 1) {
                    ToastUtils.showLong("请先完成身份认证");
                    return;
                }
                if (serviceInfo.getShop() != null) {
                    startActivity(new Intent(getContext(), ShopEntryActivity.class)
                            .putExtra(ServiceInfo.class.getName(), serviceInfo));
                } else {
                    startActivity(new Intent(getContext(), StoreEntryActivity.class));
                }

                break;
            case R.id.exitBtn:
                exit();
                break;
            case R.id.aboutUV:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
            case R.id.updateUV:
                startActivity(new Intent(getContext(), UpdatePasswordActivity.class));
                break;
            case R.id.noticeUV:
                startActivity(new Intent(getContext(), MessageListActivity.class));
                break;
        }
    }

    /**
     * 判断账号状态 跳转具体页面
     */
    private void applyStatus() {
        switch (serviceInfo.getStatus()) {
            case 1://账号正常
            case 3://待审核
                startActivity(new Intent(getContext(), ServiceApplyDetailsActivity.class)
                        .putExtra(ServiceInfo.class.getName(), serviceInfo));
                break;
            case 2://新用户
            case 4://审核没通过
                startActivityForResult(new Intent(getContext(), ApplyServiceActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
        }

    }

    public void exit() {
        AppDialog appDialog = new AppDialog(getActivity());
        appDialog.title("是否确认退出？")
                .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                        new LogoutPresenter(getContext(), null).logout();
                    }
                });

        appDialog.negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
            @Override
            public void onClick(AppDialog appDialog) {
                appDialog.dismiss();
            }
        });
        appDialog.setCancelable(false);
        appDialog.show();
    }

//    @Override
//    public void getMyShopSuccess(MyShop info) {
//        myShop = info;
//        shopInUV.setContentText("　入驻店铺");
//        if (info != null) {
//            shopInUV.setContentText("　我的店铺");
//        }
//    }
//
//    @Override
//    public void getMyShopFail() {
//        shopInUV.setContentText("　入驻店铺");
//    }

    @Override
    public void serviceInfoSuccess(ServiceInfo data) {
        if (data != null) {

            this.serviceInfo = data;
            swipeRefreshLayout.setRefreshing(false);
            rootLL.setVisibility(View.VISIBLE);
            ImageLoaderUtil.loadServerCircleImage(getContext(), data.getHeadImg(), photoIV);
            nameTV.setText(data.getName());
            phoneTV.setText(data.getPhone());
            switch (data.getStatus()) {
                case 0:
                    applyUV.setContentText("账号已被禁用");
                    break;
                case 1:
                    applyUV.setContentText("已认证");
                    break;
                case 2:
                    applyUV.setContentText("未认证");
                    break;
                case 3:
                    applyUV.setContentText("认证审核中");
                    break;
                case 4:
                    applyUV.setContentText("认证未通过");
                    break;
            }
            shopInUV.setContentText("未入职");
            ShopInfo shopInfo = data.getShop();
            if (shopInfo != null) {
                switch (serviceInfo.getJobStatus()) {
                    case "1"://正常
                        shopInUV.setContentText(shopInfo.getName());
                        break;
                    case "2"://申请入驻审核中
                        shopInUV.setContentText("待审核");
                        break;
                }
            }


        }
    }


    @Override
    public void serviceInfoFail() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResponseCodeConstant.BASE_RESPONSE) {
            presenter.getServiceInfo();
//            presenter.getServiceMyShop();
        }
    }


}
