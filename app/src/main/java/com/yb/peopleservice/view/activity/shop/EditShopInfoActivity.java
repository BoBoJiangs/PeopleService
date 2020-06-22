package com.yb.peopleservice.view.activity.shop;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.shop.EditShopPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 14:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EditShopInfoActivity extends ApplyShopActivity implements EditShopPresenter.IEditCallback {
    @BindView(R.id.sureBtn)
    Button sureBtn;
    private ShopInfo shopInfo;
    //    private ApplyShopPresenter presenter;
    private EditShopPresenter editShopPresenter;

    @Override
    public String getTitleName() {
        return "编辑";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_shop_apply;
    }

    @Override
    protected void initData() {
        super.initData();
        editShopPresenter = new EditShopPresenter(this, this);
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

            if (!StringUtils.isEmpty(shopInfo.getBackgroundImg())) {
                ImageLoaderUtil.loadServerImage(this, shopInfo.getBackgroundImg(), shopBgIV);
            }
            if (!StringUtils.isEmpty(shopInfo.getHeadImg())) {
                ImageLoaderUtil.loadServerCircleImage(this, shopInfo.getHeadImg(), headIV);
            }

        }
        setUVType();
    }

    private void setUVType() {
        shopNameUV.setVisibility(View.GONE);
        addressUV.setVisibility(View.GONE);
        nameUV.setVisibility(View.GONE);
        phoneUV.setVisibility(View.GONE);
        idCardUV.setVisibility(View.GONE);
        cardFaceFL.setVisibility(View.GONE);
        cardBackFL.setVisibility(View.GONE);
        licenseFL.setVisibility(View.INVISIBLE);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addressUV:
                startActivityForResult(new Intent(this, SearchMapActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.headUV:
                choiceImg(headIV);

                break;
            case R.id.shopBgIV:
                choiceImg(shopBgIV);
                break;
            case R.id.sureBtn:

                String address = addressUV.getContentText();
                if ("请选择".equals(address)) {
                    address = "";
                }

                String bankNumber = bankNumberUV.getContentText();

                String bankName = bankNameUV.getContentText();

                String remake = remakeUV.getContentText();


                shopInfo.setAddress(address);
                shopInfo.setBackCardNumber(bankNumber);
                shopInfo.setBackName(bankName);
                shopInfo.setIntroduction(remake);

                List<String> list = new ArrayList<>();
                if (!StringUtils.isEmpty(headUrl)) {
                    list.add(headUrl);
                }
                if (!StringUtils.isEmpty(shopBgUrl)) {
                    list.add(shopBgUrl);
                }
                if (list.isEmpty()) {
                    editShopPresenter.editShop(shopInfo);
                } else {
                    showProgressDialog();
                    uploadFilePresenter.launchImage(list, true);
                }
                ChatPresenter.getInstance().updateUser("",headUrl);
                break;
        }
    }

    @Override
    public void uploadSuccess(List<String> files, boolean isPublic) {
        for (String url : files) {
            if (FileUtils.isFile(headUrl)){
                shopInfo.setHeadImg(url);
                continue;
            }
            if (FileUtils.isFile(shopBgUrl)){
                shopInfo.setBackgroundImg(url);
            }
        }
        dissmissProgressDialog();
        editShopPresenter.editShop(shopInfo);
    }

    @Override
    public void editSuccess(ShopInfo data) {
        EventBus.getDefault().post(shopInfo);
        finish();
    }

    @Override
    public void editFail() {

    }
}
