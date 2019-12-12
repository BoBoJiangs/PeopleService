package cn.sts.flower.model.database.helper;


import android.accounts.Account;

import com.yb.peopleservice.app.MyApplication;
import com.yb.peopleservice.model.database.dao.DaoMaster;
import com.yb.peopleservice.model.database.dao.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.ArrayList;
import java.util.List;

import cn.sts.base.util.Logs;


/**
 * 数据库操作类
 * Created by weilin on 17/8/29.
 */
public class DBHelper<T> {

    private static final String TAG = "DBHelper";

    private final static String DB_NAME = "service_db";

    public static DaoMaster.DevOpenHelper openHelper;
    private static DaoSession readDaoSession;
    private static DaoSession writeDaoSession;

    public DBHelper() {
        if (openHelper == null) {
            creteDb();
        }
//
//        openHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(), dbName, null);
//        openHelper = getInstance(dbName);

//        QueryBuilder.LOG_SQL = true;
//        QueryBuilder.LOG_VALUES = true;
    }

    public static void creteDb() {
        readDaoSession = null;
        writeDaoSession = null;
        openHelper = new DaoMaster.DevOpenHelper(MyApplication.getAppContext(), DB_NAME, null);

    }

    public static void creteDBHelper(Long id){
        String dbName = id + DB_NAME;
        readDaoSession = null;
        writeDaoSession = null;
        openHelper = new DaoMaster.DevOpenHelper(MyApplication.getAppContext(), dbName, null);
    }


    /**
     * 获取可读数据库Session连接
     */
    public DaoSession getReadDaoSession() {
        if (readDaoSession == null && openHelper != null) {
            readDaoSession = new DaoMaster(openHelper.getReadableDatabase()).newSession(IdentityScopeType.None);
        }
        return readDaoSession;
    }

    /**
     * 获取可写数据库Session连接
     */
    public DaoSession getWriteDaoSession() {
        if (writeDaoSession == null && openHelper != null) {
            writeDaoSession = new DaoMaster(openHelper.getWritableDatabase()).newSession(IdentityScopeType.None);
        }
        return writeDaoSession;
    }

    /**
     * 插入一条数据,主键存在就是替换
     *
     * @param obj 数据实体
     */
    @SuppressWarnings("unchecked")
    public boolean insertOrReplaceData(T obj) {
        if (obj != null) {
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(obj.getClass());
            long rowIndex = dao.insertOrReplace(obj);
            if (rowIndex > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 插入一条数据,
     *
     * @param obj 数据实体
     */
    @SuppressWarnings("unchecked")
    public boolean insertData(T obj) {
        if (obj != null) {
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(obj.getClass());
            long rowIndex = dao.insert(obj);
            if (rowIndex > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 批量插入数据,主键存在就是替换
     *
     * @param objectList 数据实体集合
     */
    @SuppressWarnings("unchecked")
    public boolean insertOrReplaceData(List<T> objectList) {
        if (objectList != null && objectList.size() > 0) {
            Object obj = objectList.get(0);
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(obj.getClass());
            dao.insertOrReplaceInTx(objectList);
            return true;

        }
        return false;
    }

    /**
     * 批量插入数据,主键存在就是替换
     *
     * @param objectList 数据实体集合
     */
    @SuppressWarnings("unchecked")
    public boolean insertData(List<T> objectList) {
        if (objectList != null && objectList.size() > 0) {
            Object obj = objectList.get(0);
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(obj.getClass());
            dao.insertInTx(objectList);
            return true;

        }
        return false;
    }

    /**
     * 更新数据,根据数据id
     *
     * @param obj 数据实体
     */
    @SuppressWarnings("unchecked")
    public boolean updateData(T obj) {
        if (obj != null) {
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(obj.getClass());
            dao.update(obj);
            return true;
        }

        return false;
    }

    /**
     * 更新数据,根据数据id
     *
     * @param objList 数据实体
     */
    @SuppressWarnings("unchecked")
    public boolean updateData(List<T> objList) {
        if (objList != null && objList.size() > 0) {
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(objList.get(0).getClass());
            dao.updateInTx(objList);
            return true;
        }

        return false;
    }


    /**
     * 删除数据,根据数据id
     *
     * @param obj 数据实体
     */
    @SuppressWarnings("unchecked")
    public boolean deleteData(T obj) {
        if (obj != null) {
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(obj.getClass());
            dao.delete(obj);
            return true;
        }

        return false;
    }

    /**
     * 删除对象所对应的表的所有数据
     *
     * @param classz 数据实体
     */
    @SuppressWarnings("unchecked")
    public boolean deleteAllData(Class<T> classz) {
        if (classz != null) {
            AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getWriteDaoSession().getDao(classz);
            dao.deleteAll();
            return true;
        }
        return false;
    }


    /**
     * 查询对象所对应的表的所有数据
     *
     * @param classz 数据实体
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryAllData(Class<T> classz) {
        try {
            if (classz != null) {
                return (List<T>) getReadDaoSession().getDao(classz).queryBuilder().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
