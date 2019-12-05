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
    private List<ClassifyListBean> childList;
    /**
     * categoryId : 123
     * parentId : 0
     * categoryName : 服饰鞋包
     * pic : http://123.56.249.114/2019/09/1414ce410f484820943df75e8d6c8337.jpg
     * categoryIcon : null
     */

    private int categoryId;
    private int parentId;
    private String categoryName;
    private String pic;
    private Object categoryIcon;


    public List<ClassifyListBean> getChildList() {
        if (childList == null) {
            return new ArrayList<>();
        }
        return childList;
    }

    public void setChildList(List<ClassifyListBean> childList) {
        this.childList = childList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName == null ? "" : categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPic() {
        return pic == null ? "" : pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Object getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(Object categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
