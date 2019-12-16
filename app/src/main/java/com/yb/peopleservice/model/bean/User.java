package com.yb.peopleservice.model.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * 用户信息
 */
@Entity
public class User {
    @Id
    private String account;
    private String access_token;//登录token
    private String password;//用户密码
    private String userName;
    private String quickCode;//快捷登录获取的凭证
    @Convert(converter = StringConverter.class, columnType = String.class)
    private List<String> accountType;//功能列表的URL(暂时用于编辑机组信息时是否显示控制器的选项)





    @Generated(hash = 739293463)
    public User(String account, String access_token, String password, String userName,
            String quickCode, List<String> accountType) {
        this.account = account;
        this.access_token = access_token;
        this.password = password;
        this.userName = userName;
        this.quickCode = quickCode;
        this.accountType = accountType;
    }

    @Generated(hash = 586692638)
    public User() {
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

    public String getQuickCode() {
        return this.quickCode;
    }

    public void setQuickCode(String quickCode) {
        this.quickCode = quickCode;
    }

    public List<String> getAccountType() {
        return this.accountType;
    }

    public void setAccountType(List<String> accountType) {
        this.accountType = accountType;
    }

    public static class StringConverter implements PropertyConverter<List<String>, String> {

        @Override
        public List<String> convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            } else {
                List<String> list = Arrays.asList(databaseValue.split(","));
                return list;
            }
        }

        @Override
        public String convertToDatabaseValue(List<String> entityProperty) {
            if (entityProperty == null) {
                return null;
            } else {
                StringBuilder sb = new StringBuilder();
                for (String link : entityProperty) {
                    sb.append(link);
                    sb.append(",");
                }
                return sb.toString();
            }
        }
    }
}
