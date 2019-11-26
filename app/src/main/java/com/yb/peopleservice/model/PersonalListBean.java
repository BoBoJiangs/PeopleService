package com.yb.peopleservice.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 项目名称:Exam
 * 类描述: 个人中心
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/8/8 16:19
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class PersonalListBean implements MultiItemEntity {
    public static final int PAGE_TYPE = 3;//浏览历史
    public static final int CONTENT_TYPE = 1;//我的订单
    public static final int TITLE_TYPE = 2;//我的钱包
    //网格布局每行所占的个数
    public static final int SPAN_SIZE_FOUR = 1;
    public static final int SPAN_SIZE_ONE = 3;
    private int itemType;
    private int spanSize;


    public PersonalListBean(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
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
