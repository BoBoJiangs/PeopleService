package com.yb.peopleservice.model.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

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
    private String businessLicenseImg;//营业执照
    private String headImg;//头像
    private String introduction;//介绍
    private double locationLatitude;//店铺位置纬度
    private double locationLongitude;//店铺位置经度
    private String managerIdcardImgBack;//管理员身份证背面(个人信息页面)
    private String managerIdcardImgFront;//管理员身份证正面(国徽页面)
    private String message;//平台给店铺的信息，当店铺被审核拒绝通过，或者拉黑以后，会有信息
    private String managerIdcardNumber;//管理员身份证号码
    private String managerName;//管理员名称
    private String name;//店铺名称
    private String phone;//店铺电话
    private int level;
    private int status;//状态 0禁用 1正常 2新注册 3待审核 4审核不通过 5申请解除入驻店铺（服务人员状态）
    private double longitude;
    private double latitude;
    private int orderNumber;
    private double praiseRate;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
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
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude ;
    }

    public void setLocationLongitude(double locationLongitude) {
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
        dest.writeString(this.businessLicenseImg);
        dest.writeString(this.headImg);
        dest.writeString(this.introduction);
        dest.writeDouble(this.locationLatitude);
        dest.writeDouble(this.locationLongitude);
        dest.writeString(this.managerIdcardImgBack);
        dest.writeString(this.managerIdcardImgFront);
        dest.writeString(this.message);
        dest.writeString(this.managerIdcardNumber);
        dest.writeString(this.managerName);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeInt(this.level);
        dest.writeInt(this.status);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeInt(this.orderNumber);
        dest.writeDouble(this.praiseRate);
    }

    protected ShopInfo(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.businessLicenseImg = in.readString();
        this.headImg = in.readString();
        this.introduction = in.readString();
        this.locationLatitude = in.readDouble();
        this.locationLongitude = in.readDouble();
        this.managerIdcardImgBack = in.readString();
        this.managerIdcardImgFront = in.readString();
        this.message = in.readString();
        this.managerIdcardNumber = in.readString();
        this.managerName = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.level = in.readInt();
        this.status = in.readInt();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.orderNumber = in.readInt();
        this.praiseRate = in.readDouble();
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
