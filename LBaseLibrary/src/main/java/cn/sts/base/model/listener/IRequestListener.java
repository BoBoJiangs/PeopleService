package cn.sts.base.model.listener;

/**
 * 服务器请求回调监听
 * Created by weilin on 16/11/16.
 */

public interface IRequestListener<T> {

    /**
     * 请求返回成功
     *
     * @param data 数据
     */
    void onRequestSuccess(T data);

    /**
     * 请求返回失败
     *
     * @param error 错误信息
     */
    void onRequestFailure(String error);

    /**
     * 请求取消
     */
    void onRequestCancel();
}
