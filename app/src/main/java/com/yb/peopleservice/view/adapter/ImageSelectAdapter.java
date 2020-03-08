package com.yb.peopleservice.view.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.GlideApp;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import java.io.File;


/**
 * 项目名称:Nmms
 * 类描述:网络评测详情内容适配器
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/5/21 17:12
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ImageSelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean isShowBtn;

    public ImageSelectAdapter(boolean isShowBtn) {
        super(R.layout.item_photo);
        this.isShowBtn = isShowBtn;
    }


    @Override
    protected void convert(BaseViewHolder helper, String file) {
        ImageView photoIV = helper.getView(R.id.photoIV);
        if (isShowBtn) {
            if (helper.getAdapterPosition() == getData().size() - 1) {
                helper.setVisible(R.id.deleteIV, false);
                helper.setImageResource(R.id.photoIV, R.mipmap.icon_camera);
            } else {
                helper.setVisible(R.id.deleteIV, true);
                ImageLoaderUtil.loadLocalImage(mContext, file, photoIV);
            }
        } else {
            helper.setVisible(R.id.deleteIV, false);
            ImageLoaderUtil.loadLocalImage(mContext, BaseRequestServer.getFileUrl(true) +
                    file, photoIV);
        }

        helper.addOnClickListener(R.id.deleteIV);
        helper.addOnClickListener(R.id.photoIV);
    }


}
