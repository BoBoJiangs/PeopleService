package com.yb.peopleservice.view.activity.services;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.shop.ApplyShopPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述: 认证详情
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/13 14:59
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceApplyDetailsActivity extends ApplyServiceActivity {

    @Override
    public String getTitleName() {
        return "认证信息";
    }


    @Override
    protected void initData() {
        super.initData();
        serviceInfo = getIntent().getParcelableExtra(ServiceInfo.class.getName());
        headIV = headUV.getRightImageView();
        if (serviceInfo != null) {
            nameUV.setContentText(serviceInfo.getName());
            remakeUV.setContentText(serviceInfo.getIntroduction());
            phoneUV.setContentText(serviceInfo.getPhone());
            idCardUV.setContentText(serviceInfo.getIdCardNumber());
            sexUV.setContentText(serviceInfo.getSex());
            ageUV.setContentText(serviceInfo.getAge()+"");
            dateUV.setContentText(serviceInfo.getBirthday());
            addressUV.setContentText(serviceInfo.getAddress());
            ImageLoaderUtil.loadServerCircleImage(this, serviceInfo.getHeadImg(), headIV);
            ImageLoaderUtil.loadServerImage(this, serviceInfo.getIdCardImgFront(), cardFaceIV, false);
            ImageLoaderUtil.loadServerImage(this, serviceInfo.getIdCardImgBack(), cardBackIV, false);
        }
        setUVType();
    }

    private void setUVType() {
        addressUV.getInputEditText().setEnabled(false);
        remakeUV.getInputEditText().setEnabled(false);
        nameUV.getInputEditText().setEnabled(false);
        phoneUV.getInputEditText().setEnabled(false);
        idCardUV.getInputEditText().setEnabled(false);
        ageUV.getInputEditText().setEnabled(false);
        sureBtn.setVisibility(View.GONE);
    }



    @Override
    public void onViewClicked(View view) {

    }
}
