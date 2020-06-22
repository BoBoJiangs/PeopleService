package com.yb.peopleservice.view.fragment.shop;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.model.bean.shop.BalanceBean;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.presenter.shop.ShopInfoPresenter;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.common.AboutActivity;
import com.yb.peopleservice.view.activity.common.MessageListActivity;
import com.yb.peopleservice.view.activity.common.MyIncomeActivity;
import com.yb.peopleservice.view.activity.personal.UpdatePasswordActivity;
import com.yb.peopleservice.view.activity.shop.ApplyDetailsActivity;
import com.yb.peopleservice.view.activity.shop.ApplyShopActivity;
import com.yb.peopleservice.view.activity.shop.EditShopInfoActivity;
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
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 10:33
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopFragment extends LazyLoadFragment implements ShopInfoPresenter.IShopInfoCallback {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.barView)
    TextView barView;
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.photoIV)
    ImageView photoIV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.editText)
    TextView editText;
    @BindView(R.id.userInfoRL)
    LinearLayout userInfoRL;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    @BindView(R.id.applyUV)
    UtilityView applyUV;
    @BindView(R.id.profitUV)
    UtilityView profitUV;
    @BindView(R.id.exitBtn)
    TextView exitBtn;
    @BindView(R.id.rootLL)
    LinearLayout rootLL;
    private ShopInfoPresenter presenter;
    private ShopInfo shopInfo;

    public static Fragment getInstanceFragment() {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int viewResID() {
        return R.layout.shop_fragment;
    }

    @Override
    protected void initView() {
        setOnRefreshListener();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        swipeRefreshLayout.setRefreshing(true);
        presenter = new ShopInfoPresenter(getContext(), this);
        presenter.getShopInfo();
        barView.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShopInfo infoBean) {
        presenter.getShopInfo();
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
                    presenter.getShopInfo();
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

    @OnClick({R.id.phoneTV, R.id.applyUV, R.id.profitUV, R.id.exitBtn, R.id.headCL,
            R.id.aboutUV,R.id.updateUV,R.id.noticeUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headCL:
                startActivity(new Intent(getContext(), EditShopInfoActivity.class)
                        .putExtra(ShopInfo.class.getName(), shopInfo));
                break;
            case R.id.phoneTV:
                String phone = phoneTV.getText().toString();
                if (RegexUtils.isMobileSimple(phone)) {
                    AppUtils.callPhone(phone);
                } else {
                    ToastUtils.showLong("电话号码错误");
                }

                break;
            case R.id.applyUV:
                applyStatus();
                break;
            case R.id.profitUV:
                startActivity(new Intent(getContext(), MyIncomeActivity.class)
                        .putExtra("type", BalanceBean.STORE));
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
        switch (shopInfo.getStatus()) {
            case 1://账号正常
            case 3://待审核
                startActivity(new Intent(getContext(), ApplyDetailsActivity.class)
                        .putExtra(ShopInfo.class.getName(), shopInfo));
                break;
            case 2://新用户
            case 4://审核没通过
                startActivityForResult(new Intent(getContext(), ApplyShopActivity.class),
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

    @Override
    public void shopInfoSuccess(ServiceInfo myShop) {
        swipeRefreshLayout.setRefreshing(false);
        ShopInfo data = myShop.getShop();
        if (data != null) {
            new ChatPresenter().getUserInfo(AppUtils.formatID(data.getId()), data.getName());
            shopInfo = data;
            ImageLoaderUtil.loadServerCircleImage(getContext(), data.getHeadImg(), photoIV);
            showShopInfo();
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
                    applyUV.setContentText("待审核");
                    break;
            }

        }
        ChatPresenter.getInstance().setShopAlias(getContext(), myShop);
    }


    /**
     * 显示店铺信息
     */
    private void showShopInfo() {
        nameTV.setText(shopInfo.getName());
        phoneTV.setText(shopInfo.getPhone());
        addressTV.setText("地址：" + shopInfo.getAddress());
        if (StringUtils.isEmpty(shopInfo.getIntroduction())) {
            remakeTV.setVisibility(View.GONE);
        } else {
            remakeTV.setText("店铺介绍：" + shopInfo.getIntroduction());
            remakeTV.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void shopInfoFail() {
        swipeRefreshLayout.setRefreshing(false);
    }


}
