package cn.sts.platform.server.login;

import java.io.InputStream;

import cn.sts.base.model.server.request.AbstractHttpsRequestServer;
import cn.sts.base.util.Logs;
import cn.sts.platform.R;
import cn.sts.platform.util.ThirdPlatformUtil;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 第三方登录
 * Created by weilin on 18/7/2.
 */
public class TPLoginRequestServer extends AbstractHttpsRequestServer<ITPLoginRequest> {

    private static final String TAG = "GeneratorRequestServer";

    private volatile static TPLoginRequestServer requestServer;

    /**
     * 获取单例
     */
    public static TPLoginRequestServer getInstance() {
        if (requestServer == null) {
            synchronized (TPLoginRequestServer.class) {
                if (requestServer == null) {
                    requestServer = new TPLoginRequestServer();
                }
            }
        }
        return requestServer;
    }

    @Override
    public String getServerURL() {
        return ITPLoginRequest.URL_WX_API;
    }

    @Override
    public Interceptor getRequestInterceptor(boolean isUploadFile) {
        return null;
    }

    @Override
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                Logs.i(TAG, message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Override
    public Interceptor getResponseInterceptor() {
        return null;
    }

    @Override
    public InputStream getCertificateResource() {
        return ThirdPlatformUtil.application.getResources().openRawResource(R.raw.weixin);
    }

    @Override
    public String getCertificatePassword() {
        return "Aa111111";
    }
}
