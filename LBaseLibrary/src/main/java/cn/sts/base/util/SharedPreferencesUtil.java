package cn.sts.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * SharedPreferences工具类
 * <p>
 * Created by weilin on 17/2/3.
 */

public class SharedPreferencesUtil {

    /**
     * 保存本地数据
     *
     * @param key   键
     * @param value 值
     */
    public static void saveData(Context context, String key, Object value) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (value != null) {
            if (value instanceof String) {
                defaultSharedPreferences.edit().putString(key, (String) value).apply();
            } else if (value instanceof Integer) {
                defaultSharedPreferences.edit().putInt(key, (Integer) value).apply();
            } else if (value instanceof Boolean) {
                defaultSharedPreferences.edit().putBoolean(key, (Boolean) value).apply();
            } else if (value instanceof Float) {
                defaultSharedPreferences.edit().putFloat(key, (Float) value).apply();
            } else if (value instanceof Long) {
                defaultSharedPreferences.edit().putLong(key, (Long) value).apply();
            } else {
                defaultSharedPreferences.edit().putString(key, value.toString()).apply();
            }
        }

    }

    /**
     * 删除数据
     *
     * @param key 键
     * @return true/false
     */
    public static boolean deleteData(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.edit().remove(key).commit();
    }

    /**
     * 获取字符串数据，默认返回""
     *
     * @param key 键
     * @return 数据
     */
    public static String getStrData(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(key, "");
    }

    /**
     * 获取布尔型数据，默认返回false
     *
     * @param key 键
     * @return 数据
     */
    public static boolean getBoolData(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(key, false);
    }


    /**
     * 获取整型数据，默认返回0
     *
     * @param key 键
     * @return 数据
     */
    public static int getIntData(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getInt(key, 0);
    }
}
