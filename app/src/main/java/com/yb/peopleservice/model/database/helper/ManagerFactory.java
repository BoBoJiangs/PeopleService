package com.yb.peopleservice.model.database.helper;

import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.model.database.manager.DaoManager;
import com.yb.peopleservice.model.database.manager.UserManager;

public class ManagerFactory {
    /**
     * 每一个BeanManager都管理着数据库中的一个表，我将这些管理者在ManagerFactory中进行统一管理
     */
    UserManager studentManager;


    private static ManagerFactory mInstance = null;

    /**
     * 获取DaoFactory的实例
     *
     * @return
     */
    public static ManagerFactory getInstance() {
        if (mInstance == null) {
            synchronized (ManagerFactory.class) {
                if (mInstance == null) {
                    mInstance = new ManagerFactory();
                }
            }
        }
        return mInstance;
    }

    public synchronized UserManager getUserManager() {
        if (studentManager == null){
            studentManager = new UserManager(DaoManager.getInstance(MyApplication.getAppContext()).getDaoSession().getUserDao());
        }
        return studentManager;
    }
}
