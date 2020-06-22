package cn.sts.base.download;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 类描述:处理数据
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2018/12/3  11:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class DownloadInterceptor implements Interceptor {
 
    private DownloadListener downloadListener;

    DownloadInterceptor(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new DownloadResponseBody(response.body(), downloadListener))
                .build();
    }
}
