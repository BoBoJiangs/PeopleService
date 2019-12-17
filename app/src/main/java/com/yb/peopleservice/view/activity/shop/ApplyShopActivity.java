package com.yb.peopleservice.view.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.database.bean.ShopInfo;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.shop.ApplyShopPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺认证
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 14:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ApplyShopActivity extends BaseToolbarActivity implements UploadFilePresenter.IViewUploadFile, ApplyShopPresenter.IApplyCallback {
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
    private String headUrl;//头像
    private String cardFaceUrl;//身份证正面
    private String cardBackUrl;//身份证背面
    private String licenseUrl;//营业执照
    private UploadFilePresenter uploadFilePresenter;
    private ShopInfo shopInfo;
    private ApplyShopPresenter presenter;

    @Override
    public String getTitleName() {
        return "店铺认证";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_shop_apply;
    }

    @Override
    protected void initData() {
        shopInfo = new ShopInfo();
        headIV = headUV.getRightImageView();
        headIV.setId(R.id.headImg);
        uploadFilePresenter = new UploadFilePresenter(this, this);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter = new ApplyShopPresenter(this, this);
    }


    @OnClick({R.id.cardFaceFL, R.id.cardBackFL, R.id.licenseFL, R.id.headUV,
            R.id.sureBtn, R.id.addressUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addressUV:
                startActivityForResult(new Intent(this, SearchMapActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.headUV:
                choiceImg(headIV);

//                ImagePicker.withMulti(new WeChatPresenter())
//                        .setMaxCount(1)
//                        .showCamera(true)//显示拍照
//                        .mimeTypes(MimeType.ofImage())
//                        .setSelectMode(SelectMode.MODE_SINGLE)
//                        .cropSaveInDCIM(false)
//                        .cropAsCircle()
//                        .cropRectMinMargin(SizeUtils.dp2px(80))//设置剪裁边框间距
//                        //调用剪裁
//                        .crop(this, new OnImagePickCompleteListener() {
//                            @Override
//                            public void onImagePickComplete(ArrayList<ImageItem> items) {
//                                if (!items.isEmpty()) {
//                                    headUrl = items.get(0).getPath();
//                                    ImageLoaderUtil.loadLocalCircleImage(getApplicationContext(), headUrl, headIV);
//                                    uploadFilePresenter.launchImage(headUrl, true);
//                                }
//                            }
//                        });
                break;
            case R.id.cardFaceFL:
                choiceImg(cardFaceIV);
                break;
            case R.id.cardBackFL:
                choiceImg(cardBackIV);
                break;
            case R.id.licenseFL:
                choiceImg(licenseIV);
                break;
            case R.id.sureBtn:
                String shopName = shopNameUV.getContentText();
                if (StringUtils.isEmpty(shopName)) {
                    ToastUtils.showLong("店铺名称不能为空");
                    return;
                }

                String remake = remakeUV.getContentText();
                if (StringUtils.isEmpty(remake)) {
                    ToastUtils.showLong("店铺描述不能为空");
                    return;
                }

                String name = nameUV.getContentText();
                if (StringUtils.isEmpty(name)) {
                    ToastUtils.showLong("姓名不能为空");
                    return;
                }

                String idCard = idCardUV.getContentText();
                if (StringUtils.isEmpty(idCard)) {
                    ToastUtils.showLong("身份证号不能为空");
                    return;
                }
                String phone = phoneUV.getContentText();
                if (!(RegexUtils.isMobileExact(phone) || RegexUtils.isTel(phone))) {
                    ToastUtils.showLong("请输入正确的电话号码");
                    return;
                }
                if (StringUtils.isEmpty(headUrl)) {
                    ToastUtils.showLong("请上传" + headUV.getTitleText());
                    return;
                }
                if (StringUtils.isEmpty(cardFaceUrl)) {
                    ToastUtils.showLong("请上传身份证正面照");
                    return;
                }
                if (StringUtils.isEmpty(cardBackUrl)) {
                    ToastUtils.showLong("请上传身份证背面照");
                    return;
                }
                if (StringUtils.isEmpty(licenseUrl)) {
                    ToastUtils.showLong("请上传营业执照");
                    return;
                }
                shopInfo.setName(shopName);
                shopInfo.setIntroduction(remake);
                shopInfo.setManagerName(name);
                shopInfo.setManagerIdcardNumber(idCard);

                List<String> list = new ArrayList<>();
                list.add(cardFaceUrl);
                list.add(cardBackUrl);
                list.add(licenseUrl);
                uploadFilePresenter.launchImage(headUrl, true);
                uploadFilePresenter.launchImage(list, false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResponseCodeConstant.BASE_RESPONSE) {
            if (data != null) {
                PoiItem poiItem = data.getParcelableExtra(PoiItem.class.getName());
                if (poiItem != null) {
                    String address = (poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet())
                            .replaceAll("null", "");
                    addressUV.setContentText(address);
                    shopInfo.setAddress(address);
                    shopInfo.setLocationLongitude(poiItem.getLatLonPoint().getLongitude());
                    shopInfo.setLocationLatitude(poiItem.getLatLonPoint().getLatitude());
                }
            }

        }

    }

    /**
     * 选择图片
     *
     * @param imageView
     */
    private void choiceImg(ImageView imageView) {
        ImagePicker.withMulti(new WeChatPresenter())
                .setMaxCount(1)
                .setSelectMode(SelectMode.MODE_SINGLE)
                .showCamera(true)//显示拍照
                .mimeTypes(MimeType.ofImage())
                .cropRectMinMargin(SizeUtils.dp2px(80))//设置剪裁边框间距
                .pick(this, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        if (!items.isEmpty()) {
                            String url = items.get(0).getPath();
                            ImageLoaderUtil.loadLocalImage(getApplicationContext(), url, imageView);
                            switch (imageView.getId()) {
                                case R.id.cardFaceIV:
                                    cardFaceUrl = url;
                                    addFaceTV.setVisibility(View.GONE);
                                    break;
                                case R.id.cardBackIV:
                                    cardBackUrl = url;
                                    addBackTV.setVisibility(View.GONE);
                                    break;
                                case R.id.licenseIV:
                                    licenseUrl = url;
                                    licenseTV.setVisibility(View.GONE);
                                    break;
                                case R.id.headImg:
                                    headUrl = url;
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void uploadSuccess(List<String> files) {
        if (files.size() == 1) {
            shopInfo.setHeadImg(files.get(0));
        }
        if (files.size() == 3) {
            shopInfo.setManagerIdcardImgFront(files.get(0));
            shopInfo.setManagerIdcardImgBack(files.get(1));
            shopInfo.setBusinessLicenseImg(files.get(2));
        }
        if (!StringUtils.isEmpty(shopInfo.getHeadImg()) &&
                !StringUtils.isEmpty(shopInfo.getBusinessLicenseImg())) {
            presenter.applyShop(shopInfo);
        }
    }

    @Override
    public void uploadFail() {
        ToastUtils.showLong("提交失败,请核对信息后重试");
    }

    @Override
    public void ApplySuccess(ShopInfo data) {
        ToastUtils.showLong("提交成功,等待管理员审核");
        finish();
    }

    @Override
    public void ApplyFail() {
        ToastUtils.showLong("提交失败,请核对信息后重试");
    }
}
