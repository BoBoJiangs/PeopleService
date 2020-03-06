package com.yb.peopleservice.model.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yb.peopleservice.model.database.bean.ServiceInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SERVICE_INFO".
*/
public class ServiceInfoDao extends AbstractDao<ServiceInfo, String> {

    public static final String TABLENAME = "SERVICE_INFO";

    /**
     * Properties of entity ServiceInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property HeadImg = new Property(1, String.class, "headImg", false, "HEAD_IMG");
        public final static Property IdCardImgBack = new Property(2, String.class, "idCardImgBack", false, "ID_CARD_IMG_BACK");
        public final static Property IdCardImgFront = new Property(3, String.class, "idCardImgFront", false, "ID_CARD_IMG_FRONT");
        public final static Property IdCardNumber = new Property(4, String.class, "idCardNumber", false, "ID_CARD_NUMBER");
        public final static Property Introduction = new Property(5, String.class, "introduction", false, "INTRODUCTION");
        public final static Property Message = new Property(6, String.class, "message", false, "MESSAGE");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
        public final static Property Nickname = new Property(8, String.class, "nickname", false, "NICKNAME");
        public final static Property Phone = new Property(9, String.class, "phone", false, "PHONE");
        public final static Property Status = new Property(10, int.class, "status", false, "STATUS");
    }


    public ServiceInfoDao(DaoConfig config) {
        super(config);
    }
    
    public ServiceInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SERVICE_INFO\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"HEAD_IMG\" TEXT," + // 1: headImg
                "\"ID_CARD_IMG_BACK\" TEXT," + // 2: idCardImgBack
                "\"ID_CARD_IMG_FRONT\" TEXT," + // 3: idCardImgFront
                "\"ID_CARD_NUMBER\" TEXT," + // 4: idCardNumber
                "\"INTRODUCTION\" TEXT," + // 5: introduction
                "\"MESSAGE\" TEXT," + // 6: message
                "\"NAME\" TEXT," + // 7: name
                "\"NICKNAME\" TEXT," + // 8: nickname
                "\"PHONE\" TEXT," + // 9: phone
                "\"STATUS\" INTEGER NOT NULL );"); // 10: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SERVICE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ServiceInfo entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String headImg = entity.getHeadImg();
        if (headImg != null) {
            stmt.bindString(2, headImg);
        }
 
        String idCardImgBack = entity.getIdCardImgBack();
        if (idCardImgBack != null) {
            stmt.bindString(3, idCardImgBack);
        }
 
        String idCardImgFront = entity.getIdCardImgFront();
        if (idCardImgFront != null) {
            stmt.bindString(4, idCardImgFront);
        }
 
        String idCardNumber = entity.getIdCardNumber();
        if (idCardNumber != null) {
            stmt.bindString(5, idCardNumber);
        }
 
        String introduction = entity.getIntroduction();
        if (introduction != null) {
            stmt.bindString(6, introduction);
        }
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(7, message);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(9, nickname);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(10, phone);
        }
        stmt.bindLong(11, entity.getStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ServiceInfo entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String headImg = entity.getHeadImg();
        if (headImg != null) {
            stmt.bindString(2, headImg);
        }
 
        String idCardImgBack = entity.getIdCardImgBack();
        if (idCardImgBack != null) {
            stmt.bindString(3, idCardImgBack);
        }
 
        String idCardImgFront = entity.getIdCardImgFront();
        if (idCardImgFront != null) {
            stmt.bindString(4, idCardImgFront);
        }
 
        String idCardNumber = entity.getIdCardNumber();
        if (idCardNumber != null) {
            stmt.bindString(5, idCardNumber);
        }
 
        String introduction = entity.getIntroduction();
        if (introduction != null) {
            stmt.bindString(6, introduction);
        }
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(7, message);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(9, nickname);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(10, phone);
        }
        stmt.bindLong(11, entity.getStatus());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ServiceInfo readEntity(Cursor cursor, int offset) {
        ServiceInfo entity = new ServiceInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // headImg
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // idCardImgBack
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // idCardImgFront
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // idCardNumber
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // introduction
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // message
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // name
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // nickname
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // phone
            cursor.getInt(offset + 10) // status
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ServiceInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setHeadImg(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIdCardImgBack(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIdCardImgFront(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIdCardNumber(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIntroduction(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMessage(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setNickname(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPhone(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStatus(cursor.getInt(offset + 10));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ServiceInfo entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(ServiceInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ServiceInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
