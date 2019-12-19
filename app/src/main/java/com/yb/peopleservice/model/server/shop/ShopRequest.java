package com.yb.peopleservice.model.server.shop;


import com.yb.peopleservice.model.bean.shop.ServiceInfo;
import com.yb.peopleservice.model.database.bean.ShopInfo;

import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

/**
 * 类描述:店铺相关
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface ShopRequest {

    /**
     * 店铺认证
     */
    @PUT("shops")
    Observable<RequestResult<ShopInfo>> putShopInfo(@Body ShopInfo shopInfo);

    /**
     * 获取店铺信息
     */
    @GET("shops")
    Observable<RequestResult<ShopInfo>> getShopInfo();

    /**
     * 获取服务人员自己的所有信息
     */
    @GET("staffs/self")
    Observable<RequestResult<ServiceInfo>> getServiceInfo();


    /***********************************服务人员相关接口 *******************************************/

    /**
     * 服务人员认证
     */
    @PUT("staffs")
    Observable<RequestResult<ServiceInfo>> putServiceInfo(@Body ServiceInfo shopInfo);

    /**
     * 服务人员认证
     */
    @GET("shops")
    Observable<RequestResult<List<ShopInfo>>> getShops(@QueryMap Map<String, Integer> parameter);
}
