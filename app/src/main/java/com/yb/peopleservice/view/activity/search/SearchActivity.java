package com.yb.peopleservice.view.activity.search;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.service.SearchPresenter;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.activity.services.ServiceDetailsActivity;
import com.yb.peopleservice.view.adapter.user.classify.ServiceListAdapter;
import com.yb.peopleservice.view.base.BaseListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.model.entity.TabEntity;
import cn.sts.base.presenter.AbstractPresenter;
import io.reactivex.disposables.Disposable;

public class SearchActivity extends BaseListActivity implements OnTabSelectListener {
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.deleteIV)
    ImageView deleteIV;
    private ServiceListAdapter adapter;
    private SearchPresenter presenter;
    private String[] mTitles = {"默认", "价格", "销量"};
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;

    @Override
    public void initToolView() {
        super.initToolView();
    }

    @Override
    public int contentViewResID() {
        return R.layout.activity_community_search;
    }


    @Override
    protected void initData() {
        Disposable disposable = RxTextView.textChangeEvents(editText)
                .subscribe(textViewTextChangeEvent -> {
                    String string = textViewTextChangeEvent.getText().toString();
                    if (!TextUtils.isEmpty(string)) {
                        deleteIV.setVisibility(View.VISIBLE);
                    } else {
                        deleteIV.setVisibility(View.GONE);
                    }
                });
        initTabLayout();
        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {//如果是搜索按钮
                    presenter.setKeyWords(editText.getText().toString());
                    presenter.refreshList(true);
                }
                return false;
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

    private void initQueryListUI() {
        ServiceListUIPresenter<ShopInfo> queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout, this);
        presenter = new SearchPresenter(this, queryListUI);


    }

    @Override
    public void onClickItem(BaseQuickAdapter a, View view, int position) {
        ServiceListBean serviceListBean = adapter.getItem(position);
        startActivity(new Intent(this, ServiceDetailsActivity.class)
                .putExtra(ServiceListBean.class.getName(), serviceListBean));
    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity> titleList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            TabEntity tabEntity;
            if (i == 1) {
                tabEntity = new TabEntity(mTitles[i], R.mipmap.icon_price_jia, R.mipmap.icon_price_wxz);
            } else {
                tabEntity = new TabEntity(mTitles[i]);
            }
            titleList.add(tabEntity);
        }
        commonTabLayout.setIconGravity(Gravity.RIGHT);
        commonTabLayout.setTabData(titleList);
        commonTabLayout.setOnTabSelectListener(this);
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter = new ServiceListAdapter();
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    @Override
    public String getTitleName() {
        return "搜索";
    }


    @OnClick(R.id.deleteIV)
    public void onViewClicked() {
        editText.setText("");
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert mInputMethodManager != null;
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void onTabSelect(int position) {
        switch (position) {
            case 0:
                presenter.setOrder(ServiceListPresenter.DEFAULT);
                break;
            case 1:
                presenter.setOrder(ServiceListPresenter.PRICE_LESS);
                break;
            case 2:
                presenter.setOrder(ServiceListPresenter.NUMBER);
                break;
        }
        presenter.refreshList(false);
    }

    @Override
    public void onTabReselect(int position) {
        if (position == 1) {
            if (presenter.getOrder().equals(ServiceListPresenter.PRICE_LESS)) {
                presenter.setOrder(ServiceListPresenter.PRICE_MORE);
            } else {
                presenter.setOrder(ServiceListPresenter.PRICE_LESS);
            }
            presenter.refreshList(false);
        }
    }
}
