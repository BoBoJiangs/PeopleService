package com.yb.peopleservice.view.fragment;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.PersonalListBean;
import com.yb.peopleservice.view.adapter.PersonalListAdapter;
import com.yb.peopleservice.view.weight.ItemDragCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

import static com.yb.peopleservice.model.bean.PersonalListBean.CONTENT_TYPE;
import static com.yb.peopleservice.model.bean.PersonalListBean.SPAN_SIZE_ONE;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/26 9:28
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonalFragment extends BaseListFragment {
    private PersonalListAdapter adapter;
    private HeaderViewHolder headerViewHolder;
    List<PersonalListBean> listData = new ArrayList<>();

    public static Fragment getInstanceFragment() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        adapter = new PersonalListAdapter(listData, getContext());
        return adapter;
    }

//    @Override
//    protected RecyclerView.LayoutManager getLayoutManager() {
//        return new GridLayoutManager(getContext(), 3);
//    }

    @Override
    public int viewResID() {
        return R.layout.fragment_personal;
    }


    @Override
    protected void initData() {
        initHeaderView();
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_fa));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
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
     * 初始化头部轮播控件
     */
    private void initHeaderView() {
        //设置图片加载器
        View headerView = View.inflate(getActivity(), R.layout.personal_title_view, null);
        headerViewHolder = new HeaderViewHolder(headerView, getActivity());
        adapter.addHeaderView(headerView);
    }

    static class HeaderViewHolder {
        private Context context;

        HeaderViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
        }



    }
}
