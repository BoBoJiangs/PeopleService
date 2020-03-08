package com.yb.peopleservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;


/**
 * 项目名称:Flower
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/12 11:19
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AppUtils {
    /**
     * SD缓存地址
     */
    public static final String PATH_SD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/life";
    /**
     * 图片缓存地址
     */
    public static final String PATH_PIC = PATH_SD + "/photo";

    /**
     * 验证手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
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

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    public static String getPathPic() {
        File file = new File(AppConstant.FILE_PATH);
        if (file.mkdirs()) {
            return AppConstant.FILE_PATH;
        }
        return AppConstant.FILE_PATH;
    }

    public static String formatID(String id){
        return id.replace("-", "");
    }


    public static void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
