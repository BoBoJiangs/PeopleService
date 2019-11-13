package cn.sts.platform.util;

import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatCrashCallback;
import com.tencent.stat.StatCrashReporter;
import com.tencent.stat.StatService;

import cn.sts.base.util.Logs;
import cn.sts.base.util.StringUtils;
import cn.sts.platform.BuildConfig;
import cn.sts.platform.constant.ThirdPlatformConstant;

/**
 * 统计
 * Created by weilin on 2019-07-03.
 */
public class StatUtil {

    private static final String TAG = "StatUtil";

    /**
     * 初始统计
     */
    public static void initStat() {
        if (StringUtils.isBlank(ThirdPlatformConstant.MTA_APP_KEY)) {
            Logs.e(TAG, "MTA移动统计的key尚未设置，请调用TPPUtil设置");
            return;
        }

        if (ThirdPlatformUtil.application == null) {
            Logs.e(TAG, "ThirdPlatformUtil.application is null，请调用TPPUtil初始化设置application");
            return;
        }

        // [可选]设置是否打开debug输出，上线时请关闭，Logcat标签为"MtaSDK"
        StatConfig.setDebugEnable(BuildConfig.DEBUG);
        // 基础统计API
        StatService.registerActivityLifecycleCallbacks(ThirdPlatformUtil.application);

        // 初始化并启动MTA
        try {
            // 第三个参数必须为：com.tencent.stat.common.StatConstants.VERSION
            StatService.startStatService(ThirdPlatformUtil.application, ThirdPlatformConstant.MTA_APP_KEY,
                    com.tencent.stat.common.StatConstants.VERSION);
            Logs.d("MTA", "MTA初始化成功");
        } catch (MtaSDkException e) {
            // MTA初始化失败
            Logs.d("MTA", "MTA初始化失败:" + e.getMessage());
        }

    }

    /**
     * 初始 MTA Crash
     */
    public static void initCrash() {
        if (StringUtils.isBlank(ThirdPlatformConstant.MTA_APP_KEY)) {
            Logs.e(TAG, "MTA移动统计的key尚未设置，请调用TPPUtil设置");
            return;
        }
        if (ThirdPlatformUtil.application == null) {
            Logs.e(TAG, "ThirdPlatformUtil.application is null，请调用TPPUtil初始化设置application");
            return;
        }
        StatCrashReporter crashReporter = StatCrashReporter.getStatCrashReporter(ThirdPlatformUtil.application);
        // 开启异常时的实时上报
        crashReporter.setEnableInstantReporting(true);
        // 开启java异常捕获
        crashReporter.setJavaCrashHandlerStatus(true);
        // 开启Native c/c++，即so的异常捕获,请根据需要添加，记得so文件
        crashReporter.setJniNativeCrashStatus(true);
        // crash时的回调，业务可根据需要自选决定是否添加
        crashReporter.addCrashCallback(new StatCrashCallback() {

            @Override
            public void onJniNativeCrash(String tombstoneString) {
                // native dump内容，包含异常信号、进程、线程、寄存器、堆栈等信息,具体请参考：Android原生的tombstone文件格式
                Logs.e(TAG, "MTA StatCrashCallback onJniNativeCrash:\n" + tombstoneString);
            }

            @Override
            public void onJavaCrash(Thread thread, Throwable ex) {
                //thread:crash线程信息，ex:crash堆栈
                Logs.e(TAG, "MTA StatCrashCallback onJavaCrash:\n" + thread.getId() + ":" + thread.getName() + "\n" + ex.getMessage());
            }
        });
    }
}
