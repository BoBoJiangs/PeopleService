package com.yb.peopleservice.view.weight.CustomPopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.core.BottomPopupView;
import com.yb.peopleservice.R;
import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.order.OrderBean;
import com.yb.peopleservice.model.database.bean.ServiceInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/2/22 15:31
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
@SuppressLint("ViewConstructor")
public class PayBottomPopup extends BottomPopupView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.yesBtn)
    Button yesBtn;
    private OrderBean orderBean;
    private Context context;
    private PayCallBack callBack;
    private boolean isAddPay;//是否是补款

    public PayBottomPopup(@NonNull Context context, OrderBean orderBean) {
        super(context);
        this.context = context;
        this.orderBean = orderBean;
    }

    public PayBottomPopup(@NonNull Context context,  PayCallBack callBack) {
        super(context);
        this.callBack = callBack;
        this.context = context;
    }
    public PayBottomPopup(@NonNull Context context,  PayCallBack callBack,boolean isAddPay) {
        super(context);
        this.callBack = callBack;
        this.context = context;
        this.isAddPay = isAddPay;
    }

    public interface PayCallBack{
        void moneyNum(String number);
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.pay_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, getPopupImplView());
        if (isAddPay){
            title.setText("请输入补款金额");
        }
    }



    @OnClick(R.id.yesBtn)
    public void onViewClicked() {
        String number = editText.getText().toString();
        if (StringUtils.isEmpty(number)){
            ToastUtils.showLong("请输入金额");
            return;
        }
        if (callBack!=null){
            dismiss();
            callBack.moneyNum(number);
        }

    }
}
