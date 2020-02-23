package com.yb.peopleservice.view.fragment.user.details;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.order.CouponBean;
import com.yb.peopleservice.model.bean.user.service.GroupBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.ServicePresenter;
import com.yb.peopleservice.view.adapter.user.GroupAdapter;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;
import com.yb.peopleservice.view.weight.CustomPopup.GroupPopup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.view.widget.UtilityView;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 11:19
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceGroupFragment extends ServiceFragment implements ServicePresenter.IServiceCallback {
    private ServicePresenter servicePresenter;
    @BindView(R.id.groupLL)
    LinearLayout groupLL;
    @BindView(R.id.groupUV)
    UtilityView groupUV;
    @BindView(R.id.groupView)
    RecyclerView recyclerView;
    private GroupAdapter adapter;
    private List<GroupBean> data = new ArrayList<>();
    private GroupPopup popup;

    public static Fragment getInstanceFragment(ServiceListBean serviceInfo) {
        ServiceGroupFragment fragment = new ServiceGroupFragment();
        if (serviceInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ServiceListBean.class.getName(), serviceInfo);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        adapter = new GroupAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        servicePresenter = new ServicePresenter(getContext(), this);
        servicePresenter.buyGroup(serviceInfo.getId());
        groupUV.setTitleText(serviceInfo.getGroupSize() + "人正在拼单 可直接参与");
    }

    @Override
    public void groupSuccess(List<GroupBean> data) {

        if (data != null && !data.isEmpty()) {
            this.data = data;
            adapter.setNewData(data, false);
        } else {
            groupLL.setVisibility(View.GONE);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GroupBean bean) {
        popup.dismiss();

    }

    @OnClick({R.id.groupLL})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.groupLL:
                if (data != null && !data.isEmpty() && getActivity() != null) {
                    popup = new GroupPopup(getActivity(), data);
                    new XPopup.Builder(getContext())
                            .asCustom(popup)
                            .show();
                }
                break;

        }

    }
}
