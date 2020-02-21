package com.yb.peopleservice.view.fragment.user.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.GlideApp;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.ImageLoaderUtil;

import java.util.List;

import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseListFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务详情内容
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/27 11:24
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceContentFragment extends BaseListFragment {
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    ServiceListBean serviceInfo;

    public static Fragment getInstanceFragment(ServiceListBean serviceInfo) {
        ServiceContentFragment fragment = new ServiceContentFragment();
        if (serviceInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ServiceListBean.class.getName(), serviceInfo);
            fragment.setArguments(bundle);
        }

        return fragment;
    }


    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_image) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.imageView);
                ImageLoaderUtil.loadPiblicImage(mContext, item, imageView);
            }
        };
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            serviceInfo = getArguments().getParcelable(ServiceListBean.class.getName());
            if (serviceInfo != null) {
                List<String> images = serviceInfo.getContentImgs();
                adapter.setNewData(images);
                View view = inflater.inflate(R.layout.view_service_content, null);
                TextView textView = view.findViewById(R.id.textView);
                textView.setText(serviceInfo.getContentText());
                adapter.setFooterView(view);
            }

        }
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
