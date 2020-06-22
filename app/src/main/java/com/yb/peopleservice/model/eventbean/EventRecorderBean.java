package com.yb.peopleservice.model.eventbean;

/**
 * 项目名称:Exam
 * 类描述:通知录音服务更新状态
 * 创建人:YangBo QQ:819463350
 * 创建时间: 2018/8/30 10:55
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EventRecorderBean {
    public static final String START = "START";
    public static final String STOP = "STOP";
    private String message;
    private String orderId;
    private String userId;

    public EventRecorderBean() {
    }

    public EventRecorderBean(String userId,String message,String orderId) {
        this.message = message;
        this.orderId = orderId;
        this.userId = userId;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EventRecorderBean(String message) {
        this.message = message;
    }


    public String getOrderId() {
        return orderId == null ? "" : orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
