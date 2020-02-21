package com.yb.peopleservice.view.weight.CustomPopup;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.impl.InputConfirmPopupView;
import com.yb.peopleservice.R;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/5 12:33
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class CustomCashPopup extends InputConfirmPopupView {
    public CustomCashPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        setTitleContent("提现金额", "", "请输入提现金额");
    }

}
