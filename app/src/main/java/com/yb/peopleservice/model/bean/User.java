package com.yb.peopleservice.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户信息
 */
@Entity
public class User {
    @Id
    private String id;
    private int access_token;//登录token
    @Generated(hash = 1517977054)
    public User(String id, int access_token) {
        this.id = id;
        this.access_token = access_token;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getAccess_token() {
        return this.access_token;
    }
    public void setAccess_token(int access_token) {
        this.access_token = access_token;
    }
}
