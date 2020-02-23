package com.yb.peopleservice.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;
import com.squareup.picasso.Picasso;
import com.tencent.smtt.sdk.QbSdk;
import com.yb.peopleservice.BuildConfig;
import com.yb.peopleservice.GlideApp;
import com.yb.peopleservice.R;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;
import cn.sts.base.util.AppManageUtil;
import cn.sts.platform.util.PayUtil;
import cn.sts.platform.util.ThirdPlatformUtil;


/**
 * 自定义Application类
 * Created by weilin on 16/11/25.
 */
public class MyApplication extends MultiDexApplication implements AMapLocationListener {

    private static Context appContext;
    private AMapLocationClient mlocationClient;
    public static AMapLocation aMapLocation;//当前位置信息

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
        initLocation();
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
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        initGridView();
    }

    private void initGridView() {
        NineGridView.setImageLoader(new PicassoImageLoader());


    }

    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            GlideApp.with(context).load(url)//
                    .placeholder(R.drawable.base_icon_default)//
                    .error(R.drawable.base_icon_default)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
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

    private void initLocation() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置定位参数
            mlocationClient.setLocationOption(getDefaultOption());
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setInterval(10000);//可选，设置定位间隔。默认为2秒
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        mOption.setNeedAddress(true);
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location.getErrorCode() == 0) {
            MyApplication.aMapLocation = location;
            if (!(TextUtils.isEmpty(location.getAddress()) &&
                    TextUtils.isEmpty(location.getPoiName()))) {
                EventBus.getDefault().post(location);
            }

        } else {
            Log.e("amap", "定位失败");
            MyApplication.aMapLocation = null;
        }
    }
}
