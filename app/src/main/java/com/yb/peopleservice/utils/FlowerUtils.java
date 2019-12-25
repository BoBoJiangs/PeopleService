package com.yb.peopleservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yb.peopleservice.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 项目名称:Flower
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/12 11:19
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FlowerUtils {
    // 将传入时间与当前时间进行对比，是否今天\昨天\前天\同一年
    @SuppressLint("SimpleDateFormat")
    public static String getTime(long longTime) {
        Date date = TimeUtils.millis2Date(longTime);
        boolean sameYear;
        String todaySDF = "HH:mm";
        String otherSDF = "MM-dd HH:mm";
        String otherYearSDF = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sfd = null;
        String time;
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);

        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }

        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            sfd = new SimpleDateFormat(todaySDF);
            time = "今天 " + sfd.format(date);
            return time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                sfd = new SimpleDateFormat(todaySDF);
                time = "昨天 " + sfd.format(date);
                return time;
            }
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天

                sfd = new SimpleDateFormat(todaySDF);
                time = "前天 " + " " + sfd.format(date);
                return time;
            }
        }

        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone){
        return RegexUtils.isMobileExact(phone);
    }

    /**
     * 默认布局
     *
     * @param context
     * @param drawableId
     * @param text
     * @return
     */
    public static View getEmptyView(Context context, int drawableId, String text) {
        // 使用代码设置drawableleft
        Drawable drawable = context.getResources().getDrawable(drawableId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());

        View emptyView = View.inflate(context, R.layout.view_nodata, null);
        TextView textView = emptyView.findViewById(R.id.textView);
        textView.setText(text);
        textView.setCompoundDrawables(null, drawable, null, null);
        return emptyView;
    }
}
