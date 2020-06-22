package com.yb.peopleservice.view.activity.services;

import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.shop.EditServicePresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
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

/**
 * 项目名称:PeopleService
 * 类描述: 认证详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 14:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EditServiceInfoActivity extends ApplyServiceActivity implements EditServicePresenter.IEditCallback {

    private EditServicePresenter servicePresenter;

    @Override
    public String getTitleName() {
        return "编辑";
    }


    @Override
    protected void initData() {
        super.initData();
        servicePresenter = new EditServicePresenter(this, this);
        serviceInfo = getIntent().getParcelableExtra(ServiceInfo.class.getName());
        headIV = headUV.getRightImageView();
        if (serviceInfo != null) {
            nameUV.setContentText(serviceInfo.getName());
            remakeUV.setContentText(serviceInfo.getIntroduction());
            phoneUV.setContentText(serviceInfo.getPhone());
            idCardUV.setContentText(serviceInfo.getIdCardNumber());
            sexUV.setContentText(serviceInfo.getSex());
            ageUV.setContentText(serviceInfo.getAge() + "");
            dateUV.setContentText(serviceInfo.getBirthday());
            addressUV.setContentText(serviceInfo.getAddress());
            ImageLoaderUtil.loadServerCircleImage(this, serviceInfo.getHeadImg(), headIV);
            ImageLoaderUtil.loadServerImage(this, serviceInfo.getIdCardImgFront(), cardFaceIV, false);
            ImageLoaderUtil.loadServerImage(this, serviceInfo.getIdCardImgBack(), cardBackIV, false);
        }
        setUVType();
    }

    private void setUVType() {
        sexUV.setVisibility(View.GONE);
        dateUV.setVisibility(View.GONE);
        nameUV.setVisibility(View.GONE);
        phoneUV.setVisibility(View.GONE);
        idCardUV.setVisibility(View.GONE);
        ageUV.setVisibility(View.GONE);
        cardBackFL.setVisibility(View.GONE);
        cardFaceFL.setVisibility(View.GONE);
    }


    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headUV:
                choiceImg(headIV);
                break;
            case R.id.sureBtn:

                serviceInfo.setIntroduction(remakeUV.getContentText());
                serviceInfo.setAddress(addressUV.getContentText());
                if (StringUtils.isEmpty(headUrl)) {
                    servicePresenter.editServiceInfo(serviceInfo);
                } else {
                    showProgressDialog();
                    uploadFilePresenter.launchImage(headUrl, true);
                    ChatPresenter.getInstance().updateUser("", headUrl);
                }

                break;
        }
    }

    @Override
    public void uploadSuccess(List<String> files, boolean isPublic) {
        if (!files.isEmpty()) {
            serviceInfo.setHeadImg(files.get(0));
        }
        dissmissProgressDialog();
        servicePresenter.editServiceInfo(serviceInfo);
    }

    @Override
    public void editSuccess(ShopInfo data) {
        finish();
        EventBus.getDefault().post(serviceInfo);
    }

    @Override
    public void editFail() {

    }
}
