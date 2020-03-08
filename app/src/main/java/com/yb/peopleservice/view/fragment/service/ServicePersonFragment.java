package com.yb.peopleservice.view.fragment.service;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.BalanceBean;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.presenter.shop.ServiceInfoPresenter;
import com.yb.peopleservice.push.TagAliasOperatorHelper;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.common.MyIncomeActivity;
import com.yb.peopleservice.view.activity.common.ShopDetailsActivity;
import com.yb.peopleservice.view.activity.services.ApplyServiceActivity;
import com.yb.peopleservice.view.activity.services.CertificationDetailsActivity;
import com.yb.peopleservice.view.activity.services.StoreEntryActivity;
import com.yb.peopleservice.view.base.LazyLoadFragment;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;
import cn.sts.base.view.widget.AppDialog;
import cn.sts.base.view.widget.UtilityView;

import static com.yb.peopleservice.push.TagAliasOperatorHelper.ACTION_SET;

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
    @BindView(R.id.shopInfoUV)
    UtilityView shopInfoUV;
    @BindView(R.id.profitUV)
    UtilityView profitUV;
    @BindView(R.id.shopInUV)
    UtilityView shopInUV;
    @BindView(R.id.emptyLL)
    LinearLayout emptyLL;
    @BindView(R.id.rootLL)
    LinearLayout rootLL;
    @BindView(R.id.applyBtn)
    Button applyBtn;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    private ServiceInfoPresenter presenter;
    private MyShop myShop;
    private ServiceInfo serviceInfo;

    public static Fragment getInstanceFragment() {
        ServicePersonFragment fragment = new ServicePersonFragment();
        return fragment;
    }

    @Override
    public int viewResID() {
        return R.layout.service_personal_fragment;
    }

    @Override
    protected void initView() {
        setOnRefreshListener();
    }

    @Override
    protected void initData() {
        rootLL.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        presenter = new ServiceInfoPresenter(getContext(), this);
        presenter.getServiceInfo();
        presenter.getServiceMyShop();
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
                    presenter.getServiceMyShop();
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

    @OnClick({R.id.shopInfoUV, R.id.profitUV, R.id.applyBtn, R.id.shopInUV,R.id.exitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopInfoUV:
                if (serviceInfo != null) {
                    startActivity(new Intent(getContext(), CertificationDetailsActivity.class)
                            .putExtra(ServiceInfo.class.getName(), serviceInfo));
                }

                break;
            case R.id.profitUV:
                startActivity(new Intent(getContext(), MyIncomeActivity.class)
                        .putExtra("type", BalanceBean.PRRSONAL));
                break;
            case R.id.applyBtn:
                startActivityForResult(new Intent(getContext(), ApplyServiceActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.shopInUV:
                if (shopInUV.getContentText().contains("我的店铺")) {
                    myShop.setType(MyShop.SHOP_DETAILS);
                    startActivityForResult(new Intent(getContext(), ShopDetailsActivity.class)
                            .putExtra(MyShop.class.getName(), myShop), RequestCodeConstant.BASE_REQUEST);
                } else {
                    startActivity(new Intent(getContext(), StoreEntryActivity.class));
                }

                break;
            case R.id.exitBtn:
                exit();
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
                        new LogoutPresenter(getContext(),null).logout();
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

    @Override
    public void getMyShopSuccess(MyShop info) {
        myShop = info;
        shopInUV.setContentText("　入驻店铺");
        if (info != null) {
            shopInUV.setContentText("　我的店铺");
        }
    }

    @Override
    public void getMyShopFail() {
        shopInUV.setContentText("　入驻店铺");
    }

    @Override
    public void serviceInfoSuccess(ServiceInfo data) {
        if (data != null) {
            new ChatPresenter().getUserInfo(AppUtils.formatID(data.getId()),
                    data.getName());
            this.serviceInfo = data;
            swipeRefreshLayout.setRefreshing(false);
            rootLL.setVisibility(View.VISIBLE);
            if (data != null) {
                ImageLoaderUtil.loadServerCircleImage(getContext(), data.getHeadImg(), photoIV);
                nameTV.setText(data.getName());
                if (data.getStatus() == 1) {
                    shopInfoUV.setVisibility(View.VISIBLE);
                    profitUV.setVisibility(View.VISIBLE);
                    shopInUV.setVisibility(View.VISIBLE);
                    emptyLL.setVisibility(View.GONE);
                } else {
                    shopInfoUV.setVisibility(View.INVISIBLE);
                    profitUV.setVisibility(View.INVISIBLE);
                    shopInUV.setVisibility(View.INVISIBLE);
                    emptyLL.setVisibility(View.VISIBLE);
                }
                switch (data.getStatus()) {
                    case 0:
                        applyBtn.setVisibility(View.INVISIBLE);
                        remakeTV.setText("账号已被禁用,请联系管理员！");
                        break;
                    case 2:
                        applyBtn.setVisibility(View.VISIBLE);
                        applyBtn.setText("申请认证服务人员");
                        remakeTV.setText("审核通过后即可发布服务！");
                        break;
                    case 3:
                        applyBtn.setVisibility(View.INVISIBLE);
                        remakeTV.setText("认证审核中！");
                        break;
                    case 4:
                        applyBtn.setVisibility(View.VISIBLE);
                        applyBtn.setText("申请认证服务人员");
                        remakeTV.setText("审核不通过：" + data.getMessage());
                        break;
                }
            }
            setAlias(data);
            setTags(data);
        }
    }


    private void setAlias(ServiceInfo data) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        LogUtils.e(data.getId().replace("-", ""));
        tagAliasBean.alias = data.getId().replace("-", "");
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getContext(), 1, tagAliasBean);
    }

    private void setTags(ServiceInfo data) {
        Set<String> tags = new HashSet<>();
        tags.add("staff");
        if (!StringUtils.isEmpty(data.getShopId())) {
            tags.add(data.getShopId().replace("-", ""));
        }
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.tags = tags;
        TagAliasOperatorHelper.getInstance().handleAction(getContext(), 1, tagAliasBean);
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
            presenter.getServiceMyShop();
        }
    }


}
