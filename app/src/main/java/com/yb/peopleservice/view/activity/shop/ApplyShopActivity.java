package com.yb.peopleservice.view.activity.shop;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.reflect.TypeToken;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.JsonBean;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.shop.ApplyShopPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;
import jiguang.chat.utils.pinyin.CharacterParser;

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
    @BindView(R.id.bankNumberUV)
    UtilityView bankNumberUV;
    @BindView(R.id.bankNameUV)
    UtilityView bankNameUV;


    @BindView(R.id.idCardUV)
    UtilityView idCardUV;
    @BindView(R.id.cardFaceIV)
    ImageView cardFaceIV;
    @BindView(R.id.addFaceTV)
    TextView addFaceTV;
    @BindView(R.id.cardFaceFL)
    LinearLayout cardFaceFL;
    @BindView(R.id.cardBackIV)
    ImageView cardBackIV;
    @BindView(R.id.addBackTV)
    TextView addBackTV;
    @BindView(R.id.cardBackFL)
    LinearLayout cardBackFL;
    @BindView(R.id.licenseIV)
    ImageView licenseIV;
    @BindView(R.id.shopBgIV)
    ImageView shopBgIV;
    @BindView(R.id.licenseTV)
    TextView licenseTV;
    @BindView(R.id.licenseFL)
    LinearLayout licenseFL;
    @BindView(R.id.cityUV)
    UtilityView cityUV;
    String headUrl;//头像
    private String cardFaceUrl;//身份证正面
    private String cardBackUrl;//身份证背面
    private String licenseUrl;//营业执照
    String shopBgUrl;//店铺背景
    UploadFilePresenter uploadFilePresenter;
    private ShopInfo shopInfo;
    private ApplyShopPresenter presenter;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

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

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = AppUtils.getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = GsonUtils.fromJson(JsonData, new TypeToken<List<JsonBean>>() {
        }.getType());//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

        }
        showPickerView();
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String city = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                shopInfo.setProvince(province);
                shopInfo.setCity(city);
                cityUV.setContentText(province + city);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    @OnClick({R.id.cardFaceFL, R.id.cardBackFL, R.id.licenseFL, R.id.headUV,R.id.shopBgIV,
            R.id.sureBtn, R.id.addressUV,R.id.cityUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cityUV:
                initJsonData();
                break;
            case R.id.addressUV:
                startActivityForResult(new Intent(this, SearchMapActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
                break;
            case R.id.headUV:
                choiceImg(headIV);

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
            case R.id.shopBgIV:
                choiceImg(shopBgIV);
                break;

            case R.id.sureBtn:

                if (StringUtils.isEmpty(headUrl)) {
                    ToastUtils.showLong("请上传" + headUV.getTitleText());
                    return;
                }

                String shopName = shopNameUV.getContentText();
                if (StringUtils.isEmpty(shopName)) {
                    ToastUtils.showLong("店铺名称不能为空");
                    return;
                }

                String phone = phoneUV.getContentText();
                if (!(RegexUtils.isMobileSimple(phone) || RegexUtils.isTel(phone))) {
                    ToastUtils.showLong("请输入正确的店铺号码");
                    return;
                }

                if (StringUtils.isEmpty(shopInfo.getProvince())||
                        StringUtils.isEmpty(shopInfo.getCity())){
                    ToastUtils.showLong("请选择所在省市");
                    return;
                }

                String address = addressUV.getContentText();
                if ("请选择".equals(address)) {
                    ToastUtils.showLong("店铺地址不能为空");
                    return;
                }

                String bankNumber = bankNumberUV.getContentText();
                if (StringUtils.isEmpty(bankNumber)) {
                    ToastUtils.showLong("银行卡号不能为空");
                    return;
                }

                String bankName = bankNameUV.getContentText();
                if (StringUtils.isEmpty(bankNumber)) {
                    ToastUtils.showLong("开户行不能为空");
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

                if (StringUtils.isEmpty(shopBgUrl)) {
                    ToastUtils.showLong("请上传店铺背景图");
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
                shopInfo.setPhone(phone);
                shopInfo.setAddress(address);
                shopInfo.setBackCardNumber(bankNumber);
                shopInfo.setBackName(bankName);
                shopInfo.setIntroduction(remake);
                shopInfo.setManagerName(name);
                shopInfo.setManagerIdcardNumber(idCard);

                List<String> list = new ArrayList<>();
                list.add(headUrl);
                list.add(shopBgUrl);

                List<String> list2 = new ArrayList<>();
                list2.add(cardFaceUrl);
                list2.add(cardBackUrl);
                list2.add(licenseUrl);

                uploadFilePresenter.launchImage(list, true);
                uploadFilePresenter.launchImage(list2, false);
                ChatPresenter.getInstance().updateUser(shopName,headUrl);
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
                    shopInfo.setLocationLongitude(poiItem.getLatLonPoint().getLongitude()+"");
                    shopInfo.setLocationLatitude(poiItem.getLatLonPoint().getLatitude()+"");
                }
            }

        }

    }

    /**
     * 选择图片
     *
     * @param imageView
     */
    protected void choiceImg(ImageView imageView) {
        if (imageView.getId()==R.id.headImg){
            ImagePicker.withMulti(new WeChatPresenter())
                    .setMaxCount(1)
                    .showCamera(true)//显示拍照
                    .mimeTypes(MimeType.ofImage())
                    .setSelectMode(SelectMode.MODE_SINGLE)
                    .cropSaveInDCIM(false)
                    .cropAsCircle()
                    .cropRectMinMargin(SizeUtils.dp2px(80))//设置剪裁边框间距
                    //调用剪裁
                    .crop(this, new OnImagePickCompleteListener() {
                        @Override
                        public void onImagePickComplete(ArrayList<ImageItem> items) {
                            if (!items.isEmpty()) {
                                headUrl = items.get(0).getPath();
                                ImageLoaderUtil.loadLocalCircleImage(getApplicationContext(), headUrl, headIV);
                            }
                        }
                    });
        }else{
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
                                        break;
                                    case R.id.cardBackIV:
                                        cardBackUrl = url;
                                        break;
                                    case R.id.licenseIV:
                                        licenseUrl = url;
                                        break;
                                    case R.id.shopBgIV:
                                        shopBgUrl = url;
                                        break;
                                }
                            }
                        }
                    });
        }

    }

    @Override
    public void uploadSuccess(List<String> files,boolean isPublic) {
        if (files.size() == 2) {
            shopInfo.setHeadImg(files.get(0));
            shopInfo.setBackgroundImg(files.get(1));
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
        setResult(ResponseCodeConstant.BASE_RESPONSE);
    }

    @Override
    public void ApplyFail() {
        ToastUtils.showLong("提交失败,请核对信息后重试");
    }
}
