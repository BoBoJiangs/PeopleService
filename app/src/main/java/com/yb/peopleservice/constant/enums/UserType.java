package com.yb.peopleservice.constant.enums;

/**
 * 账号类型
 * Created by weilin on 2017/8/10.
 */

public enum UserType {

    /**
     * 店铺
     */
    SHOP("shop"),
    /**
     * 服务人员
     */
    STAFF("staff"),

    /**
     * 顾客
     */
    CUSTOMER("customer");

    private String value;

    private static UserType mMapType;

    // 构造方法
    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }



}
