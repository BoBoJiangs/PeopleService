package com.yb.peopleservice.view.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.PersonalListBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.model.database.manager.UserInfoManager;
import com.yb.peopleservice.model.database.manager.UserManager;
import com.yb.peopleservice.model.presenter.chat.ChatPresenter;
import com.yb.peopleservice.model.presenter.user.personal.PersonalPresenter;
import com.yb.peopleservice.push.TagAliasOperatorHelper;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.address.AddressListActivity;
import com.yb.peopleservice.view.activity.im.ChatListActivity;
import com.yb.peopleservice.view.activity.personal.EditUserInfoActivity;
import com.yb.peopleservice.view.activity.personal.MyCouponListActivity;
import com.yb.peopleservice.view.activity.personal.MyFavoriteActivity;
import com.yb.peopleservice.view.activity.personal.SetActivity;
import com.yb.peopleservice.view.adapter.PersonalListAdapter;
import com.yb.peopleservice.view.base.LazyLoadListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;

import static com.yb.peopleservice.model.bean.PersonalListBean.CONTENT_TYPE;
import static com.yb.peopleservice.model.bean.PersonalListBean.SPAN_SIZE_ONE;
import static com.yb.peopleservice.push.TagAliasOperatorHelper.ACTION_SET;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/26 9:28
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonalFragment extends LazyLoadListFragment implements PersonalPresenter.IUserCallback {
    private PersonalListAdapter adapter;
    private HeaderViewHolder headerViewHolder;
    List<PersonalListBean> listData = new ArrayList<>();
    private PersonalPresenter presenter;
    private UserManager userManager;
    private UserInfoManager infoManager;


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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_personal;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserInfoBean infoBean) {
        presenter.getUserInfo();
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        presenter = new PersonalPresenter(getContext(), this);
        userManager = ManagerFactory.getInstance().getUserManager();
        infoManager = ManagerFactory.getInstance().getUserInfoManager();

        initHeaderView();
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_fa));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
//        listData.add(new PersonalListBean(CONTENT_TYPE, SPAN_SIZE_ONE));
        adapter.setNewData(listData);
//        DraggableController mDraggableController = adapter.getDraggableController();
//
//        OnItemDragListener listener = new OnItemDragListener() {
//            @Override
//            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
////                holder.setTextColor(R.id.tv, Color.WHITE);
//            }
//
//            @Override
//            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
//            }
//
//            @Override
//            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
//            }
//        };
//
//
//        ItemDragCallback mItemDragAndSwipeCallback = new ItemDragCallback(mDraggableController);
//
//        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
//        mItemTouchHelper.attachToRecyclerView(recyclerView);
//
//        mDraggableController.enableDragItem(mItemTouchHelper);
//        mDraggableController.setOnItemDragListener(listener);


    }


    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void onClickItem(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getContext(), MyCouponListActivity.class));
                break;
            case 1://地址管理
                startActivity(new Intent(getContext(), AddressListActivity.class));
                break;
            case 2://我的优惠券
                startActivity(new Intent(getContext(), MyFavoriteActivity.class));
                break;
            case 3:

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
        View footerView = View.inflate(getActivity(), R.layout.view_exit_button, null);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog appDialog = new AppDialog(getActivity());
                appDialog.title("是否确认退出？")
                        .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                presenter.logout();
                            }
                        });

                appDialog.negativeBtn(R.string.cancel, new AppDialog.OnClickListener() {
                    @Override
                    public void onClick(AppDialog appDialog) {
                        appDialog.dismiss();
                    }
                });
                appDialog.setCancelable(false);
                appDialog.show();
            }
        });
        adapter.addFooterView(footerView);
    }

    @Override
    public void getDataSuccess(UserInfoBean data) {
        if (data != null) {
            User user = userManager.getUser();
            user.setUserId(data.getId());
            userManager.update(user);
            headerViewHolder.setUserInfoData(data);
            infoManager.deleteAll();
            infoManager.save(data);
        }

    }





    @Override
    public void getDataFail() {

    }

    @Override
    public void fetchData() {
        UserInfoBean userInfo = userManager.getUser().getInfoBean();
        if (userInfo==null){
            presenter.getUserInfo();
        }else{
            getDataSuccess(userInfo);
        }

    }

    @OnClick({R.id.setIV, R.id.msgTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setIV:
                startActivity(new Intent(getContext(), SetActivity.class));
                break;
            case R.id.msgTV:
                startActivity(new Intent(getContext(), ChatListActivity.class));
                break;
        }
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
            memberTV.setText("等级:" + userInfo.getTotalPoints());
            ImageLoaderUtil.loadServerCircleImage(context, userInfo.getHeadImg(), photoIV);
        }

        @OnClick({R.id.payTV, R.id.todoTV, R.id.finishTV, R.id.evaluateTV, R.id.userInfoRL})
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
                case R.id.userInfoRL:
                    context.startActivity(new Intent(context, EditUserInfoActivity.class));
                    break;
            }
        }
    }
}
