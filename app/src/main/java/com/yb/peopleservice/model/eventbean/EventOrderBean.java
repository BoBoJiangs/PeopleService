package com.yb.peopleservice.model.eventbean;

/**
 * 项目名称:Exam
 * 类描述:通知订单更新状态
 * 创建人:YangBo QQ:819463350
 * 创建时间: 2018/8/30 10:55
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EventOrderBean {
    private String message;

    public EventOrderBean() {
    }

    public EventOrderBean(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
