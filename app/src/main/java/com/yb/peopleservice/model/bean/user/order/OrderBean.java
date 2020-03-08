package com.yb.peopleservice.model.bean.user.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.yb.peopleservice.model.bean.user.AddressListVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/8 9:18
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderBean implements Parcelable {
    public static final int ALL = 1000;//全部订单
    public static final int NO_PAY = -1;//待付款
    public static final int UNDER_WAY = 100;//进行中
    public static final int CLOSE = 0;//已关闭
    public static final int GROUP_NOT_FINISH = 1;//待成团
    public static final int PAID = 2;//待指派
    public static final int ASSIGN = 3;//待接单
    public static final int WAITING = 4;//前往中
    public static final int ARRIVED = 5;//已抵达
    public static final int DOING = 6;//服务中
    public static final int ASSESS = 7;//待评价
    public static final int COMPLETED = 8;//已完成

    public static final int REFUND_APPLY = 9;//退款申请
    /**
     * amount : 0
     * calculatedDistance : 0
     * commodityId : string
     * consigneeAddress : string
     * consigneeCity : string
     * consigneeHouseNumber : string
     * consigneeName : string
     * consigneePhone : string
     * consigneeProvince : string
     * consigneeRegion : string
     * createTime : 2020-01-08T01:15:48.477Z
     * customerId : string
     * distance : 0
     * endLatitude : string
     * endLongitude : string
     * evaluateLevel : 0
     * exactPrice : 0
     * finishTime : 2020-01-08T01:15:48.477Z
     * groupBuy : 0
     * groupBuyGroupId : string
     * id : string
     * message : string
     * name : string
     * originTotalPrice : 0
     * payTime : 2020-01-08T01:15:48.477Z
     * price : 0
     * refundConfirmTime : 2020-01-08T01:15:48.477Z
     * refundImgs : string
     * refundMoney : 0
     * refundReason : string
     * refundStatus : 0
     * refundTime : 2020-01-08T01:15:48.477Z
     * refundType : 0
     * serviceStaffId : string
     * shopId : string
     * startLatitude : string
     * startLongitude : string
     * startTime : 2020-01-08T01:15:48.477Z
     * status : 0
     * totalPrice : 0
     */

    private int amount;//购买数量
    private int calculatedDistance;//计算距离，0否 1是，如果是1，那么会有起步价，起步距离的属性值
    private String commodityId;//商品id
    private String consigneeAddress;//被服务人详细地址
    private String consigneeCity;//被服务人市
    private String consigneeHouseNumber;//被服务人门牌号
    private String consigneeName;//被服务人姓名
    private String consigneePhone;//被服务人电话
    private String consigneeProvince;//被服务人省
    private String consigneeRegion;//被服务人区/县
    private String createTime;//订单创建时间戳
    private String customerId;//顾客id
    private int distance;//距离，起点到终点由地图API计算得到的总距离
    private String endLatitude;//终点位置纬度
    private String endLongitude;//终点位置经度
    private int evaluateLevel;//评价等级 1到5星 整数1到5
    private int exactPrice;//实际单价，折扣以后的单价
    private String finishTime;//服务完成时间
    private int groupBuy;//是否是团购订单 0否 1是
    private String groupBuyGroupId;//团购的团的id
    private String id;
    private String message;//订单备注信息，下单的时候添加，可以由店铺修改
    private String name;//订单名称，可忽略的参数
    private float originTotalPrice;//原来的总价，如果有优惠，那么这个是优惠以前的价格，然后一条横线，表示已优惠
    private String payTime;//订单支付时间
    private float price;//单价
    private String refundConfirmTime;//退款确认时间
    private String refundImgs;//退款申请的图片，uri数组 ['/img/…’,…]
    private float refundMoney;//退款的金额，需要退款的所有金额
    private String refundReason;//退款申请原因文字描述
    private String refundStatus;//退款申请状态 1申请中 0已关闭 关闭可能是由商家关闭，也可以由顾客关闭 2退款成功
    private String refundTime;//申请退款时间
    private int refundType;//退款的原因类型,1.不想买了2.团购未成功3.其它
    private String serviceStaffId;//指派的服务人员的ID
    private String shopId;//店铺id
    private String startLatitude;//起点位置纬度
    private String startLongitude;//起点位置经度
    private String startTime;//服务开始时间
    private int status;//订单状态：0凑单未成功，订单关闭 1团购已付款未凑单完成
    // 2等待指派服务人员(买已付款或团购已凑单完成) 3等待服务人员接受
    // 4服务人员接受，正在前往执行 如果服务人员拒绝，那么订单进入2，需要指派服务人员
    // 5服务人员已上门，此时打开录音功能 6服务人员已被用户确认，开始服务7已完成交易，待评价
    // 8已评价，订单全部完成
    private float totalPrice;//实际总价，订单实际需要支付的金额，由此生成支付金额
    private String groupId;
    private String orderNumber;
    private List<CouponBean> coupons;//优惠券列表

    public List<CouponBean> getCoupons() {
        if (coupons == null) {
            return new ArrayList<>();
        }
        return coupons;
    }

    public void setCoupons(List<CouponBean> coupons) {
        this.coupons = coupons;
    }

    public String getOrderNumber() {
        return orderNumber == null ? "" : orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGroupId() {
        return groupId == null ? "" : groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(int calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeHouseNumber() {
        return consigneeHouseNumber;
    }

    public void setConsigneeHouseNumber(String consigneeHouseNumber) {
        this.consigneeHouseNumber = consigneeHouseNumber;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeRegion() {
        return consigneeRegion;
    }

    public void setConsigneeRegion(String consigneeRegion) {
        this.consigneeRegion = consigneeRegion;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public int getEvaluateLevel() {
        return evaluateLevel;
    }

    public void setEvaluateLevel(int evaluateLevel) {
        this.evaluateLevel = evaluateLevel;
    }

    public int getExactPrice() {
        return exactPrice;
    }

    public void setExactPrice(int exactPrice) {
        this.exactPrice = exactPrice;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getGroupBuy() {
        return groupBuy;
    }

    public void setGroupBuy(int groupBuy) {
        this.groupBuy = groupBuy;
    }

    public String getGroupBuyGroupId() {
        return groupBuyGroupId;
    }

    public void setGroupBuyGroupId(String groupBuyGroupId) {
        this.groupBuyGroupId = groupBuyGroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOriginTotalPrice() {
        return originTotalPrice;
    }

    public void setOriginTotalPrice(float originTotalPrice) {
        this.originTotalPrice = originTotalPrice;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getRefundConfirmTime() {
        return refundConfirmTime;
    }

    public void setRefundConfirmTime(String refundConfirmTime) {
        this.refundConfirmTime = refundConfirmTime;
    }

    public List<String> refundImgList() {
        List<String> imgList = GsonUtils.fromJson(refundImgs,
                new TypeToken<List<String>>() {
        }.getType());
        if (imgList != null) {
            return imgList;
        } else {
            return new ArrayList<>();
        }

    }

    public String getRefundImgs() {
        return refundImgs;
    }

    public void setRefundImgs(String refundImgs) {
        this.refundImgs = refundImgs;
    }

    public float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public int getRefundType() {
        return refundType;
    }

    public void setRefundType(int refundType) {
        this.refundType = refundType;
    }

    public String getServiceStaffId() {
        return serviceStaffId;
    }

    public void setServiceStaffId(String serviceStaffId) {
        this.serviceStaffId = serviceStaffId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderBean() {
    }

    /**
     * 设置订单的地址信息
     */
    public void setAddressBean(AddressListVO vo) {
        setConsigneeAddress(vo.getDetailAddress());
        setConsigneeHouseNumber(vo.getHouseNum());
        setConsigneeName(vo.getConsigneeName());
        setConsigneePhone(vo.getConsigneePhone());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.amount);
        dest.writeInt(this.calculatedDistance);
        dest.writeString(this.commodityId);
        dest.writeString(this.consigneeAddress);
        dest.writeString(this.consigneeCity);
        dest.writeString(this.consigneeHouseNumber);
        dest.writeString(this.consigneeName);
        dest.writeString(this.consigneePhone);
        dest.writeString(this.consigneeProvince);
        dest.writeString(this.consigneeRegion);
        dest.writeString(this.createTime);
        dest.writeString(this.customerId);
        dest.writeInt(this.distance);
        dest.writeString(this.endLatitude);
        dest.writeString(this.endLongitude);
        dest.writeInt(this.evaluateLevel);
        dest.writeInt(this.exactPrice);
        dest.writeString(this.finishTime);
        dest.writeInt(this.groupBuy);
        dest.writeString(this.groupBuyGroupId);
        dest.writeString(this.id);
        dest.writeString(this.message);
        dest.writeString(this.name);
        dest.writeFloat(this.originTotalPrice);
        dest.writeString(this.payTime);
        dest.writeFloat(this.price);
        dest.writeString(this.refundConfirmTime);
        dest.writeString(this.refundImgs);
        dest.writeFloat(this.refundMoney);
        dest.writeString(this.refundReason);
        dest.writeString(this.refundStatus);
        dest.writeString(this.refundTime);
        dest.writeInt(this.refundType);
        dest.writeString(this.serviceStaffId);
        dest.writeString(this.shopId);
        dest.writeString(this.startLatitude);
        dest.writeString(this.startLongitude);
        dest.writeString(this.startTime);
        dest.writeInt(this.status);
        dest.writeFloat(this.totalPrice);
        dest.writeString(this.groupId);
        dest.writeString(this.orderNumber);
        dest.writeTypedList(this.coupons);
    }

    protected OrderBean(Parcel in) {
        this.amount = in.readInt();
        this.calculatedDistance = in.readInt();
        this.commodityId = in.readString();
        this.consigneeAddress = in.readString();
        this.consigneeCity = in.readString();
        this.consigneeHouseNumber = in.readString();
        this.consigneeName = in.readString();
        this.consigneePhone = in.readString();
        this.consigneeProvince = in.readString();
        this.consigneeRegion = in.readString();
        this.createTime = in.readString();
        this.customerId = in.readString();
        this.distance = in.readInt();
        this.endLatitude = in.readString();
        this.endLongitude = in.readString();
        this.evaluateLevel = in.readInt();
        this.exactPrice = in.readInt();
        this.finishTime = in.readString();
        this.groupBuy = in.readInt();
        this.groupBuyGroupId = in.readString();
        this.id = in.readString();
        this.message = in.readString();
        this.name = in.readString();
        this.originTotalPrice = in.readFloat();
        this.payTime = in.readString();
        this.price = in.readFloat();
        this.refundConfirmTime = in.readString();
        this.refundImgs = in.readString();
        this.refundMoney = in.readFloat();
        this.refundReason = in.readString();
        this.refundStatus = in.readString();
        this.refundTime = in.readString();
        this.refundType = in.readInt();
        this.serviceStaffId = in.readString();
        this.shopId = in.readString();
        this.startLatitude = in.readString();
        this.startLongitude = in.readString();
        this.startTime = in.readString();
        this.status = in.readInt();
        this.totalPrice = in.readFloat();
        this.groupId = in.readString();
        this.orderNumber = in.readString();
        this.coupons = in.createTypedArrayList(CouponBean.CREATOR);
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
}
