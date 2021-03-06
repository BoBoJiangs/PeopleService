package com.yb.peopleservice.model.server.user.classify;


import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.BannerListVO;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.HomeListBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 类描述:用户登录
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface HomeRequest {



    /**
     * 获取首页的大图
     */
    @GET("mainpage/bannerimgs")
    Observable<RequestResult<List<BannerListVO>>> getBannerList();

    /**
     * 获取首页的热门服务
     */
    @GET("categories/all")
    Observable<RequestResult<List<ClassifyListBean>>> getHotList(@QueryMap Map<String,String> map);

    /**
     * 获取首页的热销服务
     */
    @GET("mainpage/categories/hotsales")
    Observable<RequestResult<List<HomeListBean>>> getHotService(@QueryMap Map<String,String> map);

    /********************************** 地址相关 **************************************/

    /**
     *
     *
     * 新增收货地址(app用户使用)
     */
    @POST("addresses")
    Observable<RequestResult> addAddress(@Body AddressListVO parameter);

    /**
     * 删除收货地址
     */
    @DELETE("addresses/{id}")
    Observable<RequestResult> deleteAddress(@Path("id") String id);

    /**
     * 修改收货地址
     */
    @PUT("addresses")
    Observable<RequestResult> updateAddress(@Body AddressListVO parameter);

    /**
     * 查询地址列表
     */
    @GET("addresses")
    Observable<RequestResult<List<AddressListVO>>> getMyAddressList();

    /**
     * 用户详情
     */
    @GET("customers/self")
    Observable<RequestResult<UserInfoBean>> getUserInfo();

    /**
     * 修改用户信息
     */
    @PUT("customers/self")
    Observable<RequestResult<UserInfoBean>> setUserInfo(@Body UserInfoBean parameter);

    /**
     * 修改密码
     */
    @PUT("customers/password")
    Observable<RequestResult<UserInfoBean>> password(@Body Map map);
    /**
     * 修改密码(店铺管理员)
     */
    @PUT("managers/password")
    Observable<RequestResult<UserInfoBean>> passwordManager(@Body Map map);
    /**
     * 修改密码(服务人员)
     */
    @PUT("staffs/password")
    Observable<RequestResult<UserInfoBean>> passwordStaffs(@Body Map map);
}
