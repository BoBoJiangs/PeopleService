package com.yb.peopleservice.model.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.StringUtils;

import java.io.Serializable;

/**
 * 项目名称:PeopleService
 * 类描述: 店铺信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/16 16:05
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ShopInfo implements Parcelable {

    /**
     * address : string
     * businessLicenseImg : string
     * comprehensiveScore : 0
     * conformityOfProductDescription : 0
     * customerPraiseRate : 0
     * deposit : 0
     * headImg : string
     * high_quality_merchants : 0
     * id : string
     * introduction : string
     * level : 0
     * locationLatitude : string
     * locationLongitude : string
     * logisticsServiceQuality : 0
     * managerIdcardImgBack : string
     * managerIdcardImgFront : string
     * message : string
     * name : string
     * orderAcceptanceRate : 0
     * phone : string
     * selfSupport : 0
     * shopManagerId : string
     * status : 0
     * storeServiceQuality : 0
     * timestamp : 2019-12-16T07:16:28.108Z
     */
    private String id;
    private String address; //店铺地址
    private String backCardNumber;//银行卡号
    private String backName;//银行开户行
    private String backgroundImg;//背景图
    private String businessLicenseImg;//营业执照
    private String headImg;//头像
    private String introduction;//介绍
    private String locationLatitude;//店铺位置纬度
    private String locationLongitude;//店铺位置经度
    private String managerIdcardImgBack;//管理员身份证背面(个人信息页面)
    private String managerIdcardImgFront;//管理员身份证正面(国徽页面)
    private String message;//平台给店铺的信息，当店铺被审核拒绝通过，或者拉黑以后，会有信息
    private String managerIdcardNumber;//管理员身份证号码
    private String managerName;//管理员名称
    private String shopManagerId;//管理员ID
    private String managerSex;//管理员性别
    private String managerBirthday;//管理员生日
    private String saveLocation;//保存位置 0否 1是
    private String selfSupport;//是否是自营 1是 0否
    private String name;//店铺名称
    private String phone;//店铺电话
    private String deposit;//押金
    private int level;
    private int stars;//评价星级 1-5；
    private int status;//状态 0拉黑禁用 1正常 2需要审核 3已提交审核信息
    private String longitude;//店铺位置经度
    private String latitude;//店铺位置纬度
    private int orderReceived;//已接单数量
    private int orderAmount;//累计完成订单数量
    private double praiseRate;//好评率 如0.99即好评率99%
    private String province;//省
    private String city;//市

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBackCardNumber() {
        return backCardNumber == null ? "" : backCardNumber;
    }

    public void setBackCardNumber(String backCardNumber) {
        this.backCardNumber = backCardNumber;
    }

    public String getBackName() {
        return backName == null ? "" : backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getBackgroundImg() {
        return backgroundImg == null ? "" : backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getShopManagerId() {
        return shopManagerId == null ? "" : shopManagerId;
    }

    public void setShopManagerId(String shopManagerId) {
        this.shopManagerId = shopManagerId;
    }

    public String getManagerSex() {
        return managerSex == null ? "" : managerSex;
    }

    public void setManagerSex(String managerSex) {
        this.managerSex = managerSex;
    }

    public String getManagerBirthday() {
        return managerBirthday == null ? "" : managerBirthday;
    }

    public void setManagerBirthday(String managerBirthday) {
        this.managerBirthday = managerBirthday;
    }

    public String getSaveLocation() {
        return saveLocation == null ? "" : saveLocation;
    }

    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    public String getSelfSupport() {
        return selfSupport == null ? "" : selfSupport;
    }

    public void setSelfSupport(String selfSupport) {
        this.selfSupport = selfSupport;
    }

    public String getDeposit() {
        return deposit == null ? "" : deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public int getOrderNumber() {
        return orderReceived;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderReceived = orderNumber;
    }

    public double getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(double praiseRate) {
        this.praiseRate = praiseRate;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getLongitude() {
        if (StringUtils.isEmpty(longitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(longitude);
        }

    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        if (StringUtils.isEmpty(latitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(latitude);
        }
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getLevel() {
        return level;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg == null ? "" : businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getIntroduction() {
        return introduction == null ? "" : introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public double getLocationLatitude() {
        if (StringUtils.isEmpty(locationLatitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(locationLatitude);
        }
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        if (StringUtils.isEmpty(locationLongitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(locationLongitude);
        }
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getManagerIdcardImgBack() {
        return managerIdcardImgBack == null ? "" : managerIdcardImgBack;
    }

    public void setManagerIdcardImgBack(String managerIdcardImgBack) {
        this.managerIdcardImgBack = managerIdcardImgBack;
    }

    public String getManagerIdcardImgFront() {
        return managerIdcardImgFront == null ? "" : managerIdcardImgFront;
    }

    public void setManagerIdcardImgFront(String managerIdcardImgFront) {
        this.managerIdcardImgFront = managerIdcardImgFront;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getManagerIdcardNumber() {
        return managerIdcardNumber == null ? "" : managerIdcardNumber;
    }

    public void setManagerIdcardNumber(String managerIdcardNumber) {
        this.managerIdcardNumber = managerIdcardNumber;
    }

    public String getManagerName() {
        return managerName == null ? "" : managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ShopInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeString(this.backCardNumber);
        dest.writeString(this.backName);
        dest.writeString(this.backgroundImg);
        dest.writeString(this.businessLicenseImg);
        dest.writeString(this.headImg);
        dest.writeString(this.introduction);
        dest.writeString(this.locationLatitude);
        dest.writeString(this.locationLongitude);
        dest.writeString(this.managerIdcardImgBack);
        dest.writeString(this.managerIdcardImgFront);
        dest.writeString(this.message);
        dest.writeString(this.managerIdcardNumber);
        dest.writeString(this.managerName);
        dest.writeString(this.shopManagerId);
        dest.writeString(this.managerSex);
        dest.writeString(this.managerBirthday);
        dest.writeString(this.saveLocation);
        dest.writeString(this.selfSupport);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.deposit);
        dest.writeInt(this.level);
        dest.writeInt(this.status);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeInt(this.orderReceived);
        dest.writeDouble(this.praiseRate);
        dest.writeString(this.province);
        dest.writeString(this.city);
    }

    protected ShopInfo(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.backCardNumber = in.readString();
        this.backName = in.readString();
        this.backgroundImg = in.readString();
        this.businessLicenseImg = in.readString();
        this.headImg = in.readString();
        this.introduction = in.readString();
        this.locationLatitude = in.readString();
        this.locationLongitude = in.readString();
        this.managerIdcardImgBack = in.readString();
        this.managerIdcardImgFront = in.readString();
        this.message = in.readString();
        this.managerIdcardNumber = in.readString();
        this.managerName = in.readString();
        this.shopManagerId = in.readString();
        this.managerSex = in.readString();
        this.managerBirthday = in.readString();
        this.saveLocation = in.readString();
        this.selfSupport = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.deposit = in.readString();
        this.level = in.readInt();
        this.status = in.readInt();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.orderReceived = in.readInt();
        this.praiseRate = in.readDouble();
        this.province = in.readString();
        this.city = in.readString();
    }

    public static final Creator<ShopInfo> CREATOR = new Creator<ShopInfo>() {
        @Override
        public ShopInfo createFromParcel(Parcel source) {
            return new ShopInfo(source);
        }

        @Override
        public ShopInfo[] newArray(int size) {
            return new ShopInfo[size];
        }
    };
}
