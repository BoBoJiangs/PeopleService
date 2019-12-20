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

import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.presenter.shop.ShopInfoPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.shop.ApplyShopActivity;
import com.yb.peopleservice.view.activity.shop.ApplyDetailsActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 10:33
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopFragment extends BaseFragment implements ShopInfoPresenter.IShopInfoCallback {
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
        presenter.getShopInfo();

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
        return presenter = new ShopInfoPresenter(getContext(), this);
    }

    @OnClick({R.id.shopInfoLL, R.id.profitLL, R.id.applyBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopInfoLL:
                startActivity(new Intent(getContext(), ApplyDetailsActivity.class)
                        .putExtra(ShopInfo.class.getName(), shopInfo));

                break;
            case R.id.profitLL:
                break;
            case R.id.applyBtn:
                startActivityForResult(new Intent(getContext(), ApplyShopActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
        }
    }

    @Override
    public void shopInfoSuccess(ShopInfo data) {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null) {
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
    }

    @Override
    public void shopInfoFail() {
        swipeRefreshLayout.setRefreshing(false);
    }

}
