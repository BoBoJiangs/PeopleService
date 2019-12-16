package com.yb.peopleservice.model.database.manager;

import com.yb.peopleservice.model.bean.User;

import org.greenrobot.greendao.AbstractDao;

public class UserManager extends BaseBeanManager<User, Long> {
    public UserManager(AbstractDao dao) {
        super(dao);
    }
}
