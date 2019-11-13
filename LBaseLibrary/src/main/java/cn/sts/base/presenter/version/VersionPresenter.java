package cn.sts.base.presenter.version;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.model.server.request.app.AppManageRequestFunc;
import cn.sts.base.model.server.request.app.AppManageRequestServer;
import cn.sts.base.model.server.request.app.IAppManageURL;
import cn.sts.base.model.server.vo.VersionVO;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.AppManageUtil;
import cn.sts.base.util.Logs;
import cn.sts.base.util.StringUtils;
import cn.sts.base.view.widget.AppDialog;
import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 检查版本
 * created by weilin on 2017/9/1.
 */
public class VersionPresenter extends AbstractPresenter<VersionPresenter.ICheckVersion> {

    private static final String TAG = "VersionPresenter";

    /**
     * 下载成功
     */
    private static final int CODE_DOWNLOAD_SUCCESS = 100;
    /**
     * 下载中
     */
    private static final int CODE_DOWNLOADING = 101;
    /**
     * 下载失败
     */
    private static final int CODE_DOWNLOAD_FAILED = 102;

    private ICheckVersion iCheckVersion;
    private Context context;

    private AppDialog downloadingDialog;
    private boolean isShowProgress;

    private DownloadHandler downloadHandler;

    public VersionPresenter(Context context) {
        super(context);
        this.context = context;
    }

    public VersionPresenter(Context context, ICheckVersion iCheckVersion) {
        this(context);
        this.iCheckVersion = iCheckVersion;
        downloadHandler = new DownloadHandler(iCheckVersion);
    }


    /**
     * 初始化检查版本回调，并可显示下载及安装apk文件的提示界面
     *
     * @param apkPathFile apk本地下载地址
     */
    public void initCheckVersionCallback(File apkPathFile) {

        this.iCheckVersion = new ICheckVersion() {

            @Override
            public void getLatestVersionSuccess(final VersionVO versionVO) {
                if (versionVO == null || AppUtils.getAppVersionName().equals(versionVO.getVersionCode())) {
                    if (isShowProgress) {
                        ToastUtils.showLong("您的版本已经是最新的了");
                    }
                } else {
                    AppDialog appDialog = new AppDialog(context);
                    appDialog.title("发现新版本")
                            .message(versionVO.getRemark());

                    appDialog.positiveBtn("更新", new AppDialog.OnClickListener() {
                        @Override
                        public void onClick(AppDialog appDialog) {
                            appDialog.dismiss();
                            downloadNewVersion(versionVO.getUrl(), apkPathFile);
                        }
                    });
                    if ("Y".equalsIgnoreCase(versionVO.getIsMustUpdate())) {
                        appDialog.setCancelable(false);
                    } else {
                        appDialog.negativeBtn("取消", new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                            }
                        });
                    }

                    appDialog.show();
                }
            }

            @Override
            public void downloadFileProgress(int progress) {
                if (downloadingDialog == null || !downloadingDialog.isShowing()) {
                    downloadingDialog = new AppDialog(context);
                    downloadingDialog.show();
                }
                downloadingDialog.title("下载中")
                        .message("已下载 " + progress + " %");
            }

