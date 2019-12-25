package com.yb.peopleservice.view.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.presenter.user.PersonalPresenter;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.address.AddressListActivity;
import com.yb.peopleservice.view.adapter.PersonalListAdapter;
import com.yb.peopleservice.view.weight.ItemDragCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class PersonalFragment extends BaseListFragment implements PersonalPresenter.IUserCallback {
    private PersonalListAdapter adapter;
    private HeaderViewHolder headerViewHolder;
    List<PersonalListBean> listData = new ArrayList<>();
    private PersonalPresenter presenter;

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
        presenter.getUserInfo();
        initHeaderView();
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_fa));
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
        return presenter = new PersonalPresenter(getContext(), this);
    }

    @Override
    public void onClickItem(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 2://地址管理
                startActivity(new Intent(getContext(), AddressListActivity.class));
                break;
        }

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

    @Override
    public void getDataSuccess(UserInfoBean data) {

    }

    @Override
    public void getDataFail() {

    }

    static class HeaderViewHolder {
        @BindView(R.id.memberTV)
        TextView memberTV;
        @BindView(R.id.arrowTV)
        ImageView arrowTV;
        @BindView(R.id.userInfoRL)
        RelativeLayout userInfoRL;
        @BindView(R.id.payTV)
        TextView payTV;
        @BindView(R.id.todoTV)
        TextView todoTV;
        @BindView(R.id.finishTV)
        TextView finishTV;
        @BindView(R.id.evaluateTV)
        TextView evaluateTV;
        private Context context;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.photoIV)
        ImageView photoIV;


        HeaderViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
        }

        private void setUserInfoData(UserInfoBean userInfo) {
            nameTV.setText(userInfo.getNickname());
            memberTV.setVisibility(userInfo.getMember() == 0 ? View.GONE : View.VISIBLE);
            ImageLoaderUtil.loadServerCircleImage(context,userInfo.getHeadImg(),photoIV);
        }

        @OnClick({R.id.payTV, R.id.todoTV, R.id.finishTV, R.id.evaluateTV})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.payTV:
                    break;
                case R.id.todoTV:
                    break;
                case R.id.finishTV:
                    break;
                case R.id.evaluateTV:
                    break;
            }
        }
    }
}
