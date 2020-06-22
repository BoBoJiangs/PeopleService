package com.yb.peopleservice.model.database.manager;

import com.yb.peopleservice.model.database.bean.RecordBean;
import com.yb.peopleservice.model.database.bean.UserInfoBean;
import com.yb.peopleservice.model.database.dao.RecordBeanDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

public class RecordManager extends BaseBeanManager<RecordBean, Long> {
    public RecordManager(AbstractDao dao) {
        super(dao);
    }

    public List<RecordBean> queryRecordData() {
        List<RecordBean> dataList = queryBuilder()
                .where(RecordBeanDao.Properties.FileUri.notEq(""))
                .where(RecordBeanDao.Properties.FileUri.isNotNull())
                .list();
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        return dataList;
    }

    /**
     * 删除数据库已上传成功的数据
     * @return
     */
    public void deleteRecordData() {
        queryBuilder()
                .where(RecordBeanDao.Properties.FileUri.notEq(""))
                .where(RecordBeanDao.Properties.FileUri.isNotNull())
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }
}
