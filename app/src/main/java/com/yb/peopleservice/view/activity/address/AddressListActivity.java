package com.yb.peopleservice.view.activity.address;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.presenter.ServiceListUIPresenter;
import com.yb.peopleservice.model.presenter.user.address.AddressListPresenter;
import com.yb.peopleservice.view.base.BaseListActivity;

import java.util.List;

import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;

import static com.yb.peopleservice.constant.IntentKeyConstant.REQUEST_CODE;
import static com.yb.peopleservice.constant.IntentKeyConstant.RESULT_CODE;

/**
 * 项目名称:Flower
 * 类描述: 地址管理列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/3 11:12
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class AddressListActivity extends BaseListActivity implements BaseQuickAdapter.OnItemChildClickListener {

    private BaseQuickAdapter<AddressListVO, BaseViewHolder> adapter;
    private AddressListPresenter presenter;
    private boolean isSelect;
    @Override
    public int contentViewResID() {
        return R.layout.activity_address_list;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        return adapter;
    }

    private void initAdapterView() {
        adapter = new BaseQuickAdapter<AddressListVO, BaseViewHolder>(R.layout.item_address) {
            @Override
            protected void convert(BaseViewHolder helper, AddressListVO item) {
                helper.setText(R.id.nameTV, item.getConsigneeName() + " " + item.getConsigneePhone());
                helper.setVisible(R.id.emptyTV, "Y".equals(item.getIsDefault()));
                helper.setText(R.id.addressTV, item.getDetailAddress());
                helper.setGone(R.id.checkbox, false);
                helper.addOnClickListener(R.id.editIV);
            }
        };
        adapter.setOnItemChildClickListener(this::onItemChildClick);
    }

    @Override
    public void initView() {
        initAdapterView();
        super.initView();
        ServiceListUIPresenter queryListUI =
                new ServiceListUIPresenter(adapter, swipeRefreshLayout, getApplicationContext()){
                    @Override
                    public void refreshListSuccess(List list) {
                        super.refreshListSuccess(list);
                        baseQuickAdapter.removeAllFooterView();
                    }
                };
        queryListUI.setEmptyText("暂无收货地址");
        presenter = new AddressListPresenter(this, queryListUI);
    }

    @Override
    protected void initData() {
        setOnRefreshListener();
        setLoadMoreListener();
        initQueryListUI();
        isSelect = getIntent().getBooleanExtra("isSelect",false);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }


    /**
     * 初始化下拉或者加载更多的UI统一操作
     */
    private void initQueryListUI() {

        presenter.refreshList(true);

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
    public String getTitleName() {
        return "服务地址";
    }


    @OnClick(R.id.addressBtn)
    public void onViewClicked() {
        startActivityForResult(new Intent(getApplicationContext(), CreateAddressActivity.class), REQUEST_CODE);
    }

    @Override
    public void onClickItem(BaseQuickAdapter a, View view, int position) {
        if (isSelect){
            AddressListVO addressListVO = adapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra(AddressListVO.class.getName(), addressListVO);
            setResult(RESULT_CODE, intent);
            finish();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter a, View view, int position) {
        AddressListVO addressListVO = adapter.getItem(position);
        startActivityForResult(new Intent(getApplicationContext(),
                CreateAddressActivity.class).putExtra(AddressListVO.class.getName(), addressListVO), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            presenter.refreshList(false);
        }
    }
}
