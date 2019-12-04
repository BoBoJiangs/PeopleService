package com.yb.peopleservice.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.yb.peopleservice.R;


/**
 * 项目名称:CloudMonitor
 * 类描述: 倒计时获取验证码
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/24 17:18
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyTimer extends CountDownTimer {
    TextView getAuthCodeTV;
    Context context;

    /**
     * @param millisInFuture    表示以毫秒为单位 倒计时的总数
     * @param countDownInterval 表示间隔多少毫秒 调用一次 onTick 方法
     */
    public MyTimer(long millisInFuture, long countDownInterval, TextView getAuthCodeTV, Context context) {
        super(millisInFuture, countDownInterval);
        this.getAuthCodeTV = getAuthCodeTV;
        this.context = context;
    }

    @Override
    public void onTick(long time) {
        getAuthCodeTV.setText(String.valueOf(time / 1000) + "s" + "后重新获取");
        getAuthCodeTV.setTextColor(ContextCompat.getColor(context, R.color.gray_cc));
        getAuthCodeTV.setEnabled(false);
    }

    @Override
    public void onFinish() {
        getAuthCodeTV.setText("获取验证码");
        getAuthCodeTV.setTextColor(ContextCompat.getColor(context, R.color.base_text_color));
        getAuthCodeTV.setEnabled(true);
    }
}
