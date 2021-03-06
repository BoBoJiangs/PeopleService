package com.yb.peopleservice.view.activity.services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.model.bean.shop.MyShop;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.FavoriteBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.CollectPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.model.presenter.user.service.ShopDetailsPresenter;
import com.yb.peopleservice.model.server.BaseRequestServer;
import com.yb.peopleservice.utils.AppUtils;
import com.yb.peopleservice.utils.ImageLoaderUtil;
import com.yb.peopleservice.view.activity.common.ShopDetailsActivity;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;
import com.yb.peopleservice.view.fragment.user.favorite.FavoriteServiceFragment;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.model.UserInfo;
import cn.sts.base.app.AppManager;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.view.widget.UtilityView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;
import jiguang.chat.utils.ToastUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺服务列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/19 16:23
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopListActivity extends BaseListActivity implements
        ShopDetailsPresenter.IShopCallback, CollectPresenter.ICollectCallback {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.collectTV)
    TextView collectTV;
    @BindView(R.id.searchUV)
    UtilityView searchUV;
    EditText searchEt;
    @BindView(R.id.shopBgIV)
    ImageView shopBgIV;
    private ServiceListPresenter presenter;
    private ServiceListAdapter adapter;
    private ServiceListBean bean;
    private ShopDetailsPresenter detailsPresenter;
    private CollectPresenter collectPresenter;
    private MyShop myShop;
    private ShopInfo shopInfo;
    private String shopId;
    private ServiceInfo serviceInfo;

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ServiceListAdapter();
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_shop_list;
    }

    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarView(R.id.top_view)
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

    @Override
    public void initToolView() {
        super.initToolView();
        if (shopInfo != null) {
            titleTV.setText(shopInfo.getName());
        }

    }

    @Override
    protected void initData() {
        KeyboardUtils.hideSoftInput(this);
        collectPresenter = new CollectPresenter(this, this);
        serviceInfo = getIntent().getParcelableExtra(ServiceInfo.class.getName());
        if (serviceInfo != null) {
            //从地图跳转过来 用于指定服务人员下单
            shopId = serviceInfo.getShopId();
        }
        bean = getIntent().getParcelableExtra(ServiceListBean.class.getName());
        if (bean != null) {
            shopId = bean.getShopId();
        }
        shopInfo = (ShopInfo) getIntent().getParcelableExtra(ShopInfo.class.getName());
        if (shopInfo != null) {
            shopId = shopInfo.getId();
            getDataSuccess(shopInfo);
        }
        setOnRefreshListener();
        setLoadMoreListener();
        collectTV.setVisibility(View.INVISIBLE);
        searchEt = searchUV.getInputEditText();
        Disposable disposable = RxTextView.textChanges(searchEt)
                //限流时间500ms
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        String string = charSequence.toString();
                        presenter.setKeywords(string);
                        presenter.refreshList(false);
                    }
                });
    }

    @Override
    public void onPullRefresh() {
        super.onPullRefresh();
        presenter.refreshList(false);
    }

    @Override
    public void onLoadMoreRequest() {
        super.onLoadMoreRequest();
        presenter.loadMoreList();
    }

    @Override
    public void onClickItem(BaseQuickAdapter a, View view, int position) {
        ServiceListBean serviceListBean = adapter.getItem(position);
        if (serviceInfo != null && serviceListBean != null) {
            serviceListBean.setServiceStaffId(serviceInfo.getId());
        }
        startActivity(new Intent(this, ServiceDetailsActivity.class)
                .putExtra(ServiceListBean.class.getName(), serviceListBean));
    }

    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {
        ServiceListUIPresenter<ShopInfo> queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout, this);

        presenter = new ServiceListPresenter(shopId, this, queryListUI, ServiceListPresenter.SHOP_TYPE);
//        presenter.refreshList(true);

    }

    @Override
    protected AbstractPresenter createPresenter() {

        detailsPresenter = new ShopDetailsPresenter(this, this);
        detailsPresenter.getShopDetails(shopId);
        initQueryListUI();
        return presenter;
    }

    @Override
    public void collectSuccess(FavoriteBean favoriteBean) {
        collectTV.setText("已收藏");
    }

    @Override
    public void cancelSuccess() {
        collectTV.setText("收藏");
    }

    @Override
    public void isCollect(FavoriteBean data) {
        collectTV.setVisibility(View.VISIBLE);
        if (data != null) {
            collectTV.setText("已收藏");
        } else {
            collectTV.setText("收藏");
        }
    }

    @Override
    public void getDataSuccess(ShopInfo data) {
        if (data != null) {
            shopInfo = data;
            myShop = new MyShop();
            myShop.setShop(data);
            if (titleTV != null) {
                titleTV.setText(shopInfo.getName());
            }
            nameTV.setText(data.getName());
            ImageLoaderUtil.loadServerCircleImage(this, data.getHeadImg(), imageView);
            collectPresenter.getFavorite(data.getId());
            if (!StringUtils.isEmpty(data.getBackgroundImg())) {
                Glide.with(this).load(BaseRequestServer.getFileUrl(true) +
                        data.getBackgroundImg())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(15, 3)))
                        .into(shopBgIV);
            }
        }

    }

    @Override
    public void getDataFail() {

    }


    @OnClick({R.id.collectTV, R.id.shopLL, R.id.leftIV2, R.id.msgIV2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftIV2:
                finish();
                break;
            case R.id.collectTV:
                if (collectTV.getText().equals("收藏")) {
                    collectPresenter.addFavorite(shopInfo.getId(), FavoriteServiceFragment.SHOP_TYPE);
                } else {
                    collectPresenter.addFavorite(shopInfo.getId(), FavoriteServiceFragment.CANCEL_TYPE);

                }

                break;
            case R.id.shopLL:
                myShop.setType(MyShop.USER_DETAILS);
                startActivity(new Intent(this, ShopDetailsActivity.class)
                        .putExtra(ShopInfo.class.getName(), myShop.getShop()));
                break;
            case R.id.msgIV2:
                if (shopInfo != null) {
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra(JGApplication.TARGET_ID, AppUtils.formatID(shopInfo.getId()));
                    intent.putExtra(JGApplication.TARGET_APP_KEY, AppConstant.JPUSH_KEY);
                    intent.putExtra(JGApplication.DRAFT, shopInfo.getName());
                    startActivity(intent);
                } else {
                    ToastUtils.showLong("未获取到店铺信息");
                }

                break;

        }

    }
}
