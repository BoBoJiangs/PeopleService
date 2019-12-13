package com.yb.peopleservice.view.activity.shop;

import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.base.BaseActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.util.ArrayList;

import butterknife.BindView;
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
public class ApplyShopActivity extends BaseToolbarActivity {
    @BindView(R.id.headUV)
    UtilityView headUV;
    ImageView headIV;
    private String headUrl;
    private UploadFilePresenter uploadFilePresenter;
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
        headIV = headUV.getRightImageView();
        uploadFilePresenter = new UploadFilePresenter(this,null);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.headUV)
    public void onViewClicked() {
        ImagePicker.withMulti(new WeChatPresenter())
                .setSelectMode(SelectMode.MODE_SINGLE)
                .setCropRatio(1, 1)//设置剪裁比例   1：1
                .cropSaveInDCIM(false)
                .cropRectMinMargin(SizeUtils.dp2px(80))//设置剪裁边框间距
                //调用剪裁
                .crop(this, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        if (!items.isEmpty()) {
                            headUrl = items.get(0).getPath();
                            ImageLoaderUtil.loadLocalCircleImage(getApplicationContext(), headUrl, headIV);
                            uploadFilePresenter.launchImage(headUrl,true);
                        }
                    }
                });
    }
}
