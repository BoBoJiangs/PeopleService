package com.yb.peopleservice.model.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.czt.mp3recorder.MP3Recorder;
import com.shuyu.waveview.FileUtils;
import com.yb.peopleservice.model.eventbean.EventRecorderBean;
import com.yb.peopleservice.utils.RxTimerUtil;
import com.yb.peopleservice.view.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.UUID;


/**
 * 类描述: 后台录音服务
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/11  11:43
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public class TimeService extends Service implements RxTimerUtil.IRxNext {
    public static final int GRAY_SERVICE_ID = 1001;
    public static final int START_RECORD = 1002;
    public static final String CHANNEL_ID_STRING = "nyd001";
    private MP3Recorder mRecorder;
    private String filePath;
    private boolean mIsRecord = false;
    private MyHandler handler;

    private static class MyHandler extends Handler {
        WeakReference<TimeService> reference;

        public MyHandler(TimeService service) {
            reference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(null!=reference)
            {
                TimeService timeService = (TimeService) reference.get();
                if(null!=timeService) {
                    switch (msg.what){
                        case MP3Recorder.ERROR_TYPE:
                            ToastUtils.showLong("没有麦克风权限");
                            timeService.resolveError();
                            break;
                        case START_RECORD:
                            timeService.resolveRecord();
                            break;
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new MyHandler(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRecorderBean event) {
        if (event.getMessage().equals(EventRecorderBean.START)) {
            resolveRecord();
        } else {
            resolveStopRecord();
        }
    }

    /**
     * 开始录音
     */
    @SuppressLint("HandlerLeak")
    private void resolveRecord() {
        filePath = FileUtils.getAppPath();
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ToastUtils.showLong("创建文件失败");
                return;
            }
        }

        filePath = FileUtils.getAppPath() + UUID.randomUUID().toString() + ".mp3";
        mRecorder = new MP3Recorder(new File(filePath));


        mRecorder.setErrorHandler(handler);


        try {
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.showLong("录音出现异常");
            resolveError();
            return;
        }
        mIsRecord = true;
        RxTimerUtil.timer(1000 * 60, this::doNext);
    }

    /**
     * 停止录音
     */
    private void resolveStopRecord() {
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(false);
            mRecorder.stop();
        }
        mIsRecord = false;
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

    @Override
    public void doNext(long number) {
        if (mIsRecord) {
            resolveStopRecord();
        }
        handler.sendEmptyMessageDelayed(START_RECORD,1000);

    }

    /**
     * 录音异常
     */
    private void resolveError() {
//        FileUtils.deleteFile(filePath);
//        filePath = "";
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stop();
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
        resolveStopRecord();
        EventBus.getDefault().unregister(this);
    }
}
