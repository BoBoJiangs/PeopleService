package com.yb.peopleservice.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.gcssloop.widget.PagerGridLayoutManager;
import com.gcssloop.widget.PagerGridSnapHelper;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.HomeListBean;
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
public class HomeListAdapter2 extends BaseMultiItemQuickAdapter<HomeListBean, BaseViewHolder> {
    private Context context;
    private DraggableController mDraggableController;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeListAdapter2(List<HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(HomeListBean.PAGE_TYPE, R.layout.home_page_view);
        addItemType(HomeListBean.TITLE_TYPE, R.layout.e_adapter_home_title);
        addItemType(HomeListBean.CONTENT_TYPE, R.layout.e_adapter_home_shop);
        mDraggableController = new DraggableController(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {
        mDraggableController.initView(helper);
        switch (helper.getItemViewType()) {
            case HomeListBean.PAGE_TYPE:
                List<String> listData = new ArrayList<>();
                for (int i = 1; i <= 15; i++) {
                    listData.add(i + "");
                }
                PageIndicatorView indicator = helper.getView(R.id.indicator);
                RecyclerView mRecyclerView = helper.getView(R.id.pageRecyclerView);
                PagerGridLayoutManager mLayoutManager = new PagerGridLayoutManager(2, 5, PagerGridLayoutManager
                        .HORIZONTAL);
//                mLayoutManager.setPageListener(this);    // 设置页面变化监听器
                mRecyclerView.setLayoutManager(mLayoutManager);

                // 设置滚动辅助工具
                PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
                pageSnapHelper.attachToRecyclerView(mRecyclerView);
                HomePageAdapter pageAdapter = new HomePageAdapter();
                mRecyclerView.setAdapter(pageAdapter);
                pageAdapter.setNewData(listData);
                break;
            case HomeListBean.TITLE_TYPE:

                break;
            case HomeListBean.CONTENT_TYPE:
                TextView textView = helper.getView(R.id.priceTV2);
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                break;
        }

    }
    public DraggableController getDraggableController() {
        return mDraggableController;
    }
}
