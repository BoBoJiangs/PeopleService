package com.yb.peopleservice.model.server.user.classify;


import com.yb.peopleservice.model.bean.LoginBean;

import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
     * 登录
     */
    @GET("oauth0/authorize/APP")
    Observable<RequestResult<LoginBean>> login(@Query("username") String phone, @Query("grant_type") String grant_type,
                                               @Query("password") String password);


    /**
     * 登录
     */
    @GET("logout")
    Observable<RequestResult<LoginBean>> logout();

    /**
     * 快速登录（获取token）
     */
    @GET("oauth0/authorize/APP")
    Observable<RequestResult<LoginBean>> quickLogin(@Query("phone") String phone, @Query("code") String code);

    /**
     *  快速登录获取登录凭证
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    @GET("oauth0/code")
    Observable<RequestResult<String>> getLoginVoucher(@Query("phone") String phone, @Query("code") String code);

    /**
     * 获取验证码
     */
    @GET("code/phone")
    Observable<RequestResult> getCode(@Query("phone") String phone);

}
