package cn.sts.base.util;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * 设置屏幕的工具类
 * Created by daichao on 2017/8/23.
 */
public class ScreenUtil {

    /**
     * 设置隐藏标题栏
     */
    public static void setNoTitleBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 透明状态栏，布局撑满屏幕
     */
    public static void hideToolBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置全屏
     */
    public static void setFullScreen(Activity activity) {

        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     */
    public static void cancelFullScreen(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

}
