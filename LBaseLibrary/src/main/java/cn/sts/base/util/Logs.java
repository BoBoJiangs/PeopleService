package cn.sts.base.util;

import android.util.Log;

import cn.sts.base.BuildConfig;

/**
 * APP log打印
 * Created by weilin on 16/11/24.
 */

public class Logs {

    public static void d(String tag, String info) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, info);
        }
    }

    public static void i(String tag, String info) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, info);
        }
    }

    public static void w(String tag, String info) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, info);
        }
    }

    public static void e(String tag, String info) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, info);
        }
    }
}
