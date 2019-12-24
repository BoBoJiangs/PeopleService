package com.yb.peopleservice.model.server.user;


import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
     * 查询服务列表
     */
    @GET("categories/{id}/commodities")
    Observable<RequestResult<List<ServiceListBean>>> getServiceList(@Path("id") String id);


}
