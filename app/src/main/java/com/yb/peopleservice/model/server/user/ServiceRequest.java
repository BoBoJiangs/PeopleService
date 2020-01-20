package com.yb.peopleservice.model.server.user;


import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;

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
     * 查询附近服务列表
     */
    @GET("staffs/nearby")
    Observable<RequestResult<List<ServiceListBean>>> getNearbyServiceList(@QueryMap Map<String, Double> parameter);

    /**
     * 查询附近店铺列表
     */
    @GET("staffs/nearby")
    Observable<RequestResult<List<ShopInfo>>> getNearbyShopList(@QueryMap Map<String, Double> parameter);

    /**
     * 查询服务列表
     */
    @GET("categories/{id}/commodities")
    Observable<RequestResult<List<ServiceListBean>>> getServiceList(@Path("id") String id);

    /**
     * 获取店铺下的所有商品
     */
    @GET("shops/{id}/commodities")
    Observable<RequestResult<List<ServiceListBean>>> getShopList(@Path("id") String id);

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

    /********************************** 订单相关 **************************************/

    /**
     * 获取默认地址
     */
    @GET("addresses/default")
    Observable<RequestResult<AddressListVO>> getDefaultAddress();

    /**
     * 查询某个商品的优惠券
     */
    @GET("coupons/commodities/{id}")
    Observable<RequestResult<List<CouponBean>>> getCouponList(@Path("id") String id);

    /**
     * 下单
     */
    @POST("orders")
    Observable<RequestResult<OrderBean>> placeOrder(@Body Map map);

    /**
     * 获取可以申请入驻店铺的信息
     */
    @GET("orders")
    Observable<RequestResult<List<OrderBean>>> getOrders(@QueryMap Map<String, Integer> parameter);
}
