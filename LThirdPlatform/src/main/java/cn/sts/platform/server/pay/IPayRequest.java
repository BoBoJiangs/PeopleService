package cn.sts.platform.server.pay;


import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import cn.sts.platform.server.pay.bean.WxPayParam;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 支付接口
 * Created by weilin on 2019/7/1.
 */
public interface IPayRequest {


    /**
     * 微信支付
     */
    @GET("pay/wxpay/app")
    Observable<RequestResult<WxPayParam>> getWXPayInfo(@QueryMap Map<String, String> parameter);

    /**
     * 微信支付(增加金额)
     */
    @GET("pay/wxpay/app/add")
    Observable<RequestResult<String>> getAddWXPayInfo(@QueryMap Map<String, String> parameter);

    /**
     * 支付宝支付
     */
    @GET("pay/alipay/app")
    Observable<RequestResult<String>> getAliPayInfo(@QueryMap Map<String, String> parameter);

    /**
     * 获得支付宝app拉起信息,增加金额
     */
    @GET("pay/alipay/app/add")
    Observable<RequestResult<String>> getAddAliPayInfo(@QueryMap Map<String, String> parameter);
}
