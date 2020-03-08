package com.yb.peopleservice.view.fragment.shop;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.model.bean.shop.BalanceBean;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.model.presenter.shop.ShopInfoPresenter;
import com.yb.peopleservice.push.TagAliasOperatorHelper;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.common.MyIncomeActivity;
import com.yb.peopleservice.view.activity.shop.ApplyShopActivity;
import com.yb.peopleservice.view.activity.shop.ApplyDetailsActivity;
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

import static com.yb.peopleservice.push.TagAliasOperatorHelper.ACTION_SET;

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
    @BindView(R.id.photoIV)
    ImageView photoIV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.shopInfoLL)
    LinearLayout shopInfoLL;
    @BindView(R.id.profitLL)
    LinearLayout profitLL;
    @BindView(R.id.emptyLL)
    LinearLayout emptyLL;
    @BindView(R.id.applyBtn)
    Button applyBtn;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    private ShopInfoPresenter presenter;
    private ShopInfo shopInfo;

    public static Fragment getInstanceFragment() {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    @Override
    public int viewResID() {
        return R.layout.shop_fragment;
    }

    @Override
    protected void initView() {
        setOnRefreshListener();
    }

    @Override
    protected void initData() {

        swipeRefreshLayout.setRefreshing(true);
        presenter = new ShopInfoPresenter(getContext(), this);
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

    @OnClick({R.id.shopInfoLL, R.id.profitLL, R.id.applyBtn, R.id.exitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopInfoLL:
                startActivity(new Intent(getContext(), ApplyDetailsActivity.class)
                        .putExtra(ShopInfo.class.getName(), shopInfo));

                break;
            case R.id.profitLL:
                startActivity(new Intent(getContext(), MyIncomeActivity.class)
                        .putExtra("type", BalanceBean.STORE));
                break;
            case R.id.applyBtn:
                startActivityForResult(new Intent(getContext(), ApplyShopActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
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
    public void shopInfoSuccess(MyShop myShop) {
        swipeRefreshLayout.setRefreshing(false);
        ShopInfo data = myShop.getShop();
        if (data != null) {
            new ChatPresenter().getUserInfo(AppUtils.formatID(data.getId()),data.getName());
            shopInfo = data;
            ImageLoaderUtil.loadServerCircleImage(getContext(), data.getHeadImg(), photoIV);
            nameTV.setText(data.getName());
            if (data.getStatus() == 1) {
                shopInfoLL.setVisibility(View.VISIBLE);
                profitLL.setVisibility(View.VISIBLE);
                emptyLL.setVisibility(View.GONE);
            } else {
                shopInfoLL.setVisibility(View.INVISIBLE);
                profitLL.setVisibility(View.INVISIBLE);
                emptyLL.setVisibility(View.VISIBLE);
            }
            switch (data.getStatus()) {
                case 0:
                    applyBtn.setVisibility(View.INVISIBLE);
                    remakeTV.setText("店铺已被禁用,请联系管理员！");
                    break;
                case 2:
                    applyBtn.setVisibility(View.VISIBLE);
                    applyBtn.setText("申请认证店铺");
                    remakeTV.setText("审核通过后即可发布服务！");
                    break;
                case 3:
                    applyBtn.setVisibility(View.INVISIBLE);
                    remakeTV.setText("店铺审核中！");
                    break;
                case 4:
                    applyBtn.setVisibility(View.VISIBLE);
                    applyBtn.setText("申请认证店铺");
                    remakeTV.setText("审核意见：" + data.getMessage());
                    break;
            }

        }
        setAlias(myShop);
        setTags(myShop);
    }


    private void setAlias(MyShop data) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        LogUtils.e(data.getId().replace("-", ""));
        tagAliasBean.alias = data.getId().replace("-", "");
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getContext(), 1, tagAliasBean);
    }

    private void setTags(MyShop data) {
        Set<String> tags = new HashSet<>();
        tags.add("shop");
        if (!StringUtils.isEmpty(data.getShopId())) {
            tags.add(data.getShopId().replace("-", ""));
        }
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.tags = tags;
        TagAliasOperatorHelper.getInstance().handleAction(getContext(), 1, tagAliasBean);
    }

    @Override
    public void shopInfoFail() {
        swipeRefreshLayout.setRefreshing(false);
    }


}
