package com.yb.peopleservice.model.server;

import android.accounts.Account;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yb.peopleservice.BuildConfig;

import java.io.IOException;
import java.lang.reflect.Field;

import cn.sts.base.model.server.request.AbstractRequestServer;
import cn.sts.base.util.Logs;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/5  16:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class BaseRequestServer extends AbstractRequestServer {

    private static final String TAG = "BaseRequestServer";

    public volatile static BaseRequestServer requestServer;

    public BaseRequestServer() {

    }

    /**
     * 服务器地址：邓杰本地
     */
//    public static final String DEBUG_URL = "http://10.1.10.90:8087/";
//    public static final String DEBUG_URL = "http://10.1.10.99:8085/";
    /**
     * 测试外网
     */
    public static final String DEBUG_URL = "http://123.56.249.114:8112/";

    /**
     * 正式服务器地址
     */
    public static final String SERVER_URL = "http://183.220.113.73:8087/";

    /**
     * 文件地址
     */
    public static String FILE_URL = "";

    /**
     * 支付URL
     */
    public static String PAY_URL = "https://pay.sts603322.com:9443/";

    /**
     * 分享地址
     */
    public static String SHARE_URL = "";

    /**
     * 获取单例
     */
    public static BaseRequestServer getInstance() {
        if (requestServer == null) {
            synchronized (AbstractRequestServer.class) {
                if (requestServer == null) {
                    requestServer = new BaseRequestServer();
                }
            }
        }
        return requestServer;
    }

    /**
     * 文件地址
     */
    public static String getFileUrl() {

        return FILE_URL;
    }


    @Override
    public String getServerURL() {
        if (BuildConfig.DEBUG) {
            return DEBUG_URL;
//            return SERVER_URL;
        } else {
            return SERVER_URL;
        }

    }

    @Override
    public Interceptor getRequestInterceptor(boolean isUploadFile) {
        return new RequestInterceptor(isUploadFile);
    }

    @Override
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
//                if (message.contains("jessionId")) {
                LogUtils.a(message);
//                }

            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Override
    public Interceptor getResponseInterceptor() {
        return null;
    }

    /**
     * 请求-拦截器
     */
    private class RequestInterceptor implements Interceptor {

        private boolean isUploadFile;

        private RequestInterceptor(boolean isUploadFile) {
            this.isUploadFile = isUploadFile;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = null;
            try {
                Request original = chain.request();
                String cookie = "";

                request = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                //非文件上传统一设置
                if (!isUploadFile) {
                    MediaType mediaType = request.body().contentType();
                    if (mediaType != null) {
                        Field field = mediaType.getClass().getDeclaredField("mediaType");
                        field.setAccessible(true);
                        field.set(mediaType, "application/json");
                    }
                }
                Logs.i(TAG, "------开始请求------->" + JSESSIONID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return chain.proceed(request);
        }
    }


}
