package com.yb.peopleservice.view.activity.shop;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.presenter.shop.ApplyShopPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 14:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ApplyDetailsActivity extends ApplyShopActivity {
    @BindView(R.id.sureBtn)
    Button sureBtn;
    private ShopInfo shopInfo;
//    private ApplyShopPresenter presenter;

    @Override
    public String getTitleName() {
        return "店铺信息";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_shop_apply;
    }

    @Override
    protected void initData() {
        shopInfo = (ShopInfo) getIntent().getParcelableExtra(ShopInfo.class.getName());
        addressUV.getRightImageView().setVisibility(View.INVISIBLE);
        headIV = headUV.getRightImageView();

        if (shopInfo != null) {
            shopNameUV.setContentText(shopInfo.getName());
            addressUV.setContentText(shopInfo.getAddress());
            remakeUV.setContentText(shopInfo.getIntroduction());
            nameUV.setContentText(shopInfo.getManagerName());
            phoneUV.setContentText(shopInfo.getPhone());
            idCardUV.setContentText(shopInfo.getManagerIdcardNumber());
            bankNumberUV.setContentText(shopInfo.getBackCardNumber());
            bankNameUV.setContentText(shopInfo.getBackName());

            ImageLoaderUtil.loadServerImage(this,shopInfo.getBackgroundImg(),shopBgIV);
            ImageLoaderUtil.loadServerImage(this,shopInfo.getHeadImg(),headIV);
            ImageLoaderUtil.loadServerImage(this,shopInfo.getManagerIdcardImgFront(),cardFaceIV,false);
            ImageLoaderUtil.loadServerImage(this,shopInfo.getManagerIdcardImgBack(),cardBackIV,false);
            ImageLoaderUtil.loadServerImage(this,shopInfo.getBusinessLicenseImg(),licenseIV,false);
        }
        setUVType();
    }

    private void setUVType() {
        sureBtn.setVisibility(View.GONE);
        shopNameUV.getInputEditText().setEnabled(false);
        remakeUV.getInputEditText().setEnabled(false);
        nameUV.getInputEditText().setEnabled(false);
        phoneUV.getInputEditText().setEnabled(false);
        idCardUV.getInputEditText().setEnabled(false);
        bankNameUV.getInputEditText().setEnabled(false);
        bankNumberUV.getInputEditText().setEnabled(false);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @Override
    public void onViewClicked(View view) {

    }
}
