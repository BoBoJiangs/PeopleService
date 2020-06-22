package com.yb.peopleservice.view.activity.common;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.flyco.tablayout.SegmentTabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.constant.enums.UserType;
import com.yb.peopleservice.model.bean.shop.MessageBean;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.view.activity.services.order.PayActivity;
import com.yb.peopleservice.view.base.BaseViewPagerActivity;
import com.yb.peopleservice.view.fragment.comment.MessageListFragment;
import com.yb.peopleservice.view.weight.CustomPopup.PayBottomPopup;
import com.yb.peopleservice.view.weight.CustomPopup.PushMessagePopup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sts.base.presenter.AbstractPresenter;

import static com.yb.peopleservice.view.fragment.comment.MessageListFragment.RECEIVE_MSG;
import static com.yb.peopleservice.view.fragment.comment.MessageListFragment.SEND_MSG;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员选择入驻店铺
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MessageListActivity extends BaseViewPagerActivity {
    @BindView(R.id.commonTabLayout)
    SegmentTabLayout segmentTabLayout;
    @BindView(R.id.rightIV2)
    ImageView rightIV2;
    private String[] mTitles = {"接收消息", "发布消息"};
    private BasePopupView popupView;
    private MessageListFragment fragment;
    private User user;
    @Override
    public int contentViewResID() {
        return R.layout.activity_message_view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initToolView() {
        super.initToolView();

        if (user != null && user.getAccountType().contains(UserType.SHOP.getValue())) {
            rightIV2.setVisibility(View.VISIBLE);
            rightIV2.setImageResource(R.mipmap.msg_add);
            rightIV2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickRightListener();
                }
            });
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageBean bean) {
        popupView.dismiss();
        fragment.onPullRefresh();
    }

    @Override
    protected View getTabLayout() {
        return segmentTabLayout;
    }

    @Override
    protected String[] getTabTitles() {
        return mTitles;
    }

    @Override
    protected List<Fragment> getFragmentList() {
        user = ManagerFactory.getInstance().getUserManager().getUser();
        List<Fragment> fragmentList = new ArrayList<>();
        fragment = (MessageListFragment) MessageListFragment.getInstanceFragment(SEND_MSG);
        fragmentList.add(MessageListFragment.getInstanceFragment(RECEIVE_MSG));
        if (user != null && user.getAccountType().contains(UserType.SHOP.getValue())) {
            fragmentList.add(MessageListFragment.getInstanceFragment(SEND_MSG));
        }else{
            segmentTabLayout.setVisibility(View.GONE);
        }

        return fragmentList;
    }


    @Override
    public void clickRightListener() {
        popupView = new XPopup.Builder(this)
                .asCustom(new PushMessagePopup(this))
                .show();
    }

    @Override
    public String getTitleName() {
        return "消息";
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
