package com.yb.peopleservice.model.database.manager;

import com.yb.peopleservice.model.database.bean.User;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public class UserManager extends BaseBeanManager<User, Long> {
    public static User user;

    public UserManager(AbstractDao dao) {
        super(dao);
    }

    public User getUser() {
        List<User> userList = queryAll();
        if (userList != null && !userList.isEmpty()) {
            return userList.get(0);
        } else {
            return null;
        }
    }
}
