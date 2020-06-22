package com.yb.peopleservice.model.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.request.RequestListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.LocalPathConstant;
import com.yb.peopleservice.model.bean.VersionVO;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.IAppRequest;
import com.yb.peopleservice.model.server.user.classify.LoginRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sts.base.download.DownloadHelper;
import cn.sts.base.download.DownloadListener;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.view.widget.AppDialog;
import io.reactivex.Observable;

/**
 * 检查版本
 * Created by daichao on 17/9/5.
 * Modify by weilin on 17/9/18.
 */

public class VersionPresenter implements DownloadListener {


    private Context activity;

    /**
     * 下载地址
     */
    private String downLoadUrl;
    /**
     * 是否后台下载
     */
    private boolean isBackgroundDownload;
    private AppDialog downloadDialog;
    /**
     * 已下载的进度
     */
    private int downloadedProgress;

    private String downloadedStr;

    private ICheckVersion iCheckVersion;
    private boolean isMust = false; //是否是强制更新
    private DownloadHelper mDownloadHelper;
    private AppDialog appDialog;
    private String apkName;

    public VersionPresenter(Activity activity) {
        this(activity, null);
    }

    public VersionPresenter(Activity activity, ICheckVersion iCheckVersion) {
        this.activity = activity;
        this.iCheckVersion = iCheckVersion;
        mDownloadHelper = new DownloadHelper(BaseRequestServer.SERVER_URL, this);
    }

    /**
     * 检查版本
     */
    public void checkVersion() {

        final Map<String, String> map = new HashMap<>();
        //版本类型   (0:android,1:ios)
        map.put("type", "2");
        BaseRequestFunc<IAppRequest> requestFunc = new BaseRequestFunc<IAppRequest>(activity,
                new IRequestListener<List<VersionVO>>() {
            @Override
            public void onRequestSuccess(List<VersionVO> data) {
                if (data!=null&&!data.isEmpty()){
                    VersionVO versionVO = data.get(0);
                    if (!AppUtils.getAppVersionName().equals(versionVO.getVersionNumber())) {
                        downLoadUrl = versionVO.getLink();
                        apkName = versionVO.getId()+".apk";
                        if (appDialog == null) {
                            appDialog = new AppDialog(activity);
                            appDialog.getTitleTV().setGravity(Gravity.LEFT);
                            appDialog.getMessageTV().setGravity(Gravity.LEFT);
                            appDialog.title("发现新版本:V" + versionVO.getVersionNumber())
                                    .message(versionVO.getDetail())
                                    .positiveBtn("立即更新", appDialog12 -> {
                                        isBackgroundDownload = false;
                                        appDialog.dismiss();
                                        downloadNewVersion();
                                    });
                            appDialog.negativeBtn("稍后再说", new AppDialog.OnClickListener() {
                                @Override
                                public void onClick(AppDialog appDialog) {
                                    appDialog.dismiss();
                                }
                            });
                        }

                        appDialog.show();
                    } else {
                        if (iCheckVersion != null) {
                            iCheckVersion.checkUpdateSuccess(versionVO);
                        }
                    }
                }

            }

            @Override
            public void onRequestFailure(String error) {

            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(IAppRequest iRequestServer) {
                return iRequestServer.checkVersion(map);
            }
            @Override
            public Class<IAppRequest> getRequestInterfaceClass() {
                return IAppRequest.class;
            }
        };

        requestFunc.setShowProgress(false);
        new BaseRequestServer().request(requestFunc);

    }

    public void onDestroy() {
        if (appDialog != null) {
            appDialog.dismiss();
            appDialog = null;
            activity = null;
        }
    }

    /**
     * 下载新版本
     */
    private void downloadNewVersion() {
        //避免正在下载的任务 点击重复下载
        if (downloadedProgress == 0) {
            if (FileUtils.isFileExists(LocalPathConstant.getBasePath()+File.separator+apkName)){
                AppUtils.installApp(LocalPathConstant.getBasePath()+File.separator+apkName);
            }else{
                mDownloadHelper.downloadFile(downLoadUrl, LocalPathConstant.getBasePath(), apkName);
            }

        }else{
            ToastUtils.showLong("正在下载，请稍后...");
        }

    }


    /**
     * 下载中
     *
     * @param progress 进度[0-100]
     */
    private void downloadFileProgress(int progress) {
        downloadedProgress = progress;
        if (!isBackgroundDownload) {
            if (downloadDialog == null) {
                downloadedStr = "已下载:";
                downloadDialog = new AppDialog(activity)
                        .title("新版本下载中")
                        .message("已下载:0%");
                if (!isMust) {
                    downloadDialog.positiveBtn("后台下载", new AppDialog.OnClickListener() {
                        @Override
                        public void onClick(AppDialog appDialog) {
                            isBackgroundDownload = true;
                            downloadDialog.dismiss();
                        }
                    });
                }

                downloadDialog.setCancelable(false);
                downloadDialog.show();
            }
            downloadDialog.message(downloadedStr + progress + "%");
        }
    }

    /**
     * 下载成功，安装APP
     *
     * @param filePath 本地apk文件地址
     */
    private void downloadFileSuccess(File filePath) {
        if (downloadDialog != null) {
            downloadDialog.dismiss();
        }

        try {
            AppUtils.installApp(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载失败
     */
    private void downloadFileFailed() {
        if (downloadDialog != null) {
            downloadDialog.dismiss();
        }

        new AppDialog(activity)
                .title("下载失败了")
                .negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                    }
                })
                .positiveBtn("重试", new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                        isBackgroundDownload = false;
//                        downloadNewVersion();
                        mDownloadHelper.downloadFile(downLoadUrl, LocalPathConstant.getBasePath(), apkName);
                    }
                })
                .show();

    }

    @Override
    public void onStartDownload() {

    }

    @Override
    public void onProgress(int progress) {
        downloadFileProgress(progress);
    }

    @Override
    public void onFinishDownload(File file) {
        downloadedProgress = 0;
        downloadFileSuccess(file);
    }

    @Override
    public void onFail(Throwable ex) {
        downloadedProgress = 0;
        downloadFileFailed();
    }


    /**
     * 检查版本回调
     */
    public interface ICheckVersion {

        /**
         * 检查版本成功
         *
         * @param versionVO 版本信息
         */
        void checkUpdateSuccess(VersionVO versionVO);

        /**
         * 检查版本失败
         *
         * @param error 失败原因
         */
        void checkUpdateFailed(String error);
    }

}
