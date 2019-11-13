package cn.sts.base.util;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sts.base.app.AppManager;


/**
 * 崩溃日志记录
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static String TAG = "CrashHandler";
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance;

    private Application application;

    // 用来存储设备信息和异常信息
    private Map<String, String> infoMap = new HashMap<>();

    private static String logPath;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                instance = new CrashHandler();
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param application application
     * @param logPath     日志保存路径
     */
    public void init(Application application, String logPath) {
        this.application = application;
        CrashHandler.logPath = logPath;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        autoClear(30);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, throwable);
        } else {
//            SystemClock.sleep(3000);
            // 退出程序
            AppManager.getAppManager().appExit(application);

        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param throwable 异常
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        try {
            // 收集设备参数信息
//            collectDeviceInfo();
            // 保存日志文件
            saveCrashInfoFile(throwable);
            // 重启应用（按需要添加是否重启应用）
            speedRestartApp(application);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    public void restartApp(Application application) {
        //启动页
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = application.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            application.startActivity(intent);


            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 重新启动App -> 不杀进程,缓存的东西不清除,启动快
     */
    public void speedRestartApp(Application application) {
        Intent intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        application.startActivity(intent);
    }

    /**
     * 收集设备参数信息
     */
    private void collectDeviceInfo() {
        try {
            PackageManager pm = application.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(application.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                infoMap.put("versionName", versionName);
                infoMap.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error crash info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param throwable 异常
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Exception 写入错误
     */
    private String saveCrashInfoFile(Throwable throwable) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\r\n");
            sb.append(DateUtil.dateToYMDHMSStr(new Date()));
            sb.append("\n");
            for (Map.Entry<String, String> entry : infoMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key);
                sb.append("=");
                sb.append(value);
                sb.append("\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            return writeFile(sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "an error writing file...", e);
            sb.append("an error writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }

    private String writeFile(String sb) throws Exception {

        String time = DateUtil.dateToYMDHMStr(new Date());
        String fileName = "crash-" + time + ".log";
        if (FileUtil.hasSdcard()) {
            if (StringUtils.isNotBlank(getCrashPath())) {
                String path = getCrashPath();
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName, true);
                fos.write(sb.getBytes());
                fos.flush();
                fos.close();
            }
        }
        return fileName;
    }

    /**
     * 崩溃日志路径
     */
    public static String getCrashPath() {
        return logPath;
    }

    /**
     * 文件删除
     *
     * @param autoClearDay 文件保存天数
     */
    public void autoClear(final int autoClearDay) {
        if (StringUtils.isNotBlank(getCrashPath())) {
            FileUtil.delete(getCrashPath(), new FilenameFilter() {

                @Override
                public boolean accept(File file, String filename) {
                    String s = FileUtil.getFileNameWithoutExtension(filename);
                    int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                    String date = "crash-" + DateUtil.getOtherDateTimeStr(day);
                    return date.compareTo(s) >= 0;
                }
            });
        }
    }

}
