package com.yb.peopleservice.model.bean.shop;

import java.io.Serializable;

/**
 * 项目名称:PeopleService
 * 类描述: 服务人员认证信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/16 16:05
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ServiceInfo implements Serializable {


    /**
     * customerPraiseRate : 0
     * headImg : string
     * id : string
     * idCardChecked : 0
     * idCardImgBack : string
     * idCardImgFront : string
     * idCardNumber : string
     * introduction : string
     * level : 0
     * message : string
     * name : string
     * nickname : string
     * orderAcceptanceRate : 0
     * phone : string
     * refundRate : 0
     * status : 0
     * timestamp : 2019-12-19T07:12:14.882Z
     */

    private String headImg;
    private String id;
    private String idCardImgBack;
    private String idCardImgFront;
    private String idCardNumber;
    private String introduction;
    private String message;
    private String name;
    private String nickname;
    private String phone;
    private int status;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCardImgBack() {
        return idCardImgBack;
    }

    public void setIdCardImgBack(String idCardImgBack) {
        this.idCardImgBack = idCardImgBack;
    }

    public String getIdCardImgFront() {
        return idCardImgFront;
    }

    public void setIdCardImgFront(String idCardImgFront) {
        this.idCardImgFront = idCardImgFront;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
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
