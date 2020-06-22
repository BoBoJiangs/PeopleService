package com.yb.peopleservice.model.presenter.record;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.RecordBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.RecordManager;
import com.yb.peopleservice.model.eventbean.EventOrderBean;
import com.yb.peopleservice.model.presenter.shop.AssignPresenter;
import com.yb.peopleservice.model.presenter.uploadfile.UploadFilePresenter;
import com.yb.peopleservice.model.server.BaseRequestFunc;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.model.server.shop.ShopRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import cn.sts.base.callback.IViewCallback;
import cn.sts.base.model.listener.IRequestListener;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;
import io.reactivex.Observable;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/9 23:32
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class RecordPresenter extends AbstractPresenter<RecordPresenter.IRecordCallback> implements UploadFilePresenter.IViewUploadFile {

    private UploadFilePresenter filePresenter;
    private List<RecordBean> listRecord;
    private int position;
    public final static String RECORD_TAG = "RECORD_TAG";
    RecordManager recordManager;

    public RecordPresenter(Context context, RecordPresenter.IRecordCallback viewCallBack) {
        super(context, viewCallBack);
        filePresenter = new UploadFilePresenter(context, this);
        recordManager = ManagerFactory.getInstance().getRecordManager();
    }

    @Override
    public void unbind() {
        super.unbind();
    }

    //查询本地数据库文件
    public void queryFileData() {
        position = 0;
        listRecord = recordManager.queryAll();
        if (listRecord != null && !listRecord.isEmpty()) {
            if (NetworkUtils.isWifiConnected()) {
                uploadSoundFile();
            } else {
                AppDialog appDialog = new AppDialog(context);
                appDialog.title("您有待上传的音频文件,是否确定提交？")
                        .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                uploadSoundFile();
                            }
                        });

                appDialog.negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                    }
                });
                appDialog.setCancelable(false);
                appDialog.show();
            }
        }


    }

    /**
     * 上传音频文件
     */
    public void uploadSoundFile() {
        if (listRecord != null && !listRecord.isEmpty()) {
            if (position < listRecord.size()) {
                RecordBean recordBean = listRecord.get(position);
                LogUtils.eTag(RECORD_TAG, "正在上传第" + position + "个文件");
                filePresenter.upLoadSoundFile(new File(recordBean.getLocalUri()));
            } else {
                LogUtils.eTag(RECORD_TAG, "文件上传完成");
                //查询fileUrl 不为空的文件传给服务器
                List<RecordBean> beanList = recordManager.queryRecordData();
                if (beanList.isEmpty()) {
                    LogUtils.eTag(RECORD_TAG, "没有可上传的文件");
                    return;
                }
                uploadSound(beanList);
            }

        } else {
            LogUtils.eTag(RECORD_TAG, "没有音频文件的数据");
        }

    }

    /**
     * 将上传的录音URL回传给订单
     */
    public void uploadSound(List<RecordBean> recordBean) {
        BaseRequestFunc<ShopRequest> requestFunc = new BaseRequestFunc<ShopRequest>(context, new IRequestListener<Object>() {
            @Override
            public void onRequestSuccess(Object data) {
                try {
                    recordManager.deleteRecordData();
                    getViewCallBack().uploadSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailure(String error) {
                try {
                    getViewCallBack().uploadFail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtils.showLong(error);
            }

            @Override
            public void onRequestCancel() {

            }
        }) {
            @Override
            public Observable getObservable(ShopRequest iRequestServer) {
                return iRequestServer.uploadSound(recordBean);

            }

            @Override
            public Class<ShopRequest> getRequestInterfaceClass() {
                return ShopRequest.class;
            }
        };
        requestFunc.setShowProgress(true);
        BaseRequestServer.getInstance().request(requestFunc);
    }


    @Override
    public void uploadSuccess(List<String> files, boolean isPublic) {
        listRecord.get(position).setFileUri(files.get(0));
        LogUtils.eTag(RECORD_TAG, "文件路径:" +
                listRecord.get(position).getFileUri());
        //保存一下上传成功的数据
        ManagerFactory.getInstance().getRecordManager()
                .saveOrUpdate(listRecord.get(position));
        LogUtils.eTag(RECORD_TAG, "第" + position + "个文件上传成功");
        position++;
        uploadSoundFile();

    }

    @Override
    public void uploadFail() {
        //如果上传失败继续上传其他文件
        LogUtils.eTag(RECORD_TAG, "第" + position + "个文件上传失败");
        position++;
        uploadSoundFile();
    }


    public interface IRecordCallback extends IViewCallback {


        void uploadSuccess(Object data);

        void uploadFail();
    }
}
