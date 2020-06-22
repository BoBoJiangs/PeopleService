package com.yb.peopleservice.model.presenter.track;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.amap.api.track.query.model.AddTerminalRequest;
import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackRequest;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.callback.SimpleOnTrackLifecycleListener;
import com.yb.peopleservice.model.callback.SimpleOnTrackListener;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.view.activity.main.ServiceMainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;
import static com.yb.peopleservice.app.MyApplication.appContext;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/16 22:46
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AMapTrackPresenter {
    private static final String CHANNEL_ID_SERVICE_RUNNING = "CHANNEL_ID_SERVICE_RUNNING";
    private long terminalId;
    private long trackId;
    private boolean isServiceRunning;
    private boolean isGatherRunning;

    //    private boolean uploadToTrack = false;
    private AMapTrackClient aMapTrackClient;
    private Activity activity;
    private String userId;
    private TrackCallBack callBack;

    public AMapTrackPresenter(Activity activity, TrackCallBack callBack) {
        this.activity = activity;
        aMapTrackClient = new AMapTrackClient(appContext);
        aMapTrackClient.setInterval(5, 30);
        userId = ManagerFactory.getInstance().getUserManager().getUser().getUserId();
        this.callBack = callBack;
    }

    public void stopTrack() {
        if (isServiceRunning) {
            aMapTrackClient.stopTrack(new TrackParam(AppConstant.SERVICE_ID, terminalId), onTrackListener);
        }
    }

    public void startTrack(String orderId,boolean uploadToTrack) {
        if (isServiceRunning) {
            aMapTrackClient.stopTrack(new TrackParam(AppConstant.SERVICE_ID, terminalId), onTrackListener);
        } else {
            // 先根据Terminal名称查询Terminal ID，如果Terminal还不存在，就尝试创建，拿到Terminal ID后，
            // 用Terminal ID开启轨迹服务
            aMapTrackClient.queryTerminal(new QueryTerminalRequest(AppConstant.SERVICE_ID, userId), new SimpleOnTrackListener() {
                @Override
                public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                    if (queryTerminalResponse.isSuccess()) {
                        if (queryTerminalResponse.isTerminalExist()) {
                            // 当前终端已经创建过，直接使用查询到的terminal id
                            terminalId = queryTerminalResponse.getTid();
                            if (uploadToTrack) {
                                aMapTrackClient.addTrack(new AddTrackRequest(AppConstant.SERVICE_ID, terminalId), new SimpleOnTrackListener() {
                                    @Override
                                    public void onAddTrackCallback(AddTrackResponse addTrackResponse) {
                                        if (addTrackResponse.isSuccess()) {
                                            // trackId需要在启动服务后设置才能生效，因此这里不设置，而是在startGather之前设置了track id
                                            trackId = addTrackResponse.getTrid();
                                            if (callBack != null) {
                                                callBack.getTackId(orderId,trackId);
                                            }
                                            TrackParam trackParam = new TrackParam(AppConstant.SERVICE_ID, terminalId);
                                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                trackParam.setNotification(createNotification());
                                            }
                                            aMapTrackClient.startTrack(trackParam, onTrackListener);
                                            LogUtils.a("已有终端轨迹上报");
                                        } else {
                                            ToastUtils.showLong("网络请求失败，" + addTrackResponse.getErrorMsg(), Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                            } else {
                                // 不指定track id，上报的轨迹点是该终端的散点轨迹
                                TrackParam trackParam = new TrackParam(AppConstant.SERVICE_ID, terminalId);
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    trackParam.setNotification(createNotification());
                                }
                                aMapTrackClient.startTrack(trackParam, onTrackListener);
                                LogUtils.a("已有终端散点上报");
                            }
                        } else {
                            // 当前终端是新终端，还未创建过，创建该终端并使用新生成的terminal id
                            aMapTrackClient.addTerminal(new AddTerminalRequest(userId, AppConstant.SERVICE_ID), new SimpleOnTrackListener() {
                                @Override
                                public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {
                                    if (addTerminalResponse.isSuccess()) {
                                        terminalId = addTerminalResponse.getTid();
                                        TrackParam trackParam = new TrackParam(AppConstant.SERVICE_ID, terminalId);
                                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            trackParam.setNotification(createNotification());
                                        }
                                        aMapTrackClient.startTrack(trackParam, onTrackListener);
                                        LogUtils.a("新终端散点上报");
                                    } else {
                                        ToastUtils.showLong("网络请求失败，" + addTerminalResponse.getErrorMsg());
                                    }
                                }
                            });
                        }
                    } else {
                        ToastUtils.showLong("网络请求失败，" + queryTerminalResponse.getErrorMsg());
                    }


                }
            });

        }

    }

    private OnTrackLifecycleListener onTrackListener = new SimpleOnTrackLifecycleListener() {
        @Override
        public void onBindServiceCallback(int status, String msg) {
            LogUtils.a("onBindServiceCallback, status: " + status + ", msg: " + msg);
        }

        @Override
        public void onStartTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_TRACK_SUCEE || status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK) {
                // 成功启动
                LogUtils.a("启动服务成功");
                isServiceRunning = true;

                //启动采集
                aMapTrackClient.setTrackId(trackId);
                aMapTrackClient.startGather(onTrackListener);
            } else if (status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
                // 已经启动
                LogUtils.a("服务已经启动");
                isServiceRunning = true;
            } else {
                LogUtils.a("error onStartTrackCallback, status: " + status + ", msg: " + msg);
            }
        }

        @Override
        public void onStopTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_TRACK_SUCCE) {
                // 成功停止
                LogUtils.a("停止服务成功");
                isServiceRunning = false;
                isGatherRunning = false;
            } else {
                LogUtils.a("error onStopTrackCallback, status: " + status + ", msg: " + msg);

            }
        }

        @Override
        public void onStartGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_GATHER_SUCEE) {
                LogUtils.a("定位采集开启成功");
                isGatherRunning = true;
            } else if (status == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED) {
                LogUtils.a("定位采集已经开启");
                isGatherRunning = true;
            } else {
                LogUtils.a("error onStartGatherCallback, status: " + status + ", msg: " + msg);
            }
        }

        @Override
        public void onStopGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_GATHER_SUCCE) {
                LogUtils.a("定位采集停止成功");
                isGatherRunning = false;
            } else {
                LogUtils.a("error onStopGatherCallback, status: " + status + ", msg: " + msg);
            }
        }
    };

    /**
     * 在8.0以上手机，如果app切到后台，系统会限制定位相关接口调用频率
     * 可以在启动轨迹上报服务时提供一个通知，这样Service启动时会使用该通知成为前台Service，可以避免此限制
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification createNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_SERVICE_RUNNING, "app service", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(getApplicationContext(), CHANNEL_ID_SERVICE_RUNNING);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        Intent nfIntent = new Intent(activity, ServiceMainActivity.class);
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setContentIntent(PendingIntent.getActivity(activity, 0, nfIntent, 0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("生活雷达运行中")
                .setContentText("生活雷达运行中");
        Notification notification = builder.build();
        return notification;
    }

    /**
     * 创建轨迹上报回调
     */
    public interface TrackCallBack {
        void getTackId(String orderId,long trackId);
    }
}
