package com.yb.peopleservice.view.activity.shop;

import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.model.presenter.user.UpdateUserPresenter;
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

public class PersonInfoActivity extends BaseToolbarActivity{


    @BindView(R.id.headUV)
    UtilityView headUV;
    @BindView(R.id.nameUV)
    UtilityView nameUV;
    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    private String headUrl;//头像
    ImageView headIV;
    private UserInfoBean infoBean;

    @Override
    protected int contentViewResID() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        rightTV.setVisibility(View.VISIBLE);
        rightTV.setText("保存");
    }

    @Override
    protected void initData() {
        infoBean = new UserInfoBean();
        headIV = headUV.getRightImageView();

        setUserInfoText();
    }

    private void setUserInfoText() {
        User user = ManagerFactory.getInstance().getUserManager().getUser();
        UserInfoBean infoBean = user.getInfoBean();
        if (infoBean != null) {
            nameUV.setContentText(infoBean.getNickname());
            phoneUV.setContentText(infoBean.getPhone());
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return "个人信息";
    }

}
