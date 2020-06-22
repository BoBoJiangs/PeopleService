package cn.sts.base.download;

import java.io.File;

/**
 * 类描述:下载进度回调
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2018/12/3  11:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public interface DownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload(File file);
 
    void onFail(Throwable ex);

}
