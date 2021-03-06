package com.yb.peopleservice.model.server.user;


import android.accounts.Account;

import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.order.MyCouponBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.order.OrderListBean;
import com.yb.peopleservice.model.bean.user.service.EvaluateBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;

import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 类描述:服务相关
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface ServiceRequest {


    /**
     *定时刷新服务人员位置
     */
    @POST("staffs/location")
    Observable<RequestResult<List<ServiceInfo>>> addGps(@Body Map map);

    /**
     *定时刷新店铺位置
     */
    @POST("shops/location")
    Observable<RequestResult<List<ShopInfo>>> addShopGps(@Body Map map);

    /**
     * 查询附近服务列表
     */
    @GET("staffs/nearby")
    Observable<RequestResult<List<ServiceInfo>>> getNearbyServiceList(@QueryMap Map<String, Double> parameter);

    /**
     * 查询附近店铺列表
     */
    @GET("shops/nearby")
    Observable<RequestResult<List<ShopInfo>>> getNearbyShopList(@QueryMap Map<String, Double> parameter);


    /**
     * 搜索商品
     */
    @GET("commodities")
    Observable<RequestResult<List<ServiceListBean>>> searchData(@QueryMap Map<String, String> parameter);

    /**
     * 查询服务列表
     */
    @GET("categories/{id}/commodities")
    Observable<RequestResult<List<ServiceListBean>>> getServiceList(@Path("id") String id,
                                                                    @QueryMap Map<String, String> parameter);

    /**
     * 查询服务正在团购的团
     */
    @GET("commodities/{id}/buygroup")
    Observable<RequestResult<List<GroupBean>>> buyGroup(@Path("id") String id);

    /**
     * 查询团购服务列表
     */
    @GET("commodities/groupbuy")
    Observable<RequestResult<List<ServiceListBean>>> groupBuyList(@QueryMap Map<String, String> parameter);

    /**
     * 获取店铺下的所有商品
     */
    @GET("shops/{id}/commodities")
    Observable<RequestResult<List<ServiceListBean>>> getShopList(@Path("id") String id,
                                                                 @QueryMap Map<String, String> parameter);

    /**
     * 获取店铺下的所有商品
     */
    @GET("shops/{id}")
    Observable<RequestResult<ShopInfo>> getShopDetails(@Path("id") String id);

    /**
     * 增加一个收藏
     */
    @POST("favorites")
    Observable<RequestResult<FavoriteBean>> addFavorite(@Body Map map);

    /**
     * 获取对象是否已收藏
     */
    @POST("favorites/check/{id}")
    Observable<RequestResult<FavoriteBean>> getFavorite(@Path("id") String id);

    /**
     * 获取收藏的商品
     */
    @GET("favorites?type=1")
    Observable<RequestResult<List<ServiceListBean>>> getFavoriteService();

    /**
     * 获取收藏的店铺
     */
    @GET("favorites?type=2")
    Observable<RequestResult<List<ShopInfo>>> getFavoriteShop();

    /**
     * 删除收藏
     */
    @DELETE("favorites/{id}")
    Observable<RequestResult<ShopInfo>> deleteFavorite(@Path("id") String id);

    /**
     * 评价列表
     */
    @GET("commodities/evaluates")
    Observable<RequestResult<List<EvaluateBean>>> evaluates(@QueryMap Map<String, String> parameter);

    /**
     * 优惠卷列表
     */
    @GET("coupons")
    Observable<RequestResult<List<MyCouponBean>>> couponsList();

    /**
     * 优惠卷列表
     */
    @POST("coupons/{couponId}")
    Observable<RequestResult<CouponBean>> getCoupons(@Path("couponId") String id);

    /********************************** 订单相关 **************************************/

    /**
     * 获取默认地址
     */
    @GET("addresses/default")
    Observable<RequestResult<List<AddressListVO>>> getDefaultAddress();

    /**
     * 查询某个商品的优惠券
     */
    @GET("commodities/{id}/coupon")
    Observable<RequestResult<List<CouponBean>>> getCouponList(@Path("id") String id);

    /**
     * 下单
     */
    @POST("orders")
    Observable<RequestResult<OrderBean>> placeOrder(@Body OrderBean map);

    /**
     * 获取订单状态列表
     */
    @GET("orders")
    Observable<RequestResult<List<OrderListBean>>> getOrders(@QueryMap Map<String, Integer> parameter);


    /**
     * 订单评论
     */
    @POST("orders/evaluate")
    Observable<RequestResult> evaluatesOrder(@Body Map map);

    /**
     * 申请退款
     */
    @POST("orders/refund")
    Observable<RequestResult> refundOrder(@Body Map map);
}
