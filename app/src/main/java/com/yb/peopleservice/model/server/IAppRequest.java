package com.yb.peopleservice.model.server;


import com.yb.peopleservice.model.bean.VersionVO;

import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * App相关的请求
 * Created by sts on 2018/6/21.
 *
 * @author daichao
 */

public interface IAppRequest {

    /**
     * 检查版本
     */
    @GET("app/version")
    Observable<RequestResult<List<VersionVO>>> checkVersion(@QueryMap Map<String,String> parameter);


}
