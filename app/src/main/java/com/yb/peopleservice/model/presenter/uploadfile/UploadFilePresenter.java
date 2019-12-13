package com.yb.peopleservice.model.presenter.uploadfile;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.FileDetailVO;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.file.UploadRequest;
import com.yb.peopleservice.model.server.file.UploadRequestFunc;

import java.io.File;
import java.util.List;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 类描述: 上传附件
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  14:00
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class UploadFilePresenter extends AbstractPresenter<UploadFilePresenter.IViewUploadFile> {
    private Context context;

    public UploadFilePresenter(Context context, IViewUploadFile viewCallBack) {
        super(context, viewCallBack);
    }

    @Override
    public void unbind() {
        super.unbind();
    }

    //上传文件
    public void uploadFile(MultipartBody.Builder builder, boolean isPublic) {

        UploadRequestFunc requestFunc = new UploadRequestFunc(context, new IRequestListener<List<FileDetailVO>>() {
            @Override
            public void onRequestSuccess(List<FileDetailVO> data) {
                try {
                    getViewCallBack().uploadSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                ToastUtils.showLong(error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(UploadRequest iRequestServer) {
                if (isPublic) {
                    return iRequestServer.upLoadPublicFile(builder.build());
                } else {
                    return iRequestServer.upLoadPrivateFile(builder.build());
                }

            }
        };
        requestFunc.setCancelableProgress(false);
        requestFunc.setFileUpload(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }


    /**
     * 压缩图片
     */
    public void launchImage(String path,boolean isPublic) {

        Luban.with(context)
                .load(path)                                   // 传人要压缩的图片列表
                .setTargetDir(AppConstant.FILE_PATH)                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        //删除原图保留压缩后的图片
//                                FileUtil.deleteFile(images.get(0).path);
                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        builder.setType(MultipartBody.FORM);
                        builder.addFormDataPart(
                                "file", "flower.jpg",
                                RequestBody.create(MediaType.parse("application/octet-stream"), file)
                        );
                        uploadFile(builder,isPublic);

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong("操作失败,请重试");
                    }
                }).launch();    //启动压缩
    }

    public MultipartBody.Builder getBuilder(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            builder.addFormDataPart(
                    "file", "flower.jpg",
                    RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }
        return builder;
    }

    public interface IViewUploadFile extends IViewCallback {


        void uploadSuccess(List<FileDetailVO> files);

        void uploadFail();
    }
}