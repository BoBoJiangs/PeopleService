package com.yb.peopleservice.model.eventbean;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/3/24 22:54
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class EventLoginBean {
    private String authorCode;

    public EventLoginBean(String authorCode) {
        this.authorCode = authorCode;
    }

    public String getAuthorCode() {
        return authorCode == null ? "" : authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }
}
