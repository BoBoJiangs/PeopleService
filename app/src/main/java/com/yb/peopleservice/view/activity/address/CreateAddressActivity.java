package com.yb.peopleservice.view.activity.address;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.amap.api.services.core.PoiItem;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.constant.ResponseCodeConstant;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.presenter.user.address.AddressManagerPresenter;
import com.yb.peopleservice.view.activity.shop.SearchMapActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.view.widget.AppDialog;
import cn.sts.base.view.widget.UtilityView;

import static com.yb.peopleservice.constant.IntentKeyConstant.RESULT_CODE;

/**
 * 项目名称:Flower
 * 类描述: 新建地址
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/3 11:12
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CreateAddressActivity extends BaseToolbarActivity implements
        AddressManagerPresenter.IManagerCallBack {


    @BindView(R.id.nameUV)
    UtilityView nameUV;
    @BindView(R.id.phoneUV)
    UtilityView phoneUV;
    @BindView(R.id.regionUV)
    UtilityView regionUV;
    @BindView(R.id.addressUV)
    UtilityView addressUV;
    @BindView(R.id.emptyUV)
    UtilityView emptyUV;
    ImageView emptyIV;
    private String isDefault;
    private AddressManagerPresenter presenter;
    private AddressListVO addressListVO;

    @Override
    public String getTitleName() {
        return "新建地址";
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_create_address;
    }

    @Override
    protected void initView() {
        super.initView();
        emptyIV = emptyUV.getRightImageView();

    }

    @Override
    public void initToolView() {
        super.initToolView();
        setAddressInfo();
    }

    private void setAddressInfo() {
        addressListVO = getIntent().getParcelableExtra(AddressListVO.class.getName());
        if (addressListVO != null) {
            titleTV.setText("修改服务地址");
            nameUV.setContentText(addressListVO.getConsigneeName());
            phoneUV.setContentText(addressListVO.getConsigneePhone());
            regionUV.setContentText(addressListVO.getDetailAddress());
            addressUV.setContentText(addressListVO.getHouseNum());
            if ("0".equals(addressListVO.getIsDefault())){
                emptyIV.setImageResource(R.mipmap.icon_open_news);
            }else{
                emptyIV.setImageResource(R.mipmap.icon_close_news);
            }
            rightTV.setVisibility(View.VISIBLE);
            rightTV.setText("删除");
            rightTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.liji_material_red_700));
        }
    }

    @Override
    public void clickRightListener() {
        AppDialog appDialog = new AppDialog(this)
                .title("是否确认删除？")
                .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                        presenter.deleteAddress(addressListVO);
                    }
                })
                .negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                    }
                });
        appDialog.setCancelable(false);
        appDialog.show();

    }

    @Override
    protected void initData() {
        emptyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Y".equals(isDefault)) {
                    isDefault = "1";
                    emptyIV.setImageResource(R.mipmap.icon_close_news);
                } else {
                    isDefault = "0";
                    emptyIV.setImageResource(R.mipmap.icon_open_news);
                }
            }
        });
    }

    @Override
    protected AddressManagerPresenter createPresenter() {
        return presenter = new AddressManagerPresenter(this, this);
    }


    @OnClick({R.id.regionUV, R.id.addressBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regionUV:
                startActivityForResult(new Intent(this, SearchMapActivity.class),
                        RequestCodeConstant.BASE_REQUEST);
//                choiceAddress();
                break;
            case R.id.addressBtn:
                String name = submitTip(nameUV);
                if (StringUtils.isEmpty(name)) {
                    return;
                }

                String phone = submitTip(phoneUV);
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtils.showLong("请输入正确的手机号码");
                    return;
                }

                String region = submitTip(regionUV);
                if (StringUtils.isEmpty(region)) {
                    return;
                }

                String address = submitTip(addressUV);
                if (StringUtils.isEmpty(address)) {
                    return;
                }
                if (addressListVO == null) {
                    addressListVO = new AddressListVO();
                }
                addressListVO.setConsigneeName(name);
                addressListVO.setConsigneePhone(phone);
                addressListVO.setDetailAddress(region);
                addressListVO.setHouseNum(address);
                if ("Y".equals(isDefault)) {
                    addressListVO.setIsDefault(isDefault);
                }
                presenter.addAddress(addressListVO);

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
                    regionUV.setContentText(address);
                }
            }

        }

    }

    private String submitTip(UtilityView utilityView) {
        if (StringUtils.isEmpty(utilityView.getContentText())) {
            ToastUtils.showLong(utilityView.getTitleText() + "信息不能为空");
        }
        return utilityView.getContentText();
    }


    @Override
    public void onSuccess() {
        ToastUtils.showLong("操作成功");
        setResult(RESULT_CODE);
        finish();
    }

    @Override
    public void onFail(String error) {
    }

    @Override
    public void deleteSuccess() {
        ToastUtils.showLong("操作成功");
        setResult(RESULT_CODE);
        finish();
    }

    @Override
    public void deleteFail(String error) {

    }
}
