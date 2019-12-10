package com.yb.peopleservice.model.server.classify;


import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
     * 查询分类信息列表
     */
    @POST("register")
    Observable<RequestResult> register(@Body Map map);

}
