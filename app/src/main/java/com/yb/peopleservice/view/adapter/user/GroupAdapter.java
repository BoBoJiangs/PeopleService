package com.yb.peopleservice.view.adapter.user;

import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 项目名称:PeopleService
 * 类描述: 团购列表适配器
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21 16:06
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class GroupAdapter extends BaseQuickAdapter<GroupBean, BaseViewHolder> {


    public GroupAdapter() {
        super(R.layout.adapter_group_item);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupBean item) {
        String content = "还差<font color=\"#FE6026\">" + (item.getTargetSize() - item.getSize())
                + "人" + "</font>拼成";
        TextView surplusTV = helper.getView(R.id.surplusTV);
        surplusTV.setText(Html.fromHtml(content));
        UserInfoBean infoBean = item.getCustomer();
        ImageView headImg = helper.getView(R.id.headImg);
        CountdownView countdownView = helper.getView(R.id.cv_countdownView);
        countdownView.setTag(helper);
        long endTime = TimeUtils.string2Millis(item.getEndtime());
        long surplusTime = endTime - System.currentTimeMillis();
        countdownView.start(surplusTime);
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                BaseViewHolder helper = (BaseViewHolder) cv.getTag();
                if (helper != null) {
                    remove(helper.getAdapterPosition());
                }
            }
        });
        if (infoBean != null) {
            ImageLoaderUtil.loadServerCircleImage(mContext, infoBean.getHeadImg(), headImg);
            helper.setText(R.id.nameTV, infoBean.getName());
        }
        helper.getView(R.id.spellTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(item);
            }
        });
    }

    /**
     * 处理团购数据
     *
     * @param data
     * @param isAll
     */
    public void setNewData(@Nullable List<GroupBean> data, boolean isAll) {
        List<GroupBean> groupBeanList = new ArrayList<>();
        for (GroupBean item : data) {
            long endTime = TimeUtils.string2Millis(item.getEndtime());
            long surplusTime = endTime - System.currentTimeMillis();
            if (surplusTime > 0) {
                groupBeanList.add(item);
                if (!isAll && groupBeanList.size() == 2) {
                    break;
                }
            }
        }
        super.setNewData(groupBeanList);
    }
}
