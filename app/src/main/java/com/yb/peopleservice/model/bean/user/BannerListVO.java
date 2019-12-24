package com.yb.peopleservice.model.bean.user;

/**
 * 项目名称:Exam
 * 类描述:首页Banner对象
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2018/10/29 16:39
 * 修改人: 
 * 修改时间:
 * 修改描述:
 */
public class BannerListVO {

    /**
     * id : 01b1b201-bf5a-4f91-a712-01e023c39cba
     * mobileImg : /imgs/2019/12/22/ca5a2224-4d23-4f42-8df6-56c2cc5cc1e0.png
     * webImg : /imgs/2019/12/22/1f1bc2e8-ca44-4e6f-a7f1-b212bc99dde3.png
     */

    private String id;
    private String mobileImg;
    private String webImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileImg() {
        return mobileImg;
    }

    public void setMobileImg(String mobileImg) {
        this.mobileImg = mobileImg;
    }

    public String getWebImg() {
        return webImg;
    }

    public void setWebImg(String webImg) {
        this.webImg = webImg;
    }
}
