package com.yb.peopleservice.view.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
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
import com.yb.peopleservice.view.adapter.HomeListAdapter2;
import com.yb.peopleservice.view.adapter.HomePageAdapter;
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
public class HomeFragment2 extends BaseListFragment {
    private HomeListAdapter2 adapter;
    private HeaderViewHolder headerViewHolder;
    List<HomeListBean> listData = new ArrayList<>();

    public static Fragment getInstanceFragment() {
        HomeFragment2 fragment = new HomeFragment2();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        adapter = new HomeListAdapter2(listData, getContext());
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
//        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_FOUR));
//        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_FOUR));
//        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_FOUR));
        listData.add(new HomeListBean(TITLE_TYPE, SPAN_SIZE_ONE));
//        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_FOUR));
//        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_FOUR));
//        listData.add(new HomeListBean(CONTENT_TYPE, SPAN_SIZE_FOUR));
        adapter.setSpanSizeLookup((gridLayoutManager, position) ->
                listData.get(position).getSpanSize());
        DraggableController mDraggableController = adapter.getDraggableController();

        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                Log.d(TAG, "drag start");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }
        };
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
//                Log.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
//                Log.d(TAG, "View Swiped: " + pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
//                canvas.drawColor(ContextCompat.getColor(MultipleItemAndDragUseActivity.this, R.color.color_light_blue));
//                canvas.drawText("Just some text", 0, 40, paint);
            }
        };


        mDraggableController.setOnItemSwipeListener(onItemSwipeListener);

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mDraggableController);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mDraggableController.enableSwipeItem();
        mDraggableController.setOnItemSwipeListener(onItemSwipeListener);
        mDraggableController.enableDragItem(mItemTouchHelper);
        mDraggableController.setOnItemDragListener(listener);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showLong("点击了" + position);
            }
        });
        adapter.setNewData(listData);
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
//        adapter.addHeaderView(headerView);
        setBannerList(new ArrayList<>());
    }

    static class HeaderViewHolder implements PagerGridLayoutManager
            .PageListener {
        private Context context;
        @BindView(R.id.banner)
        Banner banner;
        @BindView(R.id.pageRecyclerView)
        RecyclerView mRecyclerView;
        @BindView(R.id.indicator)
        PageIndicatorView indicator;
        private PagerGridLayoutManager mLayoutManager;
        private HomePageAdapter pageAdapter;

        HeaderViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
            initBanner();
            initPageView();
        }

        private void initPageView() {
            List<String> listData = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                listData.add(i + "");
            }
            mLayoutManager = new PagerGridLayoutManager(2, 5, PagerGridLayoutManager
                    .HORIZONTAL);
            mLayoutManager.setPageListener(this);    // 设置页面变化监听器
            mRecyclerView.setLayoutManager(mLayoutManager);

            // 设置滚动辅助工具
            PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
            pageSnapHelper.attachToRecyclerView(mRecyclerView);
            pageAdapter = new HomePageAdapter();
            mRecyclerView.setAdapter(pageAdapter);
            pageAdapter.setNewData(listData);
        }

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

        @Override
        public void onPageSizeChanged(int pageSize) {
            indicator.initIndicator(pageSize);
        }

        @Override
        public void onPageSelect(int pageIndex) {
            indicator.setSelectedPage(pageIndex);
        }
    }
}
