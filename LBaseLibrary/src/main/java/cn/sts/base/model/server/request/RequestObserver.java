package cn.sts.base.model.server.request;

import cn.sts.base.model.server.vo.RequestResult;

/**
 * 在Http请求开始，显示ProgressDialog
 * 在Http请求结束，关闭ProgressDialog
 * 调用者自己对请求返回的数据进行处理
 * Created by weilin on 16/11/16.
 */
public class RequestObserver<T> extends AbstractObserver<T, RequestResult<T>> {

    private static final String TAG = "RequestObserver";

    /**
     * 构造
     *
     * @param abstractRequestFunc 请求数据统一封装类Function
     */
    public RequestObserver(AbstractRequestFunc abstractRequestFunc) {
        super(abstractRequestFunc);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param tRequestResult 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(RequestResult<T> tRequestResult) {
        if (requestListener != null) {
            requestListener.onRequestSuccess(tRequestResult.getObj());
        }
    }


}