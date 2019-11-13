package cn.sts.base.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sts.base.R;
import cn.sts.base.util.Logs;
import cn.sts.base.view.popup.PopupWindowPhotoOperate;

/**
 * 照片展示控件
 * Created by weilin on 17/3/30.
 */

public class TakePhotoView extends LinearLayout {

    private static final String TAG = "TakePhotoView";

    private Context context;

    private PhotoAdapter photoAdapter;
    /**
     * 拍摄了的照片列表
     */
    private List<Object> fileList = new ArrayList<>();
    /**
     * 照片宽高
     */
    private int photoWH;

    /**
     * 多少列
     */
    private int column;

    /**
     * 图片的间距
     */
    private int imageMargin;

    /**
     * 是否可以操作，例如删除拍照
     */
    private boolean isCanOperate = true;

    private boolean isRetakePhoto;
    private int retakePhotoIndex;

    private RecyclerView recyclerView;
    private TakePhotoListener takePhotoListener;
    private DeletePhotoListener deletePhotoListener;

    public TakePhotoView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TakePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TakePhotoView);
        try {
            isCanOperate = typedArray.getBoolean(R.styleable.TakePhotoView_operate, true);
            column = typedArray.getInt(R.styleable.TakePhotoView_column, 3);
            imageMargin = (int) typedArray.getDimension(R.styleable.TakePhotoView_image_margin, 10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
        initView();
    }

    public void setTakePhotoListener(TakePhotoListener takePhotoListener) {
        this.takePhotoListener = takePhotoListener;
    }

    public void setDeletePhotoListener(DeletePhotoListener deletePhotoListener) {
        this.deletePhotoListener = deletePhotoListener;
    }

    /**
     * 获取图片
     */
    public List getFileList() {
        List<Object> tempList = new ArrayList<>();
        tempList.addAll(fileList);
        if (isCanOperate) {
            if (fileList.size() > 0) {
                tempList.remove(fileList.size() - 1);
            }
        }
        Logs.e(TAG, "照片数量=" + tempList.size());
        return tempList;
    }

    /**
     * 设置图片
     */
    public void setFileList(final List fileList) {
        Logs.e(TAG, "setAttachmentList" + this.fileList.size());
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                TakePhotoView.this.fileList.clear();
                TakePhotoView.this.fileList.addAll(0, fileList);
                refreshPhoto();
            }
        }, 1000);
    }

    /**
     * 替换图片
     *
     * @param photoIndex 序号
     * @param file       地址
     */
    public void setFile(int photoIndex, Object file) {
        if (photoIndex < fileList.size()) {
            fileList.set(photoIndex, file);
            photoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加图片到最后或者重拍替换
     *
     * @param file 地址
     */
    public void addOrSetFile(Object file) {
        if (isCanOperate) {
            if (isRetakePhoto && retakePhotoIndex < fileList.size()) {
                fileList.set(retakePhotoIndex, file);
                photoAdapter.notifyDataSetChanged();
            } else {
                fileList.add(fileList.size() - 1, file);
            }
        } else {
            fileList.add(file);
        }
        refreshPhoto();
    }


    /**
     * 是否可以操作照片，例如：拍照、删除
     */
    public void setCanOperate(boolean isCanOperate) {
        this.isCanOperate = isCanOperate;
    }


    /**
     * 刷新照片
     */
    public void refreshPhoto() {
        try {
            int row = fileList.size() / column;
            if (fileList.size() % column > 0) {
                row += 1;
            }

            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.height = photoWH * row + imageMargin * (row - 1);

            recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, layoutParams.height, 1.0f));
            recyclerView.setLayoutManager(new GridLayoutManager(context, column));
            photoAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化拍照控件
     */
    private void initView() {
        this.fileList.clear();
        if (isCanOperate) {
            this.fileList.add(new File(""));//默认的最后一个拍摄的空照片
        }

        photoAdapter = new PhotoAdapter();
        recyclerView = new RecyclerView(context);

        //在界面显示完成后才计算
        post(new Runnable() {
            @Override
            public void run() {

                photoWH = (getWidth() - imageMargin * (column - 1)) / column;
                recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, photoWH, 1.0f));
                recyclerView.setLayoutManager(new GridLayoutManager(context, column));
                recyclerView.setAdapter(photoAdapter);
                //设置项之间的间距，三个参数，列、间距、间距是否包括边缘
                recyclerView.addItemDecoration(new RecyclerViewGridItemDecoration(column, imageMargin, false));
                recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {

                        isRetakePhoto = false;
                        if (isCanOperate && position == fileList.size() - 1) {//直接拍照
                            if (takePhotoListener != null) {
                                takePhotoListener.takePhoto();
                            }
                        } else {//对照片的操作
                            if (isCanOperate) {
                                new PopupWindowPhotoOperate(context, isCanOperate, new PopupWindowPhotoOperate.PhotoOptionListener() {

                                    @Override
                                    public void takePhoto() {
                                        if (takePhotoListener != null) {
                                            isRetakePhoto = true;
                                            retakePhotoIndex = position;
                                            takePhotoListener.retakePhoto(position);
                                        }
                                    }

                                    @Override
                                    public void previewPhoto() {
                                        if (takePhotoListener != null) {
                                            takePhotoListener.previewPhoto(fileList.get(position), position);
                                        }
                                    }

                                    @Override
                                    public void deletePhoto() {
                                        fileList.remove(position);
                                        refreshPhoto();
                                        if (deletePhotoListener != null) {
                                            deletePhotoListener.deletePhoto(position);
                                        }
                                    }
                                }).show(TakePhotoView.this);

                            } else {
                                if (takePhotoListener != null) {
                                    takePhotoListener.previewPhoto(fileList.get(position), position);
                                }
                            }
                        }
                    }
                });
                TakePhotoView.this.addView(recyclerView);
            }
        });


    }

    /**
     * 点击照相
     */
    public interface TakePhotoListener {

        /**
         * 重新拍照
         *
         * @param photoIndex 照片序号
         */
        void retakePhoto(int photoIndex);

        /**
         * 拍照
         */
        void takePhoto();

        /**
         * 预览
         *
         * @param file       图片文件或者路径
         * @param photoIndex 照片序号
         */
        void previewPhoto(Object file, int photoIndex);

    }

    /**
     * 删除照片
     */
    public interface DeletePhotoListener {

        /**
         * 删除照片
         *
         * @param photoIndex 照片序号
         */
        void deletePhoto(int photoIndex);

    }

    /**
     * 照片显示适配器
     */
    private class PhotoAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {


        PhotoAdapter() {
            super(R.layout.adapter_photo, fileList);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object file) {
            ImageView imageView = helper.getView(R.id.imageView);
            LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
            layoutParams.width = photoWH;
            layoutParams.height = photoWH;
            if (isCanOperate && helper.getAdapterPosition() == fileList.size() - 1) {
                imageView.setImageResource(R.drawable.base_icon_add);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                GlideApp.with(mContext)
//                        .load(file)
//                        .placeholder(R.drawable.base_icon_default)
//                        .into(imageView);
                if (file instanceof File) {
                    Picasso.get()
                            .load((File) file)
                            .placeholder(R.drawable.base_icon_default)
                            .into(imageView);
                } else {
                    Picasso.get()
                            .load(file.toString())
                            .placeholder(R.drawable.base_icon_default)
                            .into(imageView);

                }
            }

        }
    }


}
