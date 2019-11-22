package com.yb.peopleservice.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.gcssloop.widget.PagerGridLayoutManager;
import com.gcssloop.widget.PagerGridSnapHelper;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.BannerListVO;
import com.yb.peopleservice.model.HomeListBean;
import com.yb.peopleservice.utils.GlideImageLoader;
import com.yb.peopleservice.view.adapter.HomeListAdapter;
import com.yb.peopleservice.view.adapter.HomePageAdapter;
import com.yb.peopleservice.view.weight.ItemDragCallback;
import com.yb.peopleservice.view.weight.PageIndicatorView;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

import static com.yb.peopleservice.model.HomeListBean.CONTENT_TYPE;
import static com.yb.peopleservice.model.HomeListBean.PAGE_TYPE;
import static com.yb.peopleservice.model.HomeListBean.SPAN_SIZE_FOUR;
import static com.yb.peopleservice.model.HomeListBean.SPAN_SIZE_ONE;
import static com.yb.peopleservice.model.HomeListBean.TITLE_TYPE;

/**
 * 类描述:首页
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/20  16:37
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomeFragment extends BaseListFragment {
    private HomeListAdapter adapter;
    private HeaderViewHolder headerViewHolder;
    List<HomeListBean> listData = new ArrayList<>();

    public static Fragment getInstanceFragment() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        adapter = new HomeListAdapter(listData, getContext());
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3);
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initData() {
        initHeaderView();
        listData.add(new HomeListBean(PAGE_TYPE, SPAN_SIZE_ONE));
        listData.add(new HomeListBean(TITLE_TYPE, SPAN_SIZE_ONE));
        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new HomeListBean(TITLE_TYPE, SPAN_SIZE_ONE));
        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        adapter.setSpanSizeLookup((gridLayoutManager, position) ->
                listData.get(position).getSpanSize());
        adapter.setNewData(listData);
        DraggableController mDraggableController = adapter.getDraggableController();

        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };


        ItemDragCallback mItemDragAndSwipeCallback = new ItemDragCallback(mDraggableController);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mDraggableController.enableDragItem(mItemTouchHelper);
        mDraggableController.setOnItemDragListener(listener);


    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    /**
     * 设置轮播图片集合
     *
     * @param bannerListVO
     */
    public void setBannerList(List<BannerListVO> bannerListVO) {
        //设置图片集合
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            images.add("https://static.daojia.com/changsha/website-m/baomu-pc/res/baomu-index/banner.png?v=201811071456");
        }

        //设置轮播时间
        headerViewHolder.banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        headerViewHolder.banner.update(images);
    }

    /**
     * 初始化头部轮播控件
     */
    private void initHeaderView() {
        //设置图片加载器
        View headerView = View.inflate(getActivity(), R.layout.e_banner, null);
        headerViewHolder = new HeaderViewHolder(headerView, getActivity());
        adapter.addHeaderView(headerView);
        setBannerList(new ArrayList<>());
    }

    static class HeaderViewHolder {
        private Context context;
        @BindView(R.id.banner)
        Banner banner;

        HeaderViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
            initBanner();
        }

//        private void initPageView() {
//            List<String> listData = new ArrayList<>();
//            for (int i = 1; i <= 15; i++) {
//                listData.add(i + "");
//            }
//            mLayoutManager = new PagerGridLayoutManager(2, 5, PagerGridLayoutManager
//                    .HORIZONTAL);
//            mLayoutManager.setPageListener(this);    // 设置页面变化监听器
//            mRecyclerView.setLayoutManager(mLayoutManager);
//
//            // 设置滚动辅助工具
//            PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
//            pageSnapHelper.attachToRecyclerView(mRecyclerView);
//            pageAdapter = new HomePageAdapter();
//            mRecyclerView.setAdapter(pageAdapter);
//            pageAdapter.setNewData(listData);
//        }

        public void initBanner() {
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setOnBannerListener(position -> {
//            String url;
//            if (TextUtils.isEmpty(bannerListVO.get(position).getDetails())) {
//                url = (bannerListVO.get(position).getLink());
//            } else {
//                url = (bannerListVO.get(position).getDetails());
//            }
//            if (!TextUtils.isEmpty(url)) {
//                startActivity(new Intent(getContext(), WebViewActivity.class)
//                        .putExtra(IntentKeyConstant.WEB_VIEW_URL, url)
//                        .putExtra(IntentKeyConstant.WEB_VIEW_TITLE,
//                                bannerListVO.get(position).getTitle()));
//            }

            });
        }

//        @Override
//        public void onPageSizeChanged(int pageSize) {
//            indicator.initIndicator(pageSize);
//        }
//
//        @Override
//        public void onPageSelect(int pageIndex) {
//            indicator.setSelectedPage(pageIndex);
//        }
    }
}
