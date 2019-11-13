package cn.sts.base.callback;

/**
 * 响应结果回调
 * Created by weilin on 2018/10/9.
 */
public abstract class AbstractViewCallback<T> implements IViewCallback {

    /**
     * 成功结果回调
     *
     * @param data 返回数据
     */
    public void success(T data) {
        complete();
    }

    /**
     * 失败结果回调
     *
     * @param message 返回消息
     */
    public void fail(String message) {
        complete();
    }

    /**
     * 完成
     */
    public void complete() {

    }
}
