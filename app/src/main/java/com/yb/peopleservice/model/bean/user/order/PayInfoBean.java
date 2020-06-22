package com.yb.peopleservice.model.bean.user.order;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/18 20:58
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PayInfoBean {

    /**
     * id : 7b0dd58a-8982-4c92-b480-e77d3f9f9a57
     * orderId : a4b30b2d-2f44-4869-af14-03c57d72d3c5
     * customerId : 9541869a-5c14-4b0f-b10d-4df0bc36c6f0
     * shopId : 2aee461e-eccc-4f66-b80a-ddf1bf9b0f37
     * payType : 1
     * platformType : 1
     * appId : 2021001105656879
     * money : 50
     * complete : 1
     * outTradeNo : 7b0dd58a89824c92b480e77d3f9f9a57
     * timestamp : 2020-02-07 11:21:19
     * payTime : 2020-02-07 11:21:25
     */

    private String id;
    private String orderId;
    private String customerId;
    private String shopId;
    private int payType;
    private int platformType;
    private String appId;
    private float money;
    private int complete;
    private String outTradeNo;
    private String timestamp;
    private String payTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
