package cn.sts.base.model.server.request;

/**
 * 在Http请求开始，显示ProgressDialog
 * 在Http请求结束，关闭ProgressDialog
 * 调用者自己对请求返回的数据进行处理
 * Created by weilin on 16/11/16.
 */
public class OriginalRequestObserver<T> extends AbstractObserver<T, T> {


    /**
     * 构造
     *
     * @param abstractFunc 请求数据统一封装类Function
     */
    public OriginalRequestObserver(AbstractFunc abstractFunc) {
        super(abstractFunc);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (requestListener != null) {
            requestListener.onRequestSuccess(t);
        }
    }
}