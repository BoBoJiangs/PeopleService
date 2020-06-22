package cn.sts.base.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 类描述:下载文件的url接口
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2018/12/3  11:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public interface DownloadService {
    /**
     * 下载文件 
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载
}
