package com.yb.peopleservice.view.activity.services;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ServiceInfo;
import com.yb.peopleservice.model.database.bean.ShopInfo;
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
public class ServiceDetailsActivity extends BaseToolbarActivity {
    @BindView(R.id.headUV)
    UtilityView headUV;
    ImageView headIV;
    @BindView(R.id.shopNameUV)
    UtilityView shopNameUV;
    @BindView(R.id.remakeUV)
    UtilityView remakeUV;
    @BindView(R.id.nameUV)
    UtilityView nameUV;
    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    @BindView(R.id.addressUV)
    UtilityView addressUV;

    @BindView(R.id.idCardUV)
    UtilityView idCardUV;
    @BindView(R.id.cardFaceIV)
    ImageView cardFaceIV;
    @BindView(R.id.addFaceTV)
    TextView addFaceTV;
    @BindView(R.id.cardFaceFL)
    FrameLayout cardFaceFL;
    @BindView(R.id.cardBackIV)
    ImageView cardBackIV;
    @BindView(R.id.addBackTV)
    TextView addBackTV;
    @BindView(R.id.cardBackFL)
    FrameLayout cardBackFL;
    @BindView(R.id.licenseIV)
    ImageView licenseIV;
    @BindView(R.id.licenseTV)
    TextView licenseTV;
    @BindView(R.id.licenseFL)
    FrameLayout licenseFL;
    @BindView(R.id.sureBtn)
    Button sureBtn;
    @BindView(R.id.titleLicenseTV)
    TextView titleLicenseTV;
    private String headUrl;//头像
    private String cardFaceUrl;//身份证正面
    private String cardBackUrl;//身份证背面
    private String licenseUrl;//营业执照
    private ServiceInfo shopInfo;
    private ApplyShopPresenter presenter;

    @Override
    public String getTitleName() {
        return "认证信息";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_shop_apply;
    }

    @Override
    protected void initData() {
        shopInfo = (ServiceInfo) getIntent().getSerializableExtra(ShopInfo.class.getName());
        addressUV.getRightImageView().setVisibility(View.INVISIBLE);
        headIV = headUV.getRightImageView();
        shopNameUV.setVisibility(View.GONE);
        remakeUV.setVisibility(View.GONE);
        licenseFL.setVisibility(View.GONE);
        titleLicenseTV.setVisibility(View.GONE);
        addressUV.setVisibility(View.GONE);
        nameUV.setTitleText("姓名　　　");
        phoneUV.setTitleText("联系电话　");
        headUV.setTitleText("头像");
        if (shopInfo != null) {
            shopNameUV.setContentText(shopInfo.getName());
            remakeUV.setContentText(shopInfo.getIntroduction());
            nameUV.setContentText(shopInfo.getName());
            phoneUV.setContentText(shopInfo.getPhone());
            idCardUV.setContentText(shopInfo.getIdCardNumber());
            ImageLoaderUtil.loadServerImage(this, shopInfo.getHeadImg(), headIV);
            ImageLoaderUtil.loadServerImage(this, shopInfo.getIdCardImgFront(), cardFaceIV, false);
            ImageLoaderUtil.loadServerImage(this, shopInfo.getIdCardImgBack(), cardBackIV, false);
        }
        setUVType();
    }

    private void setUVType() {
        addFaceTV.setVisibility(View.INVISIBLE);
        addBackTV.setVisibility(View.GONE);
        licenseTV.setVisibility(View.GONE);
        sureBtn.setVisibility(View.GONE);
        shopNameUV.setEnabled(false);
        addressUV.setEnabled(false);
        remakeUV.setEnabled(false);
        nameUV.setEnabled(false);
        phoneUV.setEnabled(false);
        idCardUV.setEnabled(false);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.cardFaceFL, R.id.cardBackFL, R.id.licenseFL, R.id.headUV,
            R.id.sureBtn, R.id.addressUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addressUV:

                break;
            case R.id.headUV:

                break;
            case R.id.cardFaceFL:
                break;
            case R.id.cardBackFL:
                break;
            case R.id.licenseFL:
                break;
        }
    }

}
