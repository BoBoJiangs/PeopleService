package com.yb.peopleservice.view.activity.main;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.presenter.login.LogoutPresenter;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.fragment.service.ServicePersonFragment;
import com.yb.peopleservice.view.fragment.shop.ShopFragment;
import com.yb.peopleservice.view.fragment.shop.order.ShopOrderTabFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.AppDialog;

/**
 * 类描述:服务人员首页
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/18  17:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceMainActivity extends BaseToolbarActivity implements OnTabSelectListener {

    private String[] mTitles = {"订单", "消息", "我的"};
    private int[] mIconUnselectIds = {R.mipmap.tab_home_unselect, R.mipmap.tab_class_unselect,R.mipmap.tab_map_unselect};
    private int[] mIconSelectIds = {R.mipmap.tab_home_select, R.mipmap.tab_class_select,R.mipmap.tab_map_select};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private LogoutPresenter logoutPresenter;
    private ServicePersonFragment servicePersonFragment;
    @Override
    public int contentViewResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolView() {
        super.initToolView();
        rightLL.setVisibility(View.VISIBLE);
        rightIV2.setVisibility(View.VISIBLE);
        rightIV2.setImageResource(R.mipmap.icon_logout);
    }

    @Override
    protected void initData() {
        logoutPresenter = new LogoutPresenter(this,null);
        commonTabLayout.setTabData(getTabEntityList(), this, R.id.frameLayout,
                getFragmentList());
        commonTabLayout.setOnTabSelectListener(this);
    }

    @OnClick({R.id.rightIV2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rightIV2:
                AppDialog appDialog = new AppDialog(this);
                appDialog.title("是否确认退出？")
                        .positiveBtn(R.string.sure, new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {
                                appDialog.dismiss();
                                logoutPresenter.logout();
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


                break;
        }
    }
    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return "首页";
    }

    protected ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntityList.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntityList;
    }

    private ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        servicePersonFragment = (ServicePersonFragment) ServicePersonFragment.getInstanceFragment();
        fragmentList.add(ShopOrderTabFragment.getInstanceFragment());
        fragmentList.add(ShopOrderTabFragment.getInstanceFragment());
        fragmentList.add(servicePersonFragment);

        return fragmentList;
    }

    @Override
    public void onTabSelect(int position) {
        leftIV.setVisibility(View.GONE);
        rightLL.setVisibility(View.VISIBLE);
        if (position == 0) {
            titleTV.setText("订单");
        } else if (position == 1){
            titleTV.setText("消息");
        }else{
            titleTV.setText("我的");
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        servicePersonFragment.onActivityResult(requestCode,resultCode,data);
//    }
}
