package com.yb.peopleservice.view.adapter;

import android.widget.ImageView;

import com.cb.ratingbar.CBRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.EvaluateBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EvaluateAdapter extends BaseQuickAdapter<EvaluateBean, BaseViewHolder> {

    public EvaluateAdapter() {
        super(R.layout.adapter_evaluate);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateBean item) {
        UserInfoBean infoBean = item.getCustomer();
        ImageView headImg = helper.getView(R.id.headImg);
        CBRatingBar ratingBar = helper.getView(R.id.ratingBar);
        NineGridView nineGridView = helper.getView(R.id.nineGrid);
        helper.setText(R.id.contentTV, item.getText());
        helper.setText(R.id.timeTV, item.getTimestamp());
        ratingBar.setStarProgress(item.getLevel() / 5f * 100);
        if (infoBean != null) {
            ImageLoaderUtil.loadServerCircleImage(mContext, infoBean.getHeadImg(), headImg);
            helper.setText(R.id.nameTV, infoBean.getName());
        }
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = item.getImgs();
        if (imageDetails != null) {
            for (String imageUrl : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(BaseRequestServer.getFileUrl(true) + imageUrl);
                info.setBigImageUrl(BaseRequestServer.getFileUrl(true) + imageUrl);
                imageInfo.add(info);
            }
        }
        nineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
    }
}
