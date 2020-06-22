package com.yb.peopleservice.constant.enums;

/**
 * 类描述: 第三方登陆类型
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/24  22:48
 * 修改人:
 * 修改时间:
 * 修改描述:
 */

public enum LoginType {

    /**
     * 支付宝
     */
    ALIPAY("alipay"),
    /**
     * 微信
     */
    WECHAT("wx");


    private String value;

    private static LoginType mLoginType;

    // 构造方法
    LoginType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 获取当前用户类型
     */
    public static LoginType getLoginType() {
        return mLoginType;
    }

    /**
     * 设置当前登录类型
     *
     */
    public static void setLoginType(LoginType loginType) {
        mLoginType = loginType;
    }

}
