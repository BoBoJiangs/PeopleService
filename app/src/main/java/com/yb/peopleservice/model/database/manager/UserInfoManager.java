package com.yb.peopleservice.model.database.manager;

import com.yb.peopleservice.model.database.bean.User;
import com.yb.peopleservice.model.database.bean.UserInfoBean;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public class UserInfoManager extends BaseBeanManager<UserInfoBean, Long> {
    public UserInfoManager(AbstractDao dao) {
        super(dao);
    }

}
