package cn.sts.base.model.server.request;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.sts.base.model.server.exception.RequestExceptionRetry;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 请求服务器
 * Created by weilin on 16/11/16.
 */

public abstract class AbstractRequestServer<T> {

    private static final String TAG = "AbstractRequestServer";

    public static String JSESSIONID = "";

    private static Gson dateGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            }).create();

    /**
     * 返回服务器的请求地址
     *
     * @return 地址
     */
    public abstract String getServerURL();

    /**
     * 获取请求拦截器
     *
     * @param isUploadFile 是否是上传文件
     */
    public abstract Interceptor getRequestInterceptor(boolean isUploadFile);

    /**
     * 日志拦截器
     */
    public abstract HttpLoggingInterceptor getHttpLoggingInterceptor();


    /**
     * 获取响应拦截器
     */
    public abstract Interceptor getResponseInterceptor();

    /**
     * 创建OkHttpClient.Builder
     *
     * @return OkHttpClient.Builder
     */
    public OkHttpClient.Builder getOkHttpClientBuilder() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);

        return httpClientBuilder;
    }

    /**
     * 获取请求服务器的接口
     *
     * @param isUploadFile 是否上传文件
     */
    private T getRequestInterface(boolean isUploadFile, Class<T> classz) {

        OkHttpClient.Builder httpClientBuilder = getOkHttpClientBuilder();

        //请求拦截器
        Interceptor requestInterceptor = getRequestInterceptor(isUploadFile);
        if (requestInterceptor != null) {
            httpClientBuilder.addInterceptor(requestInterceptor);
        }

        //日志拦截器
        //log拦截器，打印请求参数及服务器返回的数据
        Interceptor loggingInterceptor = getHttpLoggingInterceptor();
        if (loggingInterceptor != null) {
            httpClientBuilder.addNetworkInterceptor(loggingInterceptor);
        }

        //响应拦截器
        Interceptor responseInterceptor = getResponseInterceptor();
        if (responseInterceptor != null) {
            httpClientBuilder.addInterceptor(responseInterceptor);
        }

        return new Retrofit.Builder()
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为GSON转换的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create(dateGson))
                //增加返回值为Observable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getServerURL())
                .client(httpClientBuilder.build())
                .build().create(classz);
    }


    /**
     * 请求服务器
     *
     * @param abstractFunc 请求信息
     */
    @SuppressWarnings("unchecked")
    public void request(AbstractFunc abstractFunc, AbstractObserver abstractObserver) {
        Observable observable;
        if (abstractFunc.getContextSoftReference() != null
                && (abstractFunc.getContextSoftReference() instanceof RxAppCompatActivity
                || abstractFunc.getContextSoftReference() instanceof RxActivity
                || abstractFunc.getContextSoftReference() instanceof RxFragmentActivity)) {
            LifecycleProvider<ActivityEvent> lifecycleProvider = (LifecycleProvider<ActivityEvent>) abstractFunc.getContextSoftReference();
            observable = abstractFunc.getObservable(getRequestInterface(abstractFunc.isFileUpload(), abstractFunc.getRequestInterfaceClass()))
                    //失败后的重试配置
                    .retryWhen(new RequestExceptionRetry())
                    //生命周期管理
                    .compose(lifecycleProvider.bindToLifecycle())
                    //http请求线程
                    .subscribeOn(Schedulers.io())
                    //取消请求线程
                    .unsubscribeOn(Schedulers.io())
                    //请求服务器的回调主线程
                    .observeOn(AndroidSchedulers.mainThread())
                    //结果判断，成功或失败
                    .map(abstractFunc);
        } else {
            observable = abstractFunc.getObservable(getRequestInterface(abstractFunc.isFileUpload(), abstractFunc.getRequestInterfaceClass()))
                    //失败后的重试配置
                    .retryWhen(new RequestExceptionRetry())
                    //http请求线程
                    .subscribeOn(Schedulers.io())
                    //取消请求线程
                    .unsubscribeOn(Schedulers.io())
                    //请求服务器的回调主线程
                    .observeOn(AndroidSchedulers.mainThread())
                    //结果判断，成功或失败
                    .map(abstractFunc);
        }

        observable.subscribe(abstractObserver);

    }

    /**
     * 请求服务器, 返回数据结构统一封装
     *
     * @param abstractRequestFunc 请求信息
     */
    public void request(AbstractRequestFunc abstractRequestFunc) {
        request(abstractRequestFunc, new RequestObserver(abstractRequestFunc));
    }

    /**
     * 请求服务器，返回数据结构不统一
     *
     * @param abstractFunc 请求信息
     */
    public void request(AbstractFunc abstractFunc) {
        request(abstractFunc, new OriginalRequestObserver(abstractFunc));
    }
}