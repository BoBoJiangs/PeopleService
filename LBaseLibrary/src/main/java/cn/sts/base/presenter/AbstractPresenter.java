package cn.sts.base.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.util.Logs;

/**
 * Presenter基类
 * Created by weilin on 2018/10/9.
 */
public abstract class AbstractPresenter<V extends IViewCallback> {

    private static final String TAG = "AbstractPresenter";

    public Context context;
    private V viewCallBack;

    public AbstractPresenter(Context context) {
        this.context = context;
    }

    public AbstractPresenter(Context context, V viewCallBack) {
        this.context = context;
        this.viewCallBack = viewCallBack;
    }

    /**
     * Presenter被创建后调用
     * 若要恢复存储的数据，需要子类重写此方法
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreate(@Nullable Bundle savedState) {
        Logs.i(TAG, "---onCreatePresenter----");
    }

    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View
     * 中的onSaveInstanceState时机相同，有可能Presenter也需要对应做些相应处理
     * 若需要存储、释放数据，需要子类重写此方法
     *
     * @param outState 销毁的时候的Bundle
     */
    public void onSaveInstanceState(Bundle outState) {
        Logs.i(TAG, "-----onSaveInstanceState----- ");
    }

    /**
     * 解除绑定
     */
    public void unbind() {
        viewCallBack = null;
        context = null;
    }

    /**
     * 获取界面回调
     *
     * @return IViewCallBack
     * @throws Exception 被回收或者未绑定回调抛出异常
     */
    public V getViewCallBack() throws Exception {
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isDestroyed()) {
                throw new Exception(getClass().getName() + "注意：Activity已被回收，绑定的回调函数已失效");
            }
        } else if (viewCallBack == null) {
            throw new Exception(getClass().getName() + "注意：View绑定的回调函数为空，可能被回收或从未设置");
        }
        return viewCallBack;
    }


}
