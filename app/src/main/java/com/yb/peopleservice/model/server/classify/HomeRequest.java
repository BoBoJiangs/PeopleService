package com.yb.peopleservice.model.server.classify;


import com.yb.peopleservice.model.bean.ClassifyListBean;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    Observable<RequestResult<List<ClassifyListBean>>> getBannerList();

}
