package com.yb.peopleservice.app;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.QbSdk;
import com.yb.peopleservice.BuildConfig;

import cn.sts.base.util.AppManageUtil;
import cn.sts.platform.util.PayUtil;
import cn.sts.platform.util.ThirdPlatformUtil;


/**
 * 自定义Application类
 * Created by weilin on 16/11/25.
 */
public class MyApplication extends MultiDexApplication {

    private static Context appContext;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        //APP开发工具初始化
        Utils.init(getApplicationContext());
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG);

        //三方平台初始化application
        ThirdPlatformUtil.init(this);
        //设置微信相关账号数据
//        ThirdPlatformUtil.setWXAppIDAndSecret(AppConstant.WECHAT_APP_ID, AppConstant.WECHAT_SECRET);
//        PayUtil.setPayUrl(BaseRequestServer.PAY_URL);

        initLog();
//        AppManageUtil.APP_CODE = AppConstant.FILE_KEY;
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtils.d(" onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d(" onViewInitFinished is " + b);
            }
        });
    }


    /**
     * 获取应用上下文
     */
    public static Context getAppContext() {
        return appContext;
    }


    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void initLog() {
        final LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setBorderSwitch(false)
                .setLogHeadSwitch(false)
                .setSingleTagSwitch(false)
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("");// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
    }
}
