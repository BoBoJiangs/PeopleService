package com.yb.peopleservice.view.fragment.user.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.CollectPresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.GlideImageLoader;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.fragment.BaseFragment;

/**
 * 项目名称:PeopleService
 * 类描述: 服务内容
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/2 16:56
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceFragment extends BaseFragment implements CollectPresenter.ICollectCallback {
    @BindView(R.id.banner)
    Banner banner;


    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.collectIV)
    ImageView collectIV;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.soldTV)
    TextView soldTV;
    ServiceListBean serviceInfo;
    private CollectPresenter collectPresenter;
    private FavoriteBean favoriteBean;

    public static Fragment getInstanceFragment(ServiceListBean serviceInfo) {
        ServiceFragment fragment = new ServiceFragment();
        if (serviceInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ServiceListBean.class.getName(), serviceInfo);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int viewResID() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            serviceInfo = getArguments().getParcelable(ServiceListBean.class.getName());
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerAnimation(Transformer.Accordion);
            //设置轮播时间
            banner.setDelayTime(3000);
            List<String> bannerImags = new ArrayList<>();
            List<String> imags = serviceInfo.getMainImg();
            for (String imag : imags) {
                imag = BaseRequestServer.getFileUrl(true) + imag;
                bannerImags.add(imag);
            }
            //banner设置方法全部调用完毕时最后调用
            banner.update(bannerImags);
            nameTV.setText(serviceInfo.getName());
            String priceUnit = serviceInfo.getPriceUnit();
            if (TextUtils.isEmpty(priceUnit)) {
                priceUnit = "元";
            } else {
                priceUnit = "元/" + priceUnit;
            }
            priceTV.setText(serviceInfo.getPrice() + priceUnit);
            soldTV.setText("已售：" + serviceInfo.getTotalSold());
        }
        collectPresenter = new CollectPresenter(getContext(), this);
        collectPresenter.getFavorite(serviceInfo.getId());
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return collectPresenter;
    }

    @OnClick(R.id.collectIV)
    public void onViewClicked() {
        if (favoriteBean == null) {
            collectPresenter.addFavorite(serviceInfo.getId(), FavoriteServiceFragment.SERVICE_TYPE);
        } else {
            collectPresenter.addFavorite(favoriteBean.getId(), FavoriteServiceFragment.CANCEL_TYPE);

        }
    }

    @Override
    public void collectSuccess(FavoriteBean favoriteBean) {
        if (favoriteBean != null) {
            this.favoriteBean = favoriteBean;
            collectIV.setBackgroundResource(R.mipmap.icon_favorite1);
        }
    }

    @Override
    public void cancelSuccess() {
        collectIV.setBackgroundResource(R.mipmap.icon_favorite);
        favoriteBean = null;
    }

    @Override
    public void isCollect(FavoriteBean data) {
        collectIV.setVisibility(View.VISIBLE);
        favoriteBean = data;
        if (data == null) {
            collectIV.setBackgroundResource(R.mipmap.icon_favorite);
        } else {
            collectIV.setBackgroundResource(R.mipmap.icon_favorite1);
        }
    }
}
