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

import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.ServiceInfo;
import com.yb.peopleservice.model.database.bean.ShopInfo;
import com.yb.peopleservice.model.presenter.shop.ServiceInfoPresenter;
import com.yb.peopleservice.model.presenter.shop.ShopInfoPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.services.ApplyServiceActivity;
import com.yb.peopleservice.view.activity.services.StoreEntryActivity;
import com.yb.peopleservice.view.activity.shop.ApplyShopActivity;
import com.yb.peopleservice.view.activity.shop.ShopDetailsActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员个人主页
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 10:33
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServicePersonFragment extends BaseFragment implements ServiceInfoPresenter.IServiceInfoCallback {
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
    @BindView(R.id.shopInLL)
    LinearLayout shopInLL;
    @BindView(R.id.emptyLL)
    LinearLayout emptyLL;
    @BindView(R.id.applyBtn)
    Button applyBtn;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    private ServiceInfoPresenter presenter;
    private ServiceInfo shopInfo;

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

        swipeRefreshLayout.setRefreshing(true);
        presenter.getServiceInfo();
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
                }
            });
            //设置下拉刷新旋转颜色
            swipeRefreshLayout.setColorSchemeColors(Color.rgb(30, 144, 255));
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter = new ServiceInfoPresenter(getContext(), this);
    }

    @OnClick({R.id.shopInfoLL, R.id.profitLL, R.id.applyBtn, R.id.shopInLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopInfoLL:

                break;
            case R.id.profitLL:
                break;
            case R.id.applyBtn:
                startActivityForResult(new Intent(getContext(), ApplyServiceActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.shopInLL:
                startActivity(new Intent(getContext(), StoreEntryActivity.class));
                break;
        }
    }

    @Override
    public void serviceInfoSuccess(ServiceInfo data) {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null) {
            shopInfo = data;
            ImageLoaderUtil.loadServerCircleImage(getContext(), data.getHeadImg(), photoIV);
            nameTV.setText(data.getName());
            if (data.getStatus() == 1) {
                shopInfoLL.setVisibility(View.VISIBLE);
                profitLL.setVisibility(View.VISIBLE);
                shopInLL.setVisibility(View.VISIBLE);
                emptyLL.setVisibility(View.GONE);
            } else {
                shopInfoLL.setVisibility(View.INVISIBLE);
                profitLL.setVisibility(View.INVISIBLE);
                shopInLL.setVisibility(View.INVISIBLE);
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
                    remakeTV.setText("审核意见：" + data.getMessage());
                    break;
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
        if (resultCode == ResponseCodeConstant.BASE_RESPONSE){
            presenter.getServiceInfo();
        }
    }
}