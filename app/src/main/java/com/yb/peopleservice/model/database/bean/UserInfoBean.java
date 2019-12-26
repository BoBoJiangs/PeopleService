package com.yb.peopleservice.model.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目名称:PeopleService
 * 类描述:用户详细信息
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/25 16:55
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
@Entity
public class UserInfoBean {

    /**
     * id : 9541869a-5c14-4b0f-b10d-4df0bc36c6f0
     * name : 新注册
     * nickname : 新注册
     * totalPoints : 0
     * level : 0
     * member : 0
     * timestamp : 2019-12-19 14:47:21
     */
    @Id
    private String id;
    private String name;
    private String nickname;//昵称
    private int totalPoints;//总分，即用户购买单数
    private int level;//用户等级
    private int member;//是否为会员 1是 0否
    private String headImg;//头像
    private String phone;//电话号码

    @Generated(hash = 1423685025)
    public UserInfoBean(String id, String name, String nickname, int totalPoints,
            int level, int member, String headImg, String phone) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.totalPoints = totalPoints;
        this.level = level;
        this.member = member;
        this.headImg = headImg;
        this.phone = phone;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }
}
