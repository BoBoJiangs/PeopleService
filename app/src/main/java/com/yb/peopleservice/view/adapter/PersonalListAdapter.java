package com.yb.peopleservice.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.PersonalListBean;

import java.util.List;


/**
 * 类描述: 首页列表适配器
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21  14:30
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonalListAdapter extends BaseMultiItemQuickAdapter<PersonalListBean, BaseViewHolder> {
    private String[] title = {"我的优惠券", "地址管理", "我的收藏"};
    private int[] imageID = {R.mipmap.icon_person2, R.mipmap.icon_person3,
            R.mipmap.icon_person4};
    private Context context;
    private DraggableController mDraggableController;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PersonalListAdapter(List<PersonalListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(PersonalListBean.CONTENT_TYPE, R.layout.personal_content_view);
        mDraggableController = new DraggableController(this);

    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalListBean item) {
        mDraggableController.initView(helper);
        if (helper.getAdapterPosition() - 1 < 3) {
            helper.setText(R.id.titleTV, title[helper.getAdapterPosition() - 1]);
            helper.setImageResource(R.id.imageView, imageID[helper.getAdapterPosition() - 1]);
        }

    }


    public DraggableController getDraggableController() {
        return mDraggableController;
    }
}
