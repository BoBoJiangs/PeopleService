package cn.sts.platform.server.pay;

import cn.sts.base.model.server.request.AbstractRequestServer;
import cn.sts.base.util.Logs;
import cn.sts.platform.util.PayUtil;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 请求
 * Created by weilin on 18/7/2.
 */
public class PayRequestServer extends AbstractRequestServer<IPayRequest> {

    private static final String TAG = "GeneratorRequestServer";

    private volatile static PayRequestServer requestServer;

    /**
     * 获取单例
     */
    public static PayRequestServer getInstance() {
        if (requestServer == null) {
            synchronized (PayRequestServer.class) {
                if (requestServer == null) {
                    requestServer = new PayRequestServer();
                }
            }
        }
        return requestServer;
    }

    @Override
    public String getServerURL() {
        return PayUtil.getPayUrl();
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

}
