package cn.sts.base.model.server.request;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import cn.sts.base.R;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.exception.RequestException;
import cn.sts.base.view.widget.ProgressDialog;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 在Http请求开始，显示ProgressDialog
 * 在Http请求结束，关闭ProgressDialog
 * 调用者自己对请求返回的数据进行处理
 * T 回调的具体数据类型，E 调用接口返回的数据类型
 * Created by weilin on 16/11/16.
 */
public abstract class AbstractObserver<T, E> implements Observer<E> {

    private static final String TAG = "RequestObserver";

    /**
     * 回调接口
     */
    public IRequestListener<T> requestListener;
    /**
     * 软引用防止内存泄露
     */
    private SoftReference<Context> contextSoftReference;
    /**
     * 请求数据
     */
    private AbstractFunc abstractRequestFunc;

    /**
     * 等待对话框
     */
    private ProgressDialog progressDialog;

    /**
     * 用它来切断Observer(观察者)与Observable(被观察者)之间的连接，
     * 当调用它的dispose()方法时, 它就会将Observer(观察者)与Observable(被观察者)
     * 之间的连接切断, 从而使Observer(观察者)收不到事件
     */
    private Disposable disposable;

    /**
     * 构造
     */
    @SuppressWarnings("unchecked")
    public AbstractObserver(AbstractFunc abstractRequestFunc) {
        this.abstractRequestFunc = abstractRequestFunc;
        this.requestListener = abstractRequestFunc.getRequestListener();
        this.contextSoftReference = new SoftReference<>(abstractRequestFunc.getContextSoftReference());
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.disposable = disposable;
        if (abstractRequestFunc.isShowProgress()) {
            showProgress();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e 异常
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dismissProgress();
        String message = null;
        if (contextSoftReference.get() != null) {
            if (e instanceof SocketTimeoutException) {
                //"请求超时，请检查您的网络";
                message = contextSoftReference.get().getString(R.string.exception_timeout);
            } else if (e instanceof SocketException) {
                //"网络中断，请检查您的网络状态";
                message = contextSoftReference.get().getString(R.string.exception_no_network);
            } else if (e instanceof RequestException) {
                message = e.getMessage();
            } else {
                message = contextSoftReference.get().getString(R.string.exception_error);
            }
        }
        if (message == null || message.length() == 0) {
            message = "Request failed";
        }
        if (requestListener != null) {
            requestListener.onRequestFailure(message);
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgress();
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅
     */
    private void cancelProgress() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        requestListener.onRequestCancel();
        dismissProgress();
    }


    /**
     * 显示等待对话框
     */
    private void showProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (contextSoftReference.get() == null
                || (contextSoftReference.get() instanceof Activity
                && ((Activity) contextSoftReference.get()).isDestroyed())) {
            return;
        }
        progressDialog = new ProgressDialog(contextSoftReference.get())
                .cancelable(abstractRequestFunc.isCancelableProgress())
                .cancelListener(new ProgressDialog.OnCancelListener() {
                    @Override
                    public void onCancel(ProgressDialog progressDialog) {
                        cancelProgress();
                    }
                });
        progressDialog.show();
    }

    /**
     * 隐藏等待对话框
     */
    private void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}