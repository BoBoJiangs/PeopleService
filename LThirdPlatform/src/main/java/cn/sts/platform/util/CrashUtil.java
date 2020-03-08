package cn.sts.platform.util;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import cn.sts.base.util.Logs;
import cn.sts.base.util.StringUtils;
import cn.sts.platform.BuildConfig;
import cn.sts.platform.constant.ThirdPlatformConstant;

/**
 * Crash日志统计
 * Created by weilin on 2019-07-03.
 */
public class CrashUtil {

    private static final String TAG = "CrashUtil";

    /**
     * 初始化 bugly crash
     */
    public static void init() {
        if (StringUtils.isBlank(ThirdPlatformConstant.BUGLY_APP_ID)) {
            Logs.e(TAG, "Bugly的应用id尚未设置，请调用TPPUtil设置");
            return;
        }
        if (ThirdPlatformUtil.application == null) {
            Logs.e(TAG, "ThirdPlatformUtil.application is null，请调用TPPUtil初始化设置application");
            return;
        }
        //在开发测试阶段，可以在初始化Bugly之前通过以下接口把调试设备设置成“开发设备”。
//        CrashReport.setIsDevelopmentDevice(ThirdPlatformUtil.application, BuildConfig.DEBUG);
        //日志
//        第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//        输出详细的Bugly SDK的Log；
//        每一条Crash都会被立即上报；
//        自定义日志将会在Logcat中输出。
//        Bugly.init(ThirdPlatformUtil.application, ThirdPlatformConstant.BUGLY_APP_ID,  false);
//        CrashReport.initCrashReport(ThirdPlatformUtil.application, ThirdPlatformConstant.BUGLY_APP_ID, BuildConfig.DEBUG);
    }
}
