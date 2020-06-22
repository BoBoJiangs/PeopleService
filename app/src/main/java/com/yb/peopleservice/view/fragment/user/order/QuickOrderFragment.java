package com.yb.peopleservice.view.fragment.user.order;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.user.ClassifyListBean;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;
import com.yb.peopleservice.model.presenter.user.service.ServiceListPresenter;
import com.yb.peopleservice.view.activity.services.ServiceListActivity;
import com.yb.peopleservice.view.activity.services.order.ConfirmOrderActivity;
import com.yb.peopleservice.view.fragment.user.classify.ClassifyFragment;

import butterknife.BindView;
import cn.sts.base.model.listener.IRequestListener;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/4/21 20:31
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class QuickOrderFragment extends ClassifyFragment {
    @BindView(R.id.topLL)
    LinearLayout topLL;
    @BindView(R.id.titleTV)
    TextView titleTV;
    public static Fragment getInstanceFragment() {
        QuickOrderFragment fragment = new QuickOrderFragment();
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        topLL.setVisibility(View.GONE);
        titleTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOnItemClickListener() {
        childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifyListBean classifyListBean = childAdapter.getItem(position);
                if (classifyListBean != null) {
                    presenter.quickOrder(classifyListBean.getId(), new IRequestListener<ServiceListBean>() {
                        @Override
                        public void onRequestSuccess(ServiceListBean data) {
                            if (data != null) {
                                Intent intent = new Intent(getContext(), ConfirmOrderActivity.class);
                                data.setPayMoney(data.getPrice());
                                data.setGroupType(0);
                                data.setGroupId("");
                                intent.putExtra(ServiceListBean.class.getName(), data);
                                startActivity(intent);
                            } else {
                                ToastUtils.showLong("未找到对应服务");
                            }
                        }

                        @Override
                        public void onRequestFailure(String error) {
                            ToastUtils.showLong("未找到对应服务");
                        }

                        @Override
                        public void onRequestCancel() {

                        }
                    });
                }

            }
        });
    }
}
