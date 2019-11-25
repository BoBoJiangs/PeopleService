package com.yb.peopleservice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/25 10:35
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ClassifyListBean {
    private String name;
    private List<ClassifyListBean> childList;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassifyListBean> getChildList() {
        if (childList == null) {
            return new ArrayList<>();
        }
        return childList;
    }

    public void setChildList(List<ClassifyListBean> childList) {
        this.childList = childList;
    }
}
