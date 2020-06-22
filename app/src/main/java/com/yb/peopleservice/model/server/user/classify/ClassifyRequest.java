package com.yb.peopleservice.model.server.user.classify;


import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 类描述:用户登录
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface ClassifyRequest {

    /**
     * 查询分类信息列表
     */
    @GET("categories/all")
    Observable<RequestResult<List<ClassifyListBean>>> getCategoryInfo();

    /**
     * 查询分类信息列表
     */
    @GET("categories/{categoryId}/commodity")
    Observable<RequestResult<ServiceListBean>> quickOrder(@Path("categoryId") String categoryId);
}
