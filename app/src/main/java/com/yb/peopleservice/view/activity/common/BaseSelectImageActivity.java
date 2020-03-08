package com.yb.peopleservice.view.activity.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.RequestCodeConstant;
import com.yb.peopleservice.model.presenter.WeChatPresenter;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.GlideImageLoader;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.adapter.ImageSelectAdapter;
import com.yb.peopleservice.view.base.BaseActivity;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;


/**
 * 类描述: 选择图片的基类
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/12  16:21
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseSelectImageActivity extends BaseOrderInfoActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.photoView)
    protected RecyclerView recyclerView;
    protected boolean isShowBtn;//是否显示拍照按钮


    protected BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter;


    @Override
    public void initView() {
        super.initView();
        initViewAndData();

    }

    protected boolean isShowBtn(){
        return true;
    }

    public void initViewAndData() {
        isShowBtn = isShowBtn();
        baseQuickAdapter = initAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(baseQuickAdapter);
        if (isShowBtn){
            List<String> files = new ArrayList<>();
            files.add("app");//加一个空数据显示添加按钮
            baseQuickAdapter.setNewData(files);
        }
        if (isSetOnItemClickListener()) {
            baseQuickAdapter.setOnItemChildClickListener(this::onItemChildClick);
        }
    }


    private void compressImage(List<String> photos) {
        Luban.with(this)
                .load(photos)
                .ignoreBy(100)
                .setTargetDir(AppUtils.getPathPic())
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(File file) {
                        dissmissProgressDialog();
                        if (baseQuickAdapter.getItemCount() > 0) {
                            baseQuickAdapter.addData(0, file.getAbsolutePath());
                        } else {
                            baseQuickAdapter.addData(file.getAbsolutePath());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        ToastUtils.showLong(e.getMessage());
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    public BaseQuickAdapter initAdapter() {
        return new ImageSelectAdapter(isShowBtn);
    }

    public boolean isSetOnItemClickListener() {
        return true;
    }

    public List<File> getFileImages() {
        List<File> fileList = new ArrayList<>();
        List<String> fileImages = getImages();
        for (String path : fileImages) {
            fileList.add(new File(path));
        }
        return fileList;
    }

    /**
     * 获取图片数据
     *
     * @return
     */
    public List<String> getImages() {
        if (baseQuickAdapter != null) {
            if (baseQuickAdapter.getData().size() > 1) {
                return baseQuickAdapter.getData().subList(0,
                        baseQuickAdapter.getData().size() - 1);
            } else {
                return new ArrayList<>();
            }

        }
        return new ArrayList<>();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.deleteIV:
                baseQuickAdapter.remove(position);
                break;
            case R.id.photoIV:
                if (isShowBtn){
                    if (baseQuickAdapter.getItemCount() - 1 == position) {
                        ArrayList<String> fileList = (ArrayList<String>) getImages();
                        ImagePicker.withMulti(new WeChatPresenter())
                                .setMaxCount(3)
                                .showCamera(true)//显示拍照
                                .mimeTypes(MimeType.ofImage())
                                .setLastImageList(fileList)
                                .setShieldList(fileList)
                                .setSelectMode(SelectMode.MODE_MULTI)
                                //调用剪裁
                                .pick(this, new OnImagePickCompleteListener() {
                                    @Override
                                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                                        if (!items.isEmpty()) {
                                            List<String> filePath = new ArrayList<>();
                                            for (ImageItem imageItem : items) {
                                                filePath.add(imageItem.path);
                                            }
                                            List<String> files = new ArrayList<>();
                                            files.add("app");//加一个空数据显示添加按钮
                                            baseQuickAdapter.setNewData(files);
                                            compressImage(filePath);
                                        }
                                    }
                                });
                    }
                }


                break;
        }
    }


}
