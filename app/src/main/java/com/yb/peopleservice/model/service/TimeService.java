package com.yb.peopleservice.model.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;



/**
 * 计时服务
 * Created by sts on 2018/7/6.
 *
 * @author daichao
 */

public class TimeService extends Service {
    public static final int GRAY_SERVICE_ID = 1001;
    public static final String CHANNEL_ID_STRING = "nyd001";
    /**
     * 当前时间
     */
    public static long currentTime = 0;

    private Handler handler;
    private TimeRunnable timeRunnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (handler == null) {
            HandlerThread handlerThread = new HandlerThread("TimeService");
            handlerThread.start();

            handler = new Handler(handlerThread.getLooper());
        }

        if (timeRunnable == null) {
            timeRunnable = new TimeRunnable();
            handler.post(timeRunnable);
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        try {

            //设置service为前台服务，提高优先级
            if (Build.VERSION.SDK_INT < 18) {
                //Android4.3以下 ，此方法能有效隐藏Notification上的图标
                startForeground(GRAY_SERVICE_ID, new Notification());
            } else if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 25) {

                startForeground(GRAY_SERVICE_ID, new Notification());
                //Android4.3 - Android7.0，此方法能有效隐藏Notification上的图标
                Intent innerIntent = new Intent(this, GrayInnerService.class);
                startService(innerIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //8.0以上设备需要设置渠道ID
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_STRING,
                        AppUtils.getAppName(), NotificationManager.IMPORTANCE_NONE);
                mChannel.enableVibration(false);
                notificationManager.createNotificationChannel(mChannel);
                Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
                startForeground(GRAY_SERVICE_ID, notification);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private class TimeRunnable implements Runnable {

        @Override
        public void run() {

            currentTime += 1000;


            handler.postDelayed(timeRunnable, 1000);
            LogUtils.i(TimeUtils.millis2String(currentTime));
        }

    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止前台服务
        stopForeground(true);
        handler.removeCallbacks(timeRunnable);
    }
}
