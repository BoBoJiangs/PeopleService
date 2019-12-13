package com.yb.peopleservice.view.fragment.shop;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yb.peopleservice.R;
import com.yb.peopleservice.view.activity.shop.ApplyShopActivity;

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
public class ShopFragment extends BaseFragment {

    @BindView(R.id.photoIV)
    ImageView photoIV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.shopInfoLL)
    LinearLayout shopInfoLL;
    @BindView(R.id.profitLL)
    LinearLayout profitLL;

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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.shopInfoLL, R.id.profitLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopInfoLL:
                startActivity(new Intent(getContext(), ApplyShopActivity.class));
                break;
            case R.id.profitLL:
                break;
        }
    }
}
