package com.yb.peopleservice.model.database.bean;

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
public class ShopInfo implements Serializable {

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
    private int status;//状态 0禁用 1正常 2新注册 3待审核 4审核不通过

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
}
