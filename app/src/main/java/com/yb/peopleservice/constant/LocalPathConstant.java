package com.yb.peopleservice.constant;

import android.os.Environment;

import com.yb.peopleservice.app.MyApplication;

import java.io.File;


/**
 * 本地文件路径
 * Created by sts on 2018/5/7.
 *
 * @author daichao
 */

public class LocalPathConstant {

    /**
     * 获取项目的根目录 
     */
    public static String getBasePath() {

        String basePath;
        //如果SD卡挂载了，就用SD卡的应用包名的路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File externalDirFile = MyApplication.getAppContext().getExternalFilesDir(null);
            if (externalDirFile != null) {
                basePath = externalDirFile.getAbsolutePath();
            } else {
                basePath = MyApplication.getAppContext().getFilesDir().getAbsolutePath();
            }

        } else {
            basePath = MyApplication.getAppContext().getFilesDir().getAbsolutePath();
        }

        return basePath;
    }

    /**
     * 获取保存log的路径
     */
    public static String getLogPath() {
        return getBasePath() + "/log/";
    }

    /**
     * 获取apk文件地址
     */
    public static File getAPKFile() {
        File file = new File(getBasePath(), "/exam.apk");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.deleteOnExit();
        return file;
    }

}
