package cn.sts.base.download;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 类描述:用于只暴露success和error
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2018/12/3  11:01
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseDownloadObserver<T> implements Observer<T> {
    @Override
    public abstract void onSubscribe(Disposable d);

    @Override 
    public void onNext(T t) {
        onDownloadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownloadError(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onDownloadSuccess(T t);

    protected abstract void onDownloadError(Throwable e);
}
