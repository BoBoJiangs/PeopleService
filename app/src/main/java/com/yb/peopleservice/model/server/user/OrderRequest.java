package com.yb.peopleservice.model.server.user;


import com.yb.peopleservice.model.bean.user.ClassifyListBean;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 类描述:用户相关
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface OrderRequest {

    /**
     * 接受订单（服务人员）
     */
    @POST("orders/{orderId}/accept")
    Observable<RequestResult> acceptOrder(@Path("orderId") String orderId);

    /**
     * 拒绝订单（服务人员）
     */
    @POST("orders/{orderId}/refuse")
    Observable<RequestResult> refuseOrder(@Path("orderId") String orderId);

    /**
     * 服务人员抵达（服务人员）
     */
    @POST("orders/{id}/arrive")
    Observable<RequestResult> arriveOrder(@Path("id") String id);

    /**
     * 开始服务（用户）
     */
    @POST("orders/{id}/start")
    Observable<RequestResult> startOrder(@Path("id") String id);

    /**
     * 服务完成（服务人员）
     */
    @POST("orders/{id}/confirm")
    Observable<RequestResult> endOrder(@Path("id") String id);

    /**
     * 拒绝退款
     */
    @POST("orders/{id}/refund/close")
    Observable<RequestResult> closeRefund(@Path("id") String id);

    /**
     * 同意退款
     */
    @POST("orders/{id}/confirm")
    Observable<RequestResult> confirmRefund(@Path("id") String id);

}
