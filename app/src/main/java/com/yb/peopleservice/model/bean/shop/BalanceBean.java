package com.yb.peopleservice.model.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目名称:PeopleService
 * 类描述: 余额
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/31 16:03
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class BalanceBean implements Parcelable {
    public static final String STORE = "1";//店铺
    public static final String PRRSONAL = "2";//服务人员
    /**
     * availableBalance : 0
     * id : string
     * targetId : string
     * targetType : 0
     * unsettledBalance : 0
     * unusableBalance : 0
     */

    private double availableBalance;//可用余额 用户确认收货后，增加可用店铺余额，可用于店铺提现
    private String id;
    private String targetId;//
    private double targetType;//对象类型 1商户 2服务人员
    private double unsettledBalance;//待结算金额 顾客付款购买商品服务后，钱进入这个账目下，等待服务完成以后，将会变成可用店铺余额
    private double unusableBalance;//不可用余额 商城进行提现申请的时候，将会把部分余额冻结，增加不可用余额
    /**
     * completeMoney : 0.0
     * completeNumber : 0.0
     * orderMoney : 0.0
     * orderNumber : 0.0
     * payMoney : 0.0
     * payNumber : 0.0
     * refundMoney : 0.0
     * refundNumber : 0.0
     */

    private double completeMoney;//完成交易金额
    private double completeNumber;//完成交易数量
    private double orderMoney;//订单金额，下单就算，不管是否支付
    private double orderNumber;//订单数量，下单就算，不管是否支付
    private double payMoney;//支付金额
    private double payNumber;//支付数量
    private double refundMoney;//确认退款金额
    private double refundNumber;//确认退款数量

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId == null ? "" : targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public double getTargetType() {
        return targetType;
    }

    public void setTargetType(double targetType) {
        this.targetType = targetType;
    }

    public double getUnsettledBalance() {
        return unsettledBalance;
    }

    public void setUnsettledBalance(double unsettledBalance) {
        this.unsettledBalance = unsettledBalance;
    }

    public double getUnusableBalance() {
        return unusableBalance;
    }

    public void setUnusableBalance(double unusableBalance) {
        this.unusableBalance = unusableBalance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.availableBalance);
        dest.writeString(this.id);
        dest.writeString(this.targetId);
        dest.writeDouble(this.targetType);
        dest.writeDouble(this.unsettledBalance);
        dest.writeDouble(this.unusableBalance);
    }

    public BalanceBean() {
    }

    protected BalanceBean(Parcel in) {
        this.availableBalance = in.readDouble();
        this.id = in.readString();
        this.targetId = in.readString();
        this.targetType = in.readDouble();
        this.unsettledBalance = in.readDouble();
        this.unusableBalance = in.readDouble();
    }

    public static final Creator<BalanceBean> CREATOR = new Creator<BalanceBean>() {
        @Override
        public BalanceBean createFromParcel(Parcel source) {
            return new BalanceBean(source);
        }

        @Override
        public BalanceBean[] newArray(int size) {
            return new BalanceBean[size];
        }
    };

    public double getCompleteMoney() {
        return completeMoney;
    }

    public void setCompleteMoney(double completeMoney) {
        this.completeMoney = completeMoney;
    }

    public double getCompleteNumber() {
        return completeNumber;
    }

    public void setCompleteNumber(double completeNumber) {
        this.completeNumber = completeNumber;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public double getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(double orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public double getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(double payNumber) {
        this.payNumber = payNumber;
    }

    public double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public double getRefundNumber() {
        return refundNumber;
    }

    public void setRefundNumber(double refundNumber) {
        this.refundNumber = refundNumber;
    }
}
