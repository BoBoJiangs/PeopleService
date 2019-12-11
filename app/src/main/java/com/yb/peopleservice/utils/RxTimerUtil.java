package com.yb.peopleservice.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 项目名称:延时操作
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2018/12/12 13:48
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class RxTimerUtil {
    private static Disposable mSubscription;


    /**
     * milliseconds毫秒后执行next操作
     *      *
     *      * @param milliseconds
     *      * @param next
     *      
     */

    public static void timer(long milliseconds, final IRxNext next) {
        mSubscription = Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(number -> {
                    if (next != null) {
                        next.doNext(number);
                    }
                    //取消订阅
                    cancel();
                });
    }

    /**
     * 每隔milliseconds毫秒后执行next操作
     *      *
     *      * @param milliseconds
     *      * @param next
     *      
     */

    public static void interval(long milliseconds, final IRxNext next) {
        mSubscription = Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long number) throws Exception {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }
                });
    }


    /**
     *      * 取消订阅
     *      
     */


    public static void cancel() {
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
            mSubscription = null;
        }
    }


    public interface IRxNext {

        void doNext(long number);
    }
}
