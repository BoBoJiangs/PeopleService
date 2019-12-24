package com.yb.peopleservice.model.bean.user;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:Exam
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/8/8 16:19
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class HomeListBean implements MultiItemEntity{
    public static final int PAGE_TYPE = 3;//分类
    public static final int CONTENT_TYPE = 1;//内容
    public static final int TITLE_TYPE = 2;//标题
    //网格布局每行所占的个数
    public static final int SPAN_SIZE_FOUR = 1;
    public static final int SPAN_SIZE_ONE = 3;
    private int itemType;
    private int spanSize;
    private List<ClassifyListBean> classList;//首页需要显示的类目


    public HomeListBean(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public List<ClassifyListBean> getClassList() {
        if (classList == null) {
            return new ArrayList<>();
        }
        return classList;
    }

    public void setClassList(List<ClassifyListBean> classList) {
        this.classList = classList;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
