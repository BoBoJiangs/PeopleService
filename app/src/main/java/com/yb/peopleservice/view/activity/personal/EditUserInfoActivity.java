package com.yb.peopleservice.view.activity.personal;

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
import com.yb.peopleservice.model.database.manager.UserInfoManager;
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

public class EditUserInfoActivity extends BaseToolbarActivity implements
        UpdateUserPresenter.IUserCallback, UploadFilePresenter.IViewUploadFile {


    @BindView(R.id.headUV)
    UtilityView headUV;
    @BindView(R.id.nameUV)
    UtilityView nameUV;
    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    private String headUrl;//头像
    ImageView headIV;
    private UpdateUserPresenter presenter;
    UploadFilePresenter uploadPresenter;
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
        uploadPresenter = new UploadFilePresenter(this, this);
        return presenter = new UpdateUserPresenter(this, this);
    }

    @Override
    public String getTitleName() {
        return "个人信息";
    }

    @Override
    public void clickRightListener() {
        if (StringUtils.isEmpty(headUrl)) {
            ToastUtils.showLong("请上传" + headUV.getTitleText());
            return;
        }
        String name = nameUV.getContentText();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showLong("昵称不能为空");
            return;
        }
        String phone = phoneUV.getContentText();
        if (!(RegexUtils.isMobileExact(phone) || RegexUtils.isTel(phone))) {
            ToastUtils.showLong("请输入正确的电话号码");
            return;
        }
        infoBean.setNickname(name);
        infoBean.setPhone(phone);
        uploadPresenter.launchImage(headUrl, true);

    }

    @OnClick(R.id.headUV)
    public void onViewClicked() {
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
    }

    @Override
    public void getDataSuccess(UserInfoBean data) {
        finish();
    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void uploadSuccess(List<String> files) {
        if (!files.isEmpty()) {
            infoBean.setHeadImg(files.get(0));
        }
        presenter.setUserInfo(infoBean);
    }

    @Override
    public void uploadFail() {

    }
}
