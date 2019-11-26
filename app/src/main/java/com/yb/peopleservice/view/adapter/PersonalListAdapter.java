package com.yb.peopleservice.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.gcssloop.widget.PagerGridLayoutManager;
import com.gcssloop.widget.PagerGridSnapHelper;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.PersonalListBean;
import com.yb.peopleservice.view.weight.PageIndicatorView;

import java.util.ArrayList;
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
    private Context context;
    private DraggableController mDraggableController;
    List<String> contentLis = new ArrayList<>();
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PersonalListAdapter(List<PersonalListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(PersonalListBean.PAGE_TYPE, R.layout.personal_history_view);
        addItemType(PersonalListBean.TITLE_TYPE, R.layout.personal_wallet_view);
        addItemType(PersonalListBean.CONTENT_TYPE, R.layout.personal_order_view);
        mDraggableController = new DraggableController(this);

        for (int i = 0; i < 5; i++) {
            contentLis.add("");
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalListBean item) {
        mDraggableController.initView(helper);
        switch (helper.getItemViewType()) {
            case PersonalListBean.TITLE_TYPE:

                break;
            case PersonalListBean.PAGE_TYPE:
                RecyclerView recyclerView = helper.getView(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
                HomeContentAdapter contentAdapter = new HomeContentAdapter();
                recyclerView.setAdapter(contentAdapter);
                contentAdapter.setNewData(contentLis);
                contentAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ToastUtils.showLong(position+"");
                    }
                });
                break;
            case PersonalListBean.CONTENT_TYPE:

                break;
        }

    }


    public DraggableController getDraggableController() {
        return mDraggableController;
    }
}
