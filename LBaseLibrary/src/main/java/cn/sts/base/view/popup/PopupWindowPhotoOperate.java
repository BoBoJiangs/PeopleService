package cn.sts.base.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.R;
import cn.sts.base.R2;
import cn.sts.base.view.widget.UtilityView;


/**
 * 照片弹出操作-拍照/预览/删除
 * Created by weilin on 17/2/16.
 */

public class PopupWindowPhotoOperate extends BasePopupWindow {

    @BindView(R2.id.takePhotoUV)
    UtilityView takePhotoUV;
    @BindView(R2.id.previewUV)
    UtilityView previewUV;
    @BindView(R2.id.deleteUV)
    UtilityView deleteUV;
    @BindView(R2.id.cancelBtn)
    Button cancelBtn;

    private PhotoOptionListener photoOptionListener;
    /**
     * 是否可以操作，例如拍照，删除
     */
    private boolean isCanOperate = true;

    public PopupWindowPhotoOperate(Context context, boolean isCanOperate, PhotoOptionListener photoOptionListener) {
        super(context);
        this.photoOptionListener = photoOptionListener;
        this.isCanOperate = isCanOperate;
        initView();
    }

    public PopupWindowPhotoOperate(Context context, PhotoOptionListener photoOptionListener) {
        super(context);
        this.photoOptionListener = photoOptionListener;
        initView();
    }

    private void initView() {
        if (!isCanOperate) {
            takePhotoUV.setVisibility(View.GONE);
            deleteUV.setVisibility(View.GONE);
        }
    }

    @Override
    public int contentViewResId() {
        return R.layout.popup_photo_operate;
    }

    @OnClick({R2.id.takePhotoUV, R2.id.previewUV, R2.id.deleteUV, R2.id.cancelBtn})
    public void onViewClicked(View view) {
        dismiss();
        int id = view.getId();
        if (id == R.id.takePhotoUV) {
            photoOptionListener.takePhoto();
        } else if (id == R.id.previewUV) {
            photoOptionListener.previewPhoto();
        } else if (id == R.id.deleteUV) {
            photoOptionListener.deletePhoto();
        } else if (id == R.id.cancelBtn) {

        }

    }

    /**
     * 照片操作
     */
    public interface PhotoOptionListener {

        /**
         * 拍照
         */
        void takePhoto();

        /**
         * 预览
         */
        void previewPhoto();

        /**
         * 删除照片
         */
        void deletePhoto();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
