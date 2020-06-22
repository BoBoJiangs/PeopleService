package com.yb.peopleservice.model.bean;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/25 20:09
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PlatformLoginBean {
    private String id;//第三方登录时未注册返回的ID用于注册
    private String code;
    private String phone;
    private String personType;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonType() {
        return personType == null ? "" : personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
}
