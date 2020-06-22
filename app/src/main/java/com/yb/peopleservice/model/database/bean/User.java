package com.yb.peopleservice.model.database.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.DaoException;

import com.google.gson.annotations.SerializedName;
import com.yb.peopleservice.model.database.dao.DaoSession;
import com.yb.peopleservice.model.database.dao.UserInfoBeanDao;
import com.yb.peopleservice.model.database.dao.UserDao;

/**
 * 用户登录的用户信息
 */
@Entity
public class User implements Parcelable {
    @Id
    private String account;
    private String access_token;//登录token
    private String password;//用户密码
    private String code;//第三方或者快捷登录返回的临时密码
    private String userName;
    private String quickCode;//快捷登录获取的凭证
    @SerializedName("token_type")
    private String tokenType;
    private String type;
    @Convert(converter = StringConverter.class, columnType = String.class)
    @SerializedName("scope")
    private List<String> accountType;

    private String userId;

    @ToOne(joinProperty = "userId")
    private UserInfoBean infoBean;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    @Generated(hash = 1039837059)
    private transient String infoBean__resolvedKey;






    public User() {
    }





    @Generated(hash = 510797623)
    public User(String account, String access_token, String password, String code,
            String userName, String quickCode, String tokenType, String type,
            List<String> accountType, String userId) {
        this.account = account;
        this.access_token = access_token;
        this.password = password;
        this.code = code;
        this.userName = userName;
        this.quickCode = quickCode;
        this.tokenType = tokenType;
        this.type = type;
        this.accountType = accountType;
        this.userId = userId;
    }





    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (accountType == null) {
            return new ArrayList<>();
        }
        return accountType;
    }

    public void setAccountType(List<String> accountType) {
        this.accountType = accountType;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }





    public String getCode() {
        return this.code;
    }





    public void setCode(String code) {
        this.code = code;
    }





    /** To-one relationship, resolved on first access. */
    @Generated(hash = 476370479)
    public UserInfoBean getInfoBean() {
        String __key = this.userId;
        if (infoBean__resolvedKey == null || infoBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserInfoBeanDao targetDao = daoSession.getUserInfoBeanDao();
            UserInfoBean infoBeanNew = targetDao.load(__key);
            synchronized (this) {
                infoBean = infoBeanNew;
                infoBean__resolvedKey = __key;
            }
        }
        return infoBean;
    }





    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1925003790)
    public void setInfoBean(UserInfoBean infoBean) {
        synchronized (this) {
            this.infoBean = infoBean;
            userId = infoBean == null ? null : infoBean.getId();
            infoBean__resolvedKey = userId;
        }
    }





    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }





    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }





    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }





    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.access_token);
        dest.writeString(this.password);
        dest.writeString(this.code);
        dest.writeString(this.userName);
        dest.writeString(this.quickCode);
        dest.writeString(this.tokenType);
        dest.writeString(this.type);
        dest.writeStringList(this.accountType);
        dest.writeString(this.userId);
        dest.writeParcelable(this.infoBean, flags);
    }

    protected User(Parcel in) {
        this.account = in.readString();
        this.access_token = in.readString();
        this.password = in.readString();
        this.code = in.readString();
        this.userName = in.readString();
        this.quickCode = in.readString();
        this.tokenType = in.readString();
        this.type = in.readString();
        this.accountType = in.createStringArrayList();
        this.userId = in.readString();
        this.infoBean = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
