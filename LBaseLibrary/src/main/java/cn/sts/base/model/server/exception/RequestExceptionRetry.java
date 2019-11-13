package cn.sts.base.model.server.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * 请求发送异常重试次数
 * Created by weilin on 16/11/16.
 */
public class RequestExceptionRetry implements Function<Observable<? extends Throwable>, Observable<?>> {

    /**
     * 重试次数
     */
    private int count = 0;
    /**
     * 重试延迟时间，毫秒
     */
    private long delay = 3000;
    /**
     * 再次请求失败后叠加延迟时间，毫秒
     */
    private long increaseDelay = 3000;

    /**
     * 请求异常重试的构造方法
     */
    public RequestExceptionRetry() {

    }

    /**
     * 请求异常重试的构造方法
     *
     * @param count 重试次数
     * @param delay 重试延迟时间，毫秒
     */
    public RequestExceptionRetry(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    /**
     * 请求异常重试的构造方法
     *
     * @param count         重试次数
     * @param delay         重试延迟时间，毫秒
     * @param increaseDelay 再次请求失败后叠加延迟时间，毫秒
     */
    public RequestExceptionRetry(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable
                .zipWith(Observable.range(1, count + 1), new BiFunction<Throwable, Integer, TryInfo>() {
                    @Override
                    public TryInfo apply(@NonNull Throwable throwable, @NonNull Integer integer) throws Exception {
                        return new TryInfo(throwable, integer);
                    }
                }).flatMap(new Function<TryInfo, Observable<?>>() {
                    @Override
                    public Observable<?> apply(@NonNull TryInfo tryInfo) throws Exception {
                        if ((tryInfo.throwable instanceof ConnectException
                                || tryInfo.throwable instanceof SocketTimeoutException
                                || tryInfo.throwable instanceof TimeoutException)
                                && tryInfo.index <= count) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                            return Observable.timer(delay + (tryInfo.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(tryInfo.throwable);
                    }
                });
    }

    /**
     * 异常信息
     */
    private class TryInfo {
        private int index;
        private Throwable throwable;

        private TryInfo(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}
