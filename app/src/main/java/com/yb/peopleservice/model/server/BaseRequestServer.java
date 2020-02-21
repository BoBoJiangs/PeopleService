package com.yb.peopleservice.model.server;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.model.bean.LoginBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import cn.sts.base.model.server.request.AbstractHttpsRequestServer;
import cn.sts.base.model.server.request.AbstractRequestServer;
import cn.sts.base.util.Logs;
import cn.sts.platform.util.PayUtil;
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
public class BaseRequestServer extends AbstractHttpsRequestServer {

    private static final String TAG = "BaseRequestServer";

    public volatile static BaseRequestServer requestServer;

    public BaseRequestServer() {

    }

//    public static final String DEBUG_URL = "http://10.1.10.90:8087/";
//    public static final String DEBUG_URL = "http://10.1.10.99:8085/";
//    /**
//     * 测试外网
//     */
//    public static final String DEBUG_URL = "http://123.56.249.114/";

    /**
     * 正式服务器地址
     */
    public static String SERVER_URL = "https://www.shenghuoleida.com/";

    public static String baseURL;

    /**
     * 公开文件地址
     */
    public static String FILE_URL = "/file?uri=";

    /**
     * 支付URL
     */
    public static String PAY_URL;

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
    public static String getFileUrl(boolean isPublic) {
        if (isPublic) {
            return SERVER_URL;
        } else {
            return baseURL + FILE_URL;
        }
    }


    @Override
    public String getServerURL() {

        User data = ManagerFactory.getInstance().getUserManager().getUser();
        if (data != null && !data.getAccountType().isEmpty()) {
            if (data.getAccountType().contains(LoginBean.USER_TYPE)) {
                baseURL = SERVER_URL + "api/";
            } else if (data.getAccountType().contains(LoginBean.SHOP_TYPE)) {
                baseURL = SERVER_URL + "shop/api/";
            } else if (data.getAccountType().contains(LoginBean.SERVICE_TYPE)) {
                baseURL = SERVER_URL + "staff/api/";
            } else {
                baseURL = SERVER_URL + "sso/";
            }
        } else {
            baseURL = SERVER_URL + "sso/";
        }
        if (TextUtils.isEmpty(PayUtil.getPayUrl())) {
            PayUtil.setPayUrl(baseURL);
        }

        return baseURL;
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

    @Override
    public InputStream getCertificateResource() {
        return MyApplication.getAppContext().getResources().openRawResource(R.raw.daojia);
    }

    @Override
    public String getCertificatePassword() {
        return "123456";
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
                String token = "";
                User account = ManagerFactory.getInstance().getUserManager().getUser();
                if (account != null) {
                    token = account.getTokenType() + " " + account.getAccess_token();
                }


                request = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", token)
                        .method(original.method(), original.body())
                        .build();
                //非文件上传统一设置
                if (!isUploadFile && request.body() != null) {
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
