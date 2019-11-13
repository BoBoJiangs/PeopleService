package cn.sts.base.model.server.vo;

import java.io.Serializable;

/**
 * 常见问题实体
 * Created by weilin on 2019/7/3.
 */


public class HelpCenterVO implements Serializable {

    /**
     * 问题ID
     */
    private Long id;

    /**
     * 问题主题
     */
    private String title;

    /**
     * 类型名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
