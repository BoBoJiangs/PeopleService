package com.yb.peopleservice.view.adapter.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.gcssloop.widget.PagerGridLayoutManager;
import com.gcssloop.widget.PagerGridSnapHelper;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.HomeListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.activity.services.ServiceDetailsActivity;
import com.yb.peopleservice.view.activity.services.ServiceListActivity;
import com.yb.peopleservice.view.adapter.HomeContentAdapter;
import com.yb.peopleservice.view.weight.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 类描述: 首页列表适配器
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/21  14:30
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeListBean, BaseViewHolder> {
    private Context context;
    private DraggableController mDraggableController;
    List<String> contentLis = new ArrayList<>();
    private HomePageAdapter pageAdapter;
    private HomeContentAdapter contentAdapter;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeListAdapter(List<HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(HomeListBean.PAGE_TYPE, R.layout.home_page_view);
        addItemType(HomeListBean.TITLE_TYPE, R.layout.e_adapter_home_title);
        addItemType(HomeListBean.CONTENT_TYPE, R.layout.e_adapter_group_img);
        mDraggableController = new DraggableController(this);

        for (int i = 0; i < 5; i++) {
            contentLis.add("");
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {
        mDraggableController.initView(helper);
//        mDraggableController.setToggleViewId(R.id.titleLL);
        switch (helper.getItemViewType()) {
            case HomeListBean.PAGE_TYPE:
                RecyclerView mRecyclerView = helper.getView(R.id.pageRecyclerView);
                PageIndicatorView indicator = helper.getView(R.id.indicator);
                pageAdapter = (HomePageAdapter) mRecyclerView.getAdapter();
                if (pageAdapter == null) {
                    indicator.initIndicator(2);
                    PagerGridLayoutManager mLayoutManager = new PagerGridLayoutManager(2, 5, PagerGridLayoutManager
                            .HORIZONTAL);
                    mLayoutManager.setPageListener(new PagerGridLayoutManager.PageListener() {
                        @Override
                        public void onPageSizeChanged(int pageSize) {

                        }

                        @Override
                        public void onPageSelect(int pageIndex) {
                            indicator.setSelectedPage(pageIndex);
                        }
                    });    // 设置页面变化监听器
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    // 设置滚动辅助工具
                    PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
                    pageSnapHelper.attachToRecyclerView(mRecyclerView);
                    HomePageAdapter pageAdapter = new HomePageAdapter();
                    mRecyclerView.setAdapter(pageAdapter);
                    pageAdapter.setNewData(item.getClassList());
                } else {
                    pageAdapter.setNewData(item.getClassList());
                }

                break;
            case HomeListBean.TITLE_TYPE:
                helper.setText(R.id.titleTV,item.getName());

                RecyclerView recyclerView = helper.getView(R.id.recyclerView);
                contentAdapter = (HomeContentAdapter) recyclerView.getAdapter();
                helper.getView(R.id.titleLL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ServiceListActivity.class)
                                .putExtra("type", ServiceListPresenter.SERVICE_TYPE)
                                .putExtra(ClassifyListBean.class.getName(),item));
                    }
                });
                if (contentAdapter == null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    HomeContentAdapter contentAdapter = new HomeContentAdapter();
                    recyclerView.setAdapter(contentAdapter);
                    contentAdapter.setNewData(item.getCommodities());
                    contentAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ServiceListBean serviceListBean = contentAdapter.getItem(position);
                            context.startActivity(new Intent(context, ServiceDetailsActivity.class)
                                    .putExtra(ServiceListBean.class.getName(),serviceListBean));
                        }
                    });
                } else {
                    contentAdapter.setNewData(item.getCommodities());
                }

                break;
            case HomeListBean.CONTENT_TYPE:
                helper.getView(R.id.imageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ServiceListActivity.class)
                                .putExtra("type", ServiceListPresenter.GRUOP_TYPE));
                    }
                });
                break;
        }

    }


    public DraggableController getDraggableController() {
        return mDraggableController;
    }
}
