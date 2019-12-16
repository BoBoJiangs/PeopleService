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
    private String access_token;//登录token
    private String account;
    private String password;
    private String userName;
    @Generated(hash = 1990117032)
    public User(String id, String access_token, String account, String password,
            String userName) {
        this.id = id;
        this.access_token = access_token;
        this.account = account;
        this.password = password;
        this.userName = userName;
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
    public String getAccess_token() {
        return this.access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
