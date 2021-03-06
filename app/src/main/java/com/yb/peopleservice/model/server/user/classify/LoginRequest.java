package com.yb.peopleservice.model.server.user.classify;


import com.yb.peopleservice.model.bean.PlatformLoginBean;
import com.yb.peopleservice.model.database.bean.User;

import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 类描述:用户登录注册
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface LoginRequest {

    /**
     * 注册
     */
    @POST("register")
    Observable<RequestResult> register(@Body Map map);

    /**
     * 验证手机号(是否已被注册)
     */
    @POST("register/checkusername")
    Observable<RequestResult<Boolean>> checkUserName(@Query("phone") String phone);


    /**
     * 登录
     */
    @GET("oauth0/authorize/APP")
    Observable<RequestResult<User>> login(@Query("username") String phone, @Query("grant_type") String grant_type,
                                          @Query("password") String password);

    /**
     * 登录
     */
    @POST("oauth0/authorize/APP")
    Observable<RequestResult<User>> login(@Body Map map);

    /**
     * 微信登录
     */
    @GET("wx/login")
    Observable<RequestResult<PlatformLoginBean>> wxLogin(@QueryMap Map<String,String> map);

    /**
     * 登录
     */
    @GET("alipay/login")
    Observable<RequestResult<PlatformLoginBean>> aliPayLogin(@QueryMap Map<String,String> map);

    /**
     * 退出
     */
    @GET("logout")
    Observable<RequestResult<User>> logout();

    /**
     * 快速登录（获取token）
     */
    @POST("oauth0/authorize/code/APP")
    Observable<RequestResult<User>> quickLogin(@Body Map map);

    /**
     *  快速登录获取登录凭证
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    @POST("oauth0/code")
    Observable<RequestResult<String>> getLoginVoucher(@Query("phone") String phone, @Query("code") String code);

    /**
     * 获取验证码
     */
    @GET("code/phone")
    Observable<RequestResult> getCode(@Query("phone") String phone);

    /**
     * 获取验证码
     */
    @POST("oauth0/resetpass")
    Observable<RequestResult> resetPass(@Body Map map);

    /**
     * 获取支付宝授权码
     */
    @GET("alipay/authinfo")
    Observable<RequestResult<String>> authInfo();

}
