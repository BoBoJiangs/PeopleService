package com.yb.peopleservice.view.adapter.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yb.peopleservice.R;
import com.yb.peopleservice.constant.AppConstant;
import com.yb.peopleservice.constant.IntentKeyConstant;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.view.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sts.base.presenter.AbstractPresenter;
import cn.sts.platform.constant.ThirdPlatformBroadcastConstant;
import cn.sts.platform.constant.ThirdPlatformIntentKeyConstant;
import cn.sts.platform.constant.ThirdPlatformStatusConstant;
import cn.sts.platform.presenter.pay.PayPresenter;

/**
 * 类描述: 支付页面
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/9/18  10:57
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PayActivity extends BaseToolbarActivity {
    public final static String ORDER_ID = "orderId";
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.payBtn)
    Button payBtn;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.checkbox2)
    CheckBox checkbox2;
    private float payPrice = 0;//实际支付的费用
    private int payType;//支付方式 默认微信支付
    private String orderId;//订单号
    private PayPresenter presenter;
    private WeChatPayReceiver receiver;//微信登录的广播
    private AddressListVO addressVO;//地址

    @Override
    protected int contentViewResID() {
        return R.layout.activity_pay;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void initData() {
        presenter = new PayPresenter(this);
        orderId = getIntent().getStringExtra(ORDER_ID);
        payPrice = getIntent().getFloatExtra(IntentKeyConstant.DATA_KEY, 0);
        priceTV.setText("¥ " + payPrice);
        setPayType();

        addressVO = getIntent().getParcelableExtra(AddressListVO.class.getName());
        receiver = new WeChatPayReceiver();
        IntentFilter intentFilter = new IntentFilter(ThirdPlatformBroadcastConstant.PAY_RESULT);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public String getTitleName() {
        return "支付订单";
    }


    @OnClick({R.id.weChatRL, R.id.aliPayRL, R.id.payBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weChatRL:
                payType = AppConstant.WECHAT_TYPE;
                setPayType();
                break;
            case R.id.aliPayRL:
                payType = AppConstant.ALIPAY_TYPE;
                setPayType();
                break;
            case R.id.payBtn:
                if (payType == 0) {
                    ToastUtils.showLong("请选择支付方式");
                    return;
                }
                if (payType == AppConstant.ALIPAY_TYPE) {
                    presenter.aliPay(orderId, "订单支付");
                } else {
                    presenter.wxPay(orderId, "订单支付", "flowerWorld");
                }

                break;
        }
    }

    /**
     * 根据选择的支付方式改变对应布局
     */
    private void setPayType() {
        if (payType == AppConstant.WECHAT_TYPE) {
            payBtn.setText("微信支付(" + priceTV.getText() + ")");
            checkbox1.setChecked(true);
            checkbox2.setChecked(false);
        } else {
            payBtn.setText("支付宝支付(" + priceTV.getText() + ")");
            checkbox1.setChecked(false);
            checkbox2.setChecked(true);
        }
    }

    /**
     * 微信登录成功返回的个人信息
     */
    public class WeChatPayReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int payResult = intent.getIntExtra(ThirdPlatformIntentKeyConstant.PAY_RESULT, 0);
                if (payResult == ThirdPlatformStatusConstant.SUCCESS) {
//                    startActivity(new Intent(context, PaySuccessActivity.class)
//                            .putExtra(AddressListVO.class.getName(),addressVO));
                    finish();
                }
            }

        }

    }
}
