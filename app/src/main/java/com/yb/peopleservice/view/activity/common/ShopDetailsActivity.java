package com.yb.peopleservice.view.activity.common;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.presenter.shop.ServiceShopStatePresenter;
import com.yb.peopleservice.model.presenter.user.service.CollectPresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 项目名称:Flower
 * 类描述: 店铺详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/4 17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopDetailsActivity extends BaseToolbarActivity implements
        ServiceShopStatePresenter.IServiceInfoCallback {

    @BindView(R.id.companyNameTV)
    TextView companyNameTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.remakeTV)
    TextView remakeTV;
    @BindView(R.id.sureTV)
    protected TextView sureTV;
    @BindView(R.id.sureBtn)
    protected Button sureBtn;
    @BindView(R.id.stateLL)
    protected LinearLayout stateLL;
    @BindView(R.id.shopBgIV)
    ImageView shopBgIV;
    protected ServiceShopStatePresenter presenter;
    //    private MyShop myShop;
    protected ShopInfo shopInfo;

    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_shop_details;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        shopInfo = (ShopInfo) getIntent().getParcelableExtra(ShopInfo.class.getName());
        if (shopInfo != null) {
            setViewText();
            if (!StringUtils.isEmpty(shopInfo.getBackgroundImg())) {
                Glide.with(this).load(BaseRequestServer.getFileUrl(true) +
                        shopInfo.getBackgroundImg())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(15, 3)))
                        .into(shopBgIV);
            }
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected AbstractPresenter createPresenter() {

        return presenter = new ServiceShopStatePresenter(this, this);
    }


    @OnClick(R.id.sureBtn)
    public void onViewClicked() {
//        if (myShop == null) {
//            return;
//        }
//        if (myShop.getShop() == null) {
//            return;
//        }
//        if (myShop.getStatus().equals("1")) {
//            presenter.unboundShop(myShop.getShop().getId());
//        }else{
//            presenter.cancelaShop(myShop.getShop().getId());
//        }
    }

    protected void setViewText() {
//        if (myShop.getType()==MyShop.SHOP_DETAILS){
//            stateLL.setVisibility(View.VISIBLE);
//        }else{
//            stateLL.setVisibility(View.GONE);
//        }
//        ShopInfo shopInfo = myShop.getShop();
        if (shopInfo != null) {
            titleTV.setText(shopInfo.getName());
            phoneTV.setText("联系电话：" + shopInfo.getPhone());
            addressTV.setText("地址：" + shopInfo.getAddress());
            remakeTV.setText(shopInfo.getIntroduction());
        }

    }

    @OnClick(R.id.phoneTV)
    public void onClick() {
        if (!StringUtils.isEmpty(shopInfo.getPhone())) {
            AppUtils.callPhone(shopInfo.getPhone());
        }
    }


    @Override
    public void onSuccess() {
        ToastUtils.showLong("操作成功");
        finish();
    }

    @Override
    public void onFail() {

    }
}
