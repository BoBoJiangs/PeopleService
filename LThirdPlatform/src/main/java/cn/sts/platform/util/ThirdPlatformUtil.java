package cn.sts.platform.util;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.sts.platform.constant.ThirdPlatformConstant;

/**
 * 第三方平台工具类
 * Created by weilin on 2019-07-02.
 */
public class ThirdPlatformUtil {

    private static final String TAG = "ThirdPlatformUtil";

    public static Application application;

    private static IWXAPI iwxapi;

    /**
     * 初始化第三方平台，注入Application
     *
     * @param application application
     */
    public static void init(Application application) {
        ThirdPlatformUtil.application = application;
    }

    /**
     * 设置微信应用id和秘钥
     *
     * @param appId  应用id
     * @param secret 应用秘钥
     */
    public static void setWXAppIDAndSecret(String appId, String secret) {
        ThirdPlatformConstant.WX_APP_ID = appId;
        ThirdPlatformConstant.WX_APP_SECRET = secret;
    }

    /**
     * 设置支付宝应用id
     *
     * @param appId 应用id
     */
    public static void setAliPayAppID(String appId) {
        ThirdPlatformConstant.ZFB_APP_ID = appId;
    }

    /**
     * 设置bugly应用id
     *
     * @param appId 应用id
     */
    public static void setBuglyAppID(String appId) {
        ThirdPlatformConstant.BUGLY_APP_ID = appId;
    }

    /**
     * 设置MTA应用key
     *
     * @param appKey 应用key
     */
    public static void setMTAAppKey(String appKey) {
        ThirdPlatformConstant.MTA_APP_KEY = appKey;
    }

    public static IWXAPI getIWXAPI() {
        if (ThirdPlatformUtil.application == null) {
            throw new NullPointerException("ThirdPlatformUtil.application is null，请调用ThirdPlatformUtil初始化设置application!");
        }

        if (ThirdPlatformConstant.WX_APP_ID == null) {
            throw new NullPointerException("ThirdPlatformUtil.WX_APP_ID is null，请调用ThirdPlatformUtil设置WX_APP_ID!");
        }
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(application, ThirdPlatformConstant.WX_APP_ID, false);
        }
        return iwxapi;
    }
}
