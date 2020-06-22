package com.yb.peopleservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static String formatID(String id) {
        return id.replace("-", "");
    }


    public static void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static boolean isPassWord(String passWord) {
        //空字符串强度值为0
        if (TextUtils.isEmpty(passWord)) return false;
        //字符统计
        int iNum = 0, iLtt = 0, iSym = 0;
        for(int i=0;i < passWord.length();i++) {
            char c = passWord.charAt(i);
            if (c >= '0' && c <= '9') iNum++;
            else if (c >= 'a' && c <= 'z') iLtt++;
            else if (c >= 'A' && c <= 'Z') iLtt++;
            else iSym++;
        }
        if (passWord.length() > 7
                && iNum > 0
                && iLtt > 0)
        {
            return true;
        }

        return false;
    }
}
