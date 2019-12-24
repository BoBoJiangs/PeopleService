package com.yb.peopleservice.model.server.user.classify;


import com.yb.peopleservice.model.bean.user.BannerListVO;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;

import java.util.List;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

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
    @GET("mainpage/categories")
    Observable<RequestResult<List<ClassifyListBean>>> getHotList();
}
