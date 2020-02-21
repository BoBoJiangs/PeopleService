package com.yb.peopleservice.model.server.shop;


import android.accounts.Account;

import com.yb.peopleservice.model.bean.shop.BalanceBean;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.bean.shop.PersonListBean;
import com.yb.peopleservice.model.bean.shop.ServiceInfo;
import com.yb.peopleservice.model.bean.shop.ShopInfo;

import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
     * 查询本店铺下所有的服务人员关系列表
     */
    @GET("staffs")
    Observable<RequestResult<List<PersonListBean>>> shopPerson();

    /**
     * 指派服务员
     */
    @POST("orders/{id}/staffs/{staffId}")
    Observable<RequestResult<Object>> assignService(@Path("id") String id,@Path("staffId") String staffId);

    /**
     * 查询余额
     */
    @GET("balance")
    Observable<RequestResult<BalanceBean>> queryBalance();

    /**
     * 统计信息（账户信息）
     */
    @GET("statistics")
    Observable<RequestResult<List<BalanceBean>>> statistics(@QueryMap Map<String, String> map);

    /**
     * 店铺申请提现
     */
    @POST("withdraw")
    Observable<RequestResult<Object>> withDraw(@Body Map<String, Object> map);


    /***********************************服务人员相关接口 *******************************************/

    /**
     * 获取服务人员自己的所有信息
     */
    @GET("staffs/self")
    Observable<RequestResult<ServiceInfo>> getServiceInfo();

    /**
     * 服务人员认证
     */
    @PUT("staffs")
    Observable<RequestResult<ServiceInfo>> putServiceInfo(@Body ServiceInfo shopInfo);

    /**
     * 获取可以申请入驻店铺的信息
     */
    @GET("shops")
    Observable<RequestResult<List<ShopInfo>>> getShops(@QueryMap Map<String, Integer> parameter);

    /**
     * 获取自己入驻的店铺信息
     */
    @GET("shops/settledin")
    Observable<RequestResult<MyShop>> getMyShop();

    /**
     * 申请入驻到店铺
     */
    @POST("shops/{id}/settledin")
    Observable<RequestResult<Object>> applyEntryShop(@Path("id") String id);

    /**
     * 解除关联店铺
     */
    @POST("shops/{id}/unbound")
    Observable<RequestResult<Object>> unboundShop(@Path("id") String id);


    /**
     *
     * 取消申请入驻到店铺
     */
    @POST("shops/{id}/cancelsettledin")
    Observable<RequestResult<Object>> cancelaShop(@Path("id") String id);



}
