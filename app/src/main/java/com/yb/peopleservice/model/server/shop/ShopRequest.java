package com.yb.peopleservice.model.server.shop;


import com.yb.peopleservice.model.bean.ClassifyListBean;
import com.yb.peopleservice.model.database.bean.ShopInfo;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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
    @PUT("shop/api/shops")
    Observable<RequestResult<ShopInfo>> putShopInfo(@Body ShopInfo shopInfo);

    /**
     * 获取店铺信息
     */
    @GET("shop/api/shops")
    Observable<RequestResult<ShopInfo>> getShopInfo();
}
