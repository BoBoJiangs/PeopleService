package cn.sts.base.model.server.request.app;

import java.io.IOException;
import java.lang.reflect.Field;

import cn.sts.base.model.server.request.AbstractRequestServer;
import cn.sts.base.util.AppManageUtil;
import cn.sts.base.util.Logs;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 版本管理/帮助中心请求
 * Created by weilin on 18/7/2.
 */
public class AppManageRequestServer extends AbstractRequestServer<IAppManageURL> {

    private static final String TAG = "AppManageRequestServer";

    private volatile static AppManageRequestServer requestServer;

    /**
     * 获取单例
     */
    public static AppManageRequestServer getInstance() {
        if (requestServer == null) {
            synchronized (AppManageRequestServer.class) {
                if (requestServer == null) {
                    requestServer = new AppManageRequestServer();
                }
            }
        }
        return requestServer;
    }

    @Override
    public String getServerURL() {
        return AppManageUtil.APP_MANAGE_URL;
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

    /**
     * 请求-拦截器
     */
    private class RequestInterceptor implements Interceptor {

        private boolean isUploadFile;

        private RequestInterceptor(boolean uploadFile) {
            isUploadFile = uploadFile;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = null;
            try {
                Request original = chain.request();
                String cookie = "JSESSIONID=" + JSESSIONID;

                request = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("device-os-version", android.os.Build.VERSION.RELEASE)
                        .addHeader("cookie", cookie)
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

                Logs.i(TAG, "------开始请求------->" + original.url());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return chain.proceed(request);
        }
    }
}
