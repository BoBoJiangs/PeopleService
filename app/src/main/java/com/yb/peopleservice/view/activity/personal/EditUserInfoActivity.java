package com.yb.peopleservice.view.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserManager;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.model.presenter.user.UpdateUserPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.login.LoginActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;
import cn.sts.base.view.widget.UtilityView;

public class EditUserInfoActivity extends BaseToolbarActivity implements
        UpdateUserPresenter.IUserCallback, UploadFilePresenter.IViewUploadFile {


    @BindView(R.id.headUV)
    UtilityView headUV;
    @BindView(R.id.nameUV)
    UtilityView nameUV;
    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    @BindView(R.id.trueNameUV)
    UtilityView trueNameUV;
    @BindView(R.id.ageUV)
    UtilityView ageUV;
    @BindView(R.id.dateUV)
    UtilityView dateUV;
    @BindView(R.id.manRG)
    RadioButton manRG;
    @BindView(R.id.woManRG)
    RadioButton woManRG;
    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.sexUV)
    UtilityView sexUV;
    private String headUrl;//头像
    ImageView headIV;
    private UpdateUserPresenter presenter;
    UploadFilePresenter uploadPresenter;
    private UserInfoBean infoBean;
    private String nikeName;

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
        phoneUV.getInputEditText().setEnabled(false);
        setUserInfoText();
    }

    private void setUserInfoText() {
        User user = ManagerFactory.getInstance().getUserManager().getUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        UserInfoBean infoBean = user.getInfoBean();
        if (infoBean != null) {
            nameUV.setContentText(infoBean.getNickname());
            phoneUV.setContentText(infoBean.getPhone());
            trueNameUV.setContentText(infoBean.getName());
            sexUV.setContentText(infoBean.getSex());
            if (infoBean.getAge() != 0) {
                ageUV.setContentText(infoBean.getAge() + "");
            }
            dateUV.setContentText(infoBean.getBirthday());
            if (!StringUtils.isEmpty(infoBean.getHeadImg())) {
                ImageLoaderUtil.loadServerCircleImage(this, infoBean.getHeadImg(), headIV);
            }
        } else {
            infoBean = new UserInfoBean();
        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        uploadPresenter = new UploadFilePresenter(this, this);
        return presenter = new UpdateUserPresenter(this, this);
    }

    @Override
    public String getTitleName() {
        return "个人信息";
    }

    @Override
    public void clickRightListener() {
//        if (StringUtils.isEmpty(headUrl)) {
//            ToastUtils.showLong("请上传" + headUV.getTitleText());
//            return;
//        }
        nikeName = nameUV.getContentText();
        if (StringUtils.isEmpty(nikeName)) {
            ToastUtils.showLong("昵称不能为空");
            return;
        }
        String phone = phoneUV.getContentText();
        if (!(RegexUtils.isMobileExact(phone) || RegexUtils.isTel(phone))) {
            ToastUtils.showLong("请输入正确的电话号码");
            return;
        }
        String trueName = trueNameUV.getContentText();
        if (StringUtils.isEmpty(trueName)) {
            ToastUtils.showLong("姓名不能为空");
            return;
        }
        int age = 0;
        try {
            age = Integer.parseInt(ageUV.getContentText());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (StringUtils.isEmpty(age)) {
//            ToastUtils.showLong("年龄不能为空");
//            return;
//        }
        infoBean.setAge(age);
        infoBean.setName(trueName);
        infoBean.setNickname(nikeName);
        infoBean.setPhone(phone);
        if (!StringUtils.isEmpty(headUrl)) {
            uploadPresenter.launchImage(headUrl, true);
        } else {
            presenter.setUserInfo(infoBean);
        }
        ChatPresenter.getInstance().updateUser(nikeName, headUrl);
    }

    @OnClick({R.id.headUV, R.id.dateUV, R.id.sexUV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dateUV:
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String dateTime = TimeUtils.date2String(date,
                                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
                        infoBean.setBirthday(dateTime);
                        dateUV.setContentText(dateTime);
                    }
                }).build();
                pvTime.show();
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
            case R.id.sexUV:
                new XPopup.Builder(this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"男", "女"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        sexUV.setContentText(text);
                                        infoBean.setSex(text);
                                    }
                                })
                        .show();
                break;
        }

    }

    @Override
    public void getDataSuccess(UserInfoBean data) {
        finish();
        EventBus.getDefault().post(infoBean);
    }


    @Override
    public void getDataFail() {

    }

    @Override
    public void uploadSuccess(List<String> files, boolean isPublic) {
        if (!files.isEmpty()) {
            infoBean.setHeadImg(files.get(0));
        }
        presenter.setUserInfo(infoBean);
    }

    @Override
    public void uploadFail() {

    }

}
