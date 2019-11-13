package cn.sts.base.model.server.request;

import android.content.Context;

import java.lang.ref.SoftReference;

import cn.sts.base.R;
import cn.sts.base.model.listener.IRequestListener;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 请求数据统一封装类Function
 * T 获取请求服务器接口  E 调用接口返回的统一数据类型
 * Created by weilin on 16/11/16.
 */
public abstract class AbstractFunc<T, E> implements Function<E, E> {


    /**
     * rx生命周期管理
     */
    private SoftReference<Context> contextSoftReference;
    /**
     * 请求回调监听
     */
    private IRequestListener requestListener;

    /**
     * 是否显示等待框
     */
    private boolean isShowProgress = true;

    /**
     * 是否可以取消等待框
     */
    private boolean isCancelableProgress = true;

    /**
     * 是否包含文件
     */
    private boolean isFileUpload = false;

    private String progressMessage;

    public AbstractFunc() {

    }

    /**
     * 请求数据封装类
     *
     * @param requestListener 服务器请求回调监听
     */
    public AbstractFunc(IRequestListener requestListener) {
        setRequestListener(requestListener);
    }

    /**
     * 请求数据封装类
     *
     * @param context         context
     * @param requestListener 服务器请求回调监听
     */
    public AbstractFunc(Context context, IRequestListener requestListener) {
        setContextSoftReference(context);
        setRequestListener(requestListener);
    }

    /**
     * 获取是否显示等待框，默认显示
     */
    public boolean isShowProgress() {
        return isShowProgress;
    }

    /**
     * 设置是否显示等待框
     */
    public void setShowProgress(boolean showProgress) {
        this.isShowProgress = showProgress;
    }

    /**
     * 设置是否可以取消等待框
     */
    public void setCancelableProgress(boolean isCancelableProgress) {
        this.isCancelableProgress = isCancelableProgress;
    }

    /**
     * 是否可以取消等待框
     */
    public boolean isCancelableProgress() {
        return isCancelableProgress;
    }

    /**
     * 是否有文件上传
     */
    public boolean isFileUpload() {
        return isFileUpload;
    }

    /**
     * 设置是否上传文件，默认否
     */
    public void setFileUpload(boolean fileUpload) {
        isFileUpload = fileUpload;
    }


    /**
     * 获取等待提示信息
     */
    public String getProgressMessage() {
        if (contextSoftReference != null && contextSoftReference.get() != null && progressMessage == null) {
            return contextSoftReference.get().getString(R.string.app_waiting);
        }
        return progressMessage;
    }

    /**
     * 设置等待提示信息，默认：请稍后
     */
    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    /**
     * 获取回调监听
     */
    public IRequestListener getRequestListener() {
        return requestListener;
    }

    /**
     * 设置回调监听
     */
    public void setRequestListener(IRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    /**
     * 设置当前rx生命周期
     */
    public void setContextSoftReference(Context context) {
        this.contextSoftReference = new SoftReference<>(context);
    }

    /**
     * 获取当前rx生命周期
     */
    public Context getContextSoftReference() {
        if (contextSoftReference != null) {
            return contextSoftReference.get();
        }
        return null;
    }


    @Override
    public E apply(@NonNull E requestResult) throws Exception {
        return requestResult;
    }


    /**
     * 获取调用服务器的Observable(被观察者)
     *
     * @param iRequestServer 请求的接口实例
     * @return 被观察者
     */
    public abstract Observable getObservable(T iRequestServer);


    /**
     * 获取请求服务器接口class
     *
     * @return 接口class
     */
    public abstract Class<T> getRequestInterfaceClass();
}
