package com.yb.peopleservice.view.activity.common;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.BalanceBean;
import com.yb.peopleservice.model.presenter.shop.IncomePresenter;
import com.yb.peopleservice.view.base.BaseToolbarActivity;
import com.yb.peopleservice.view.weight.CustomPopup.CustomCashPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.base.util.NumberUtil;

public class MyIncomeActivity extends BaseToolbarActivity implements IncomePresenter.IIncomeCallback {
    IncomePresenter presenter;
    @BindView(R.id.moneyTV1)
    TextView moneyTV1;
    @BindView(R.id.cashOutBtn)
    Button cashOutBtn;
    @BindView(R.id.moneyTV2)
    TextView moneyTV2;
    @BindView(R.id.moneyTV3)
    TextView moneyTV3;
    @BindView(R.id.moneyTV4)
    TextView moneyTV4;
    @BindView(R.id.tradeNumTV)
    TextView tradeNumTV;
    @BindView(R.id.tradeTV)
    TextView tradeTV;
    @BindView(R.id.payNumTV)
    TextView payNumTV;
    @BindView(R.id.payTV)
    TextView payTV;
    @BindView(R.id.card_view_one)
    CardView cardViewOne;
    @BindView(R.id.orderNumTV)
    TextView orderNumTV;
    @BindView(R.id.orderTV)
    TextView orderTV;
    @BindView(R.id.refundNumTV)
    TextView refundNumTV;
    @BindView(R.id.refundTV)
    TextView refundTV;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.line)
    LinearLayout line;

    private double availableBalance;//可提现的余额
    private String type;


    @Override
    protected int contentViewResID() {
        return R.layout.activity_my_income;
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        presenter = new IncomePresenter(this, this);
        if (BalanceBean.STORE.equals(type)){
            presenter.queryBalance();
        }else{
            container.setVisibility(View.INVISIBLE);
            line.setVisibility(View.INVISIBLE);
        }

        presenter.statistics(type);
        cashOutBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return presenter;
    }

    @Override
    public String getTitleName() {
        return "我的收益";
    }

    @Override
    public void applySuccess(Object data) {
        ToastUtils.showLong("提现申请成功");
        presenter.queryBalance();
    }

    @Override
    public void incomeSuccess(BalanceBean data) {
        if (data != null) {
            cashOutBtn.setVisibility(View.VISIBLE);
            availableBalance = data.getAvailableBalance();
            moneyTV1.setText(data.getAvailableBalance() + "");
            moneyTV2.setText(data.getUnsettledBalance() + "");
            moneyTV3.setText(data.getAvailableBalance() + "");
            moneyTV4.setText(data.getUnusableBalance() + "");
        }

    }

    @Override
    public void incomeFail() {

    }

    @Override
    public void statisticsSuccess(List<BalanceBean> datas) {
        if (datas != null && !datas.isEmpty()) {
            BalanceBean data = datas.get(datas.size() - 1);
            tradeNumTV.setText(data.getCompleteNumber() + "");
            tradeTV.setText("交易金额：¥" + data.getCompleteMoney());

            payNumTV.setText(data.getPayNumber() + "");
            payTV.setText("支付金额：¥" + data.getPayMoney());

            orderNumTV.setText(data.getOrderNumber() + "");
            orderTV.setText("订单金额：¥" + data.getOrderNumber());

            refundNumTV.setText(data.getRefundNumber() + "");
            refundTV.setText("退款金额：¥" + data.getRefundMoney());
        }

    }

    @Override
    public void statisticsFail() {

    }


    @OnClick(R.id.cashOutBtn)
    public void onViewClicked() {
        CustomCashPopup cashPopup = new CustomCashPopup(this);
        cashPopup.setTitleContent("提现金额", "", "请输入提现金额");
        cashPopup.setListener(new OnInputConfirmListener() {
            @Override
            public void onConfirm(String text) {
                if (!StringUtils.isEmpty(text) && NumberUtil.isIntNumber(text)) {
                    double money = Double.parseDouble(text);
                    if (availableBalance >= money){
                        presenter.withDraw(money);
                    }else{
                        ToastUtils.showLong("提现金额不能大于可用余额");
                    }

                }
            }
        }, null);
        new XPopup.Builder(this)
                .asCustom(cashPopup)
                .show();
    }
}
