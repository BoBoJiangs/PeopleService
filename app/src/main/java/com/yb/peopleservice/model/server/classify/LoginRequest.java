package com.yb.peopleservice.model.server.classify;


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
     * 获取验证码
     */
    @GET("code/phone")
    Observable<RequestResult> getCode(@Query("phone") String phone);

}
