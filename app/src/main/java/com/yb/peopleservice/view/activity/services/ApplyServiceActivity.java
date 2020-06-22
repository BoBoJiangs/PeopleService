package com.yb.peopleservice.view.activity.services;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.user.JsonBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.shop.ApplyShopPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.shop.SearchMapActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 申请服务人员认证
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 14:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ApplyServiceActivity extends BaseToolbarActivity implements UploadFilePresenter.IViewUploadFile, ApplyShopPresenter.IApplyCallback {
    @BindView(R.id.headUV)
    UtilityView headUV;
    ImageView headIV;
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
    LinearLayout cardFaceFL;
    @BindView(R.id.cardBackIV)
    ImageView cardBackIV;
    @BindView(R.id.addBackTV)
    TextView addBackTV;
    @BindView(R.id.cardBackFL)
    LinearLayout cardBackFL;
    @BindView(R.id.ageUV)
    UtilityView ageUV;
    @BindView(R.id.dateUV)
    UtilityView dateUV;
    @BindView(R.id.sexUV)
    UtilityView sexUV;
    @BindView(R.id.sureBtn)
    Button sureBtn;
    @BindView(R.id.cityUV)
    UtilityView cityUV;
    protected String headUrl;//头像
    private String cardFaceUrl;//身份证正面
    private String cardBackUrl;//身份证背面
    UploadFilePresenter uploadFilePresenter;
    protected ServiceInfo serviceInfo;
    private ApplyShopPresenter presenter;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    @Override
    public String getTitleName() {
        return "服务人员认证";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_apply_service_info;
    }

    @Override
    protected void initData() {
        serviceInfo = new ServiceInfo();
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
                serviceInfo.setProvince(province);
                serviceInfo.setCity(city);
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

    @OnClick({R.id.cardFaceFL, R.id.cardBackFL, R.id.headUV,
            R.id.sureBtn, R.id.addressUV, R.id.dateUV, R.id.sexUV, R.id.cityUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cityUV:
                initJsonData();
                break;
            case R.id.headUV:
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
                break;
            case R.id.cardFaceFL:
                choiceImg(cardFaceIV);
                break;
            case R.id.cardBackFL:
                choiceImg(cardBackIV);
                break;
            case R.id.dateUV:
                KeyboardUtils.hideSoftInput(this);
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String dateTime = TimeUtils.date2String(date,
                                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
                        serviceInfo.setBirthday(dateTime);
                        dateUV.setContentText(dateTime);
                    }
                }).build();
                pvTime.show();
                break;
            case R.id.sexUV:
                new XPopup.Builder(this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"男", "女"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        sexUV.setContentText(text);
                                        serviceInfo.setSex(text);
                                    }
                                })
                        .show();
                break;
            case R.id.sureBtn:

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

                if (StringUtils.isEmpty(serviceInfo.getProvince())||
                        StringUtils.isEmpty(serviceInfo.getCity())){
                    ToastUtils.showLong("请选择所在省市");
                    return;
                }

                String address = addressUV.getContentText();
                if (StringUtils.isEmpty(address)) {
                    ToastUtils.showLong("地址不能为空");
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
                int age = 0;
                try {
                    age = Integer.parseInt(ageUV.getContentText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (age == 0) {
                    ToastUtils.showLong("年龄不能为空");
                    return;
                }

                serviceInfo.setIntroduction(remakeUV.getContentText());
                serviceInfo.setName(name);
                serviceInfo.setIdCardNumber(idCard);
                serviceInfo.setPhone(phone);
                serviceInfo.setAddress(address);
                serviceInfo.setAge(age);

                List<String> list = new ArrayList<>();
                list.add(cardFaceUrl);
                list.add(cardBackUrl);
                uploadFilePresenter.launchImage(headUrl, true);
                uploadFilePresenter.launchImage(list, false);
                ChatPresenter.getInstance().updateUser(name, headUrl);
                break;
        }
    }


    /**
     * 选择图片
     *
     * @param imageView
     */
    protected void choiceImg(ImageView imageView) {
        if (imageView.getId() == R.id.headImg) {
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
        } else {
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
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void uploadSuccess(List<String> files, boolean isPublic) {
        if (files.size() == 1) {
            serviceInfo.setHeadImg(files.get(0));
        }
        if (files.size() == 2) {
            serviceInfo.setIdCardImgFront(files.get(0));
            serviceInfo.setIdCardImgBack(files.get(1));
        }
        if (!StringUtils.isEmpty(serviceInfo.getHeadImg()) &&
                !StringUtils.isEmpty(serviceInfo.getIdCardImgBack())) {
            presenter.applyService(serviceInfo);
        }
    }

    @Override
    public void uploadFail() {
        ToastUtils.showLong("提交失败,请核对信息后重试");
    }

    @Override
    public void ApplySuccess(ShopInfo data) {
        ToastUtils.showLong("提交成功,等待管理员审核");
        EventBus.getDefault().post(serviceInfo);
        finish();
    }

    @Override
    public void ApplyFail() {
        ToastUtils.showLong("提交失败,请核对信息后重试");
    }
}
