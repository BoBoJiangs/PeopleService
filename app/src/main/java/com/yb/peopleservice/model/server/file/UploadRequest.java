package com.yb.peopleservice.model.server.file;


import com.yb.peopleservice.model.bean.FileDetailVO;

import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.RequestResult;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 类描述:上架商品
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public interface UploadRequest {

    /**
     * 上传私有文件
     */
    @POST("api/file/private/upload")
    Observable<RequestResult<List<String>>> upLoadPrivateFile(@Body MultipartBody multipartBody);


    /**
     * 上传公开数据
     */
    @POST("api/file/public/upload")
    Observable<RequestResult<List<String>>> upLoadPublicFile(@Body MultipartBody multipartBody);

}
