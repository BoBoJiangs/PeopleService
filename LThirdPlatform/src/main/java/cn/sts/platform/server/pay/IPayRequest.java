package cn.sts.platform.server.pay;


import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import cn.sts.platform.server.pay.bean.WxPayParam;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 支付接口
 * Created by weilin on 2019/7/1.
 */
public interface IPayRequest {


    /**
     * 微信支付
     */
    @POST("app/order/getWx")
    Observable<RequestResult<WxPayParam>> getWXPayInfo(@Body Map parameter);

    /**
     * 支付宝支付
     */
    @PUT("orders/{id}/pay")
    Observable<RequestResult<String>> getAliPayInfo(@Path("id") String id);

}
