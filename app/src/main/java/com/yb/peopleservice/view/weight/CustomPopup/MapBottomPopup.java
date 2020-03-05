package com.yb.peopleservice.view.weight.CustomPopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.cb.ratingbar.CBRatingBar;
import com.lxj.xpopup.core.BottomPopupView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.services.ShopListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 15:31
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MapBottomPopup extends BottomPopupView {
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.careersTV)
    TextView careersTV;
    @BindView(R.id.shopNameTV)
    TextView shopNameTV;
    @BindView(R.id.ratingBar)
    CBRatingBar ratingBar;
    @BindView(R.id.praiseRateTV)
    TextView praiseRateTV;
    @BindView(R.id.contentTV)
    TextView contentTV;
    private Context context;
    private ShopInfo shopInfo;
    private ServiceInfo serviceInfo;

    public MapBottomPopup(@NonNull Context context, ServiceInfo serviceInfo) {
        super(context);
        this.context = context;
        this.serviceInfo = serviceInfo;
    }

    public MapBottomPopup(@NonNull Context context, ShopInfo shopInfo) {
        super(context);
        this.context = context;
        this.shopInfo = shopInfo;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.view_map_bottom;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, getPopupImplView());
        careersTV.setVisibility(View.GONE);
        shopNameTV.setVisibility(View.GONE);
        setServiceInfoData();
    }

    @SuppressLint("SetTextI18n")
    private void setServiceInfoData() {
        if (serviceInfo != null) {
            nameTV.setText(serviceInfo.getName());
            ratingBar.setStarProgress(serviceInfo.getLevel() / 5f * 100);
            praiseRateTV.setText("已接" + serviceInfo.getOrderNumber() + "单 好评" +
                    serviceInfo.getPraiseRate() * 100 + "%");
            contentTV.setText(serviceInfo.getIntroduction());
            ImageLoaderUtil.loadServerCircleImage(context, serviceInfo.getHeadImg(), headImg);
        }
        if (shopInfo != null) {
            nameTV.setText(shopInfo.getName());
            ratingBar.setStarProgress(shopInfo.getLevel() / 5f * 100);
            praiseRateTV.setText("已接" + shopInfo.getOrderNumber() + "单 好评" +
                    shopInfo.getPraiseRate() * 100 + "%");
            contentTV.setText(shopInfo.getIntroduction());
            ImageLoaderUtil.loadServerCircleImage(context, shopInfo.getHeadImg(), headImg);
        }
    }


    @OnClick(R.id.nameTV)
    public void onViewClicked() {
        if (shopInfo != null) {
            ActivityUtils.startActivity(new Intent(context, ShopListActivity.class)
                    .putExtra(ShopInfo.class.getName(),shopInfo));
        }
    }
}
