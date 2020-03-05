package com.yb.peopleservice.view.activity.im;

import android.view.View;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.helper.ManagerFactory;
import com.yb.peopleservice.view.base.BaseActivity;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import java.io.File;

import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.sts.base.presenter.AbstractPresenter;
import jiguang.chat.activity.fragment.ConversationListFragment;

public class ChatListActivity extends BaseToolbarActivity {

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.base_text_color).init();
    }

    @Override
    public void initToolView() {
        super.initToolView();
        titleTV.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected int contentViewResID() {
        return R.layout.activity_chat_list;
    }

    @Override
    protected void initData() {
        FragmentUtils.add(getSupportFragmentManager(), new ConversationListFragment(),
                R.id.frameLayout);

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }


    @Override
    public String getTitleName() {
        return "消息列表";
    }
}
