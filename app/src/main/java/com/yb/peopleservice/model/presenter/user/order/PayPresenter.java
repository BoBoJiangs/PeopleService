package com.yb.peopleservice.model.presenter.user.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yb.peopleservice.model.server.BaseRequestServer;

import java.util.HashMap;
import java.util.Map;

import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.util.AppManageUtil;
import cn.sts.base.util.Logs;
import cn.sts.base.util.StringUtils;
import cn.sts.platform.constant.ThirdPlatformBroadcastConstant;
import cn.sts.platform.constant.ThirdPlatformIntentKeyConstant;
import cn.sts.platform.constant.ThirdPlatformPayConstant;
import cn.sts.platform.constant.ThirdPlatformStatusConstant;
import cn.sts.platform.server.pay.AbstractPayRequestFunc;
import cn.sts.platform.server.pay.IPayRequest;
import cn.sts.platform.server.pay.PayRequestServer;
import cn.sts.platform.server.pay.bean.AliPayResult;
import cn.sts.platform.server.pay.bean.WxPayParam;
import cn.sts.platform.util.ThirdPlatformUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 支付
 * Created by weilin on 2019-07-01.
 */
public class PayPresenter {

    private static final String TAG = "PayPresenter";


    private Context context;


    public PayPresenter(Context context) {
        this.context = context;
    }


    /**
     * 微信支付
     *
     * @param orderId      订单id
     * @param title        订单标题
     * @param businessType 业务类型
     */
    public void wxPay(String orderId, String money,boolean isPayAdd) {

        AbstractPayRequestFunc requestFunc = new AbstractPayRequestFunc(context, new IRequestListener<WxPayParam>() {
            @Override
            public void onRequestSuccess(WxPayParam data) {
                PayReq req = new PayReq();

                req.appId = data.getAppId();
                req.partnerId = data.getMchId();
                req.prepayId = data.getPrepayId();
                req.nonceStr = data.getNonceStr();
                req.timeStamp = data.getTimeStamp();
                req.packageValue = "Sign=WXPay";

                req.sign = data.getSign();
                ThirdPlatformUtil.getIWXAPI().registerApp(data.getAppId());
                boolean status = ThirdPlatformUtil.getIWXAPI().sendReq(req);
                Logs.i(TAG, "调用微信 status=" + status);
                if (!status) {
                    sendPayResult(ThirdPlatformPayConstant.WX_PAY, ThirdPlatformStatusConstant.FAIL);
                }
            }

            @Override
            public void onRequestFailure(String error) {
                sendPayResult(ThirdPlatformPayConstant.WX_PAY, ThirdPlatformStatusConstant.FAIL, "支付失败:" + error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(IPayRequest iRequestServer) {
                Map<String, String> map = new HashMap<>(2);
                if (isPayAdd){
                    map.put("orderId", orderId);
                    map.put("money", money);
                    return iRequestServer.getAddWXPayInfo(map);
                }else{
                    map.put("orderId", orderId);
                    return iRequestServer.getWXPayInfo(map);
                }

            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 支付宝支付
     *
     * @param orderId 订单id
     */
    public void aliPay(String orderId,String money,boolean isPayAdd) {
        AbstractPayRequestFunc requestFunc = new AbstractPayRequestFunc(context, new IRequestListener<String>() {
            @Override
            public void onRequestSuccess(String data) {
                //网络请求
                Observable
                        .create(new ObservableOnSubscribe<Map<String, String>>() {
                            @Override
                            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                                PayTask payTask = new PayTask((Activity) context);
                                Map<String, String> resultMap = payTask.payV2(data, true);
                                emitter.onNext(resultMap);
                                emitter.onComplete();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Map<String, String>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Map<String, String> object) {
                                AliPayResult aliPayResult = new AliPayResult(object);
                                //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知
                                // 同步返回需要验证的信息
//                                String resultInfo = aliPayResult.getResult();
                                String resultStatus = aliPayResult.getResultStatus();

                                int payResult;
                                switch (resultStatus) {
                                    case "9000":
                                        payResult = ThirdPlatformStatusConstant.SUCCESS;
                                        break;
                                    case "6001":
                                        payResult = ThirdPlatformStatusConstant.CANCEL;
                                        break;
                                    default:
                                        payResult = ThirdPlatformStatusConstant.FAIL;
                                        break;
                                }
                                sendPayResult(ThirdPlatformPayConstant.ALI_PAY, payResult);

                            }

                            @Override
                            public void onError(Throwable e) {
                                sendPayResult(ThirdPlatformPayConstant.ALI_PAY, ThirdPlatformStatusConstant.FAIL);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void onRequestFailure(String error) {

                sendPayResult(ThirdPlatformPayConstant.ALI_PAY, ThirdPlatformStatusConstant.FAIL);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(IPayRequest iRequestServer) {
                Map<String, String> map = new HashMap<>(2);
                if (isPayAdd){
                    map.put("orderId", orderId);
                    map.put("money", money);
                    return iRequestServer.getAddAliPayInfo(map);
                }else{
                    map.put("orderId", orderId);
                    return iRequestServer.getAliPayInfo(map);
                }


            }
        };
        requestFunc.setShowProgress(false);
        BaseRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 通过广播发送支付结果
     *
     * @param payType   支付方式
     * @param payResult 支付结果
     */
    public void sendPayResult(String payType, int payResult) {
        sendPayResult(payType, payResult, null);
    }

    /**
     * 通过广播发送支付结果
     *
     * @param payType   支付方式
     * @param payResult 支付结果
     * @param msg       支付结果消息
     */
    private void sendPayResult(String payType, int payResult, String msg) {
        if (StringUtils.isNotBlank(msg)) {
            ToastUtils.showLong(msg);
        } else {
            switch (payResult) {
                case ThirdPlatformStatusConstant.SUCCESS:
                    ToastUtils.showLong("支付成功");
                    break;
                case ThirdPlatformStatusConstant.CANCEL:
                    ToastUtils.showLong("支付取消");
                    break;
                default:
                    ToastUtils.showLong("支付失败");
                    break;
            }
        }

        Intent intent = new Intent(ThirdPlatformBroadcastConstant.PAY_RESULT);
        intent.putExtra(ThirdPlatformIntentKeyConstant.PAY_TYPE, payType);
        intent.putExtra(ThirdPlatformIntentKeyConstant.PAY_RESULT, payResult);
        context.sendBroadcast(intent);
    }
}
