package com.yb.peopleservice.model.server.user;


import com.yb.peopleservice.model.bean.ClassifyListBean;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 类描述:用户相关
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface UserRequest {

    /**
     * 顾客获取个人信息
     */
    @GET("api/customers/self")
    Observable<RequestResult<List<ClassifyListBean>>> getUserInfo(@Query("parentId") int parentId);

}