            @Override
            public void downloadFileSuccess(String filePath) {
                if (downloadingDialog != null && downloadingDialog.isShowing()) {
                    downloadingDialog.dismiss();
                }
                try {
                    installApk(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showLong("安装失败，文件可能已损坏");
                }
            }

            @Override
            public void downloadFileFailed(final String downloadUrl) {
                new AppDialog(context)
                        .message("下载失败~")
                        .negativeBtn("取消", new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                            }
                        })
                        .positiveBtn("重试", new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                downloadNewVersion(downloadUrl, apkPathFile);
                            }
                        })
                        .show();
            }
        };
        downloadHandler = new DownloadHandler(iCheckVersion);
    }

    private void installApk(String apkPath) {
        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //注意Android8.0必须打开未知应用安装授权
            if (Build.VERSION.SDK_INT >= 26) {
                //是否打开未知应用安装授权
                boolean isOpen = context.getPackageManager().canRequestPackageInstalls();
                if (!isOpen) {
                    ToastUtils.showLong("请打开安装应用的权限");
                    Uri packageURI = Uri.parse("package:" + context.getPackageName());
                    Intent intentInstall = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    context.startActivity(intentInstall);
                }
            }
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName(), file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        context.startActivity(intent);
    }

    /**
     * 检查版本
     *
     * @param isShowProgress 是否显示进度
     * @param versionType    0:安卓，1：iOS，2：硬件
     */
    public void checkVersion(boolean isShowProgress, int versionType) {
        if (StringUtils.isBlank(AppManageUtil.APP_CODE)) {
            Logs.e(TAG, "APP_CODE为空，请调用LBaseUtil设置");
            return;
        }
        this.isShowProgress = isShowProgress;
        final Map<String, Object> map = new HashMap<>(2);
        map.put("appName", AppManageUtil.APP_CODE);
        map.put("versionType", versionType);

        AppManageRequestFunc requestFunc = new AppManageRequestFunc(context, new IRequestListener<VersionVO>() {
            @Override
            public void onRequestSuccess(VersionVO data) {
                if (iCheckVersion != null) {
                    iCheckVersion.getLatestVersionSuccess(data);
                }
            }

            @Override
            public void onRequestFailure(String error) {
                if (iCheckVersion != null) {
                    iCheckVersion.getLatestVersionSuccess(null);
                }
            }

            @Override
            public void onRequestCancel() {

            }
        }) {

            @Override
            public Observable getObservable(IAppManageURL iRequestServer) {
                return iRequestServer.getAppLatestVersion(map);
            }
        };
        requestFunc.setShowProgress(isShowProgress);
        AppManageRequestServer.getInstance().request(requestFunc);
    }

    /**
     * 下载新版本
     */
    public void downloadNewVersion(final String downloadUrl, final File file) {
        if (StringUtils.isBlank(downloadUrl)) {
            downloadFailed(downloadUrl);
            return;
        }
        Request request = new Request.Builder().url(AppManageUtil.APP_MANAGE_URL + downloadUrl).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downloadFailed(downloadUrl);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        is = responseBody.byteStream();
                        long total = responseBody.contentLength();
                        fos = new FileOutputStream(file);
                        long sum = 0;

                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            // 下载中
                            Message message = downloadHandler.obtainMessage();
                            message.what = CODE_DOWNLOADING;
                            message.arg1 = progress;
                            downloadHandler.sendMessage(message);
                        }
                        fos.flush();
                        // 下载完成
                        Message message = new Message();
                        message.what = CODE_DOWNLOAD_SUCCESS;
                        message.obj = file.getPath();
                        downloadHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        downloadFailed(downloadUrl);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    downloadFailed(downloadUrl);
                }
            }

        });
    }

    private void downloadFailed(String downloadUrl) {
        Message message = new Message();
        message.what = CODE_DOWNLOAD_FAILED;
        message.obj = downloadUrl;
        downloadHandler.sendMessage(message);
    }

    private static class DownloadHandler extends Handler {

        private WeakReference<ICheckVersion> iCheckVersionWeakReference;

        private DownloadHandler(ICheckVersion iCheckVersion) {
            iCheckVersionWeakReference = new WeakReference<>(iCheckVersion);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (iCheckVersionWeakReference.get() != null) {
                switch (msg.what) {
                    case CODE_DOWNLOAD_SUCCESS:
                        iCheckVersionWeakReference.get().downloadFileSuccess(msg.obj.toString());
                        break;
                    case CODE_DOWNLOADING:
                        iCheckVersionWeakReference.get().downloadFileProgress(msg.arg1);
                        break;
                    case CODE_DOWNLOAD_FAILED:
                        iCheckVersionWeakReference.get().downloadFileFailed(msg.obj.toString());
                        break;
                    default:
                }
            }
        }
    }

    /**
     * 检查版本
     * Created by weilin on 17/3/31.
     */
    public interface ICheckVersion extends IViewCallback {

        /**
         * 获取最新的版本
         *
         * @param versionVO 版本信息
         */
        void getLatestVersionSuccess(VersionVO versionVO);

        /**
         * 下载进度
         *
         * @param progress 百分比
         */
        void downloadFileProgress(int progress);

        /**
         * 下载成功
         *
         * @param filePath 文件路径
         */
        void downloadFileSuccess(String filePath);

        /**
         * 下载失败
         *
         * @param downloadUrl 下载地址
         */
        void downloadFileFailed(String downloadUrl);
    }

}

