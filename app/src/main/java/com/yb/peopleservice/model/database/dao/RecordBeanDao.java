package com.yb.peopleservice.model.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yb.peopleservice.model.database.bean.RecordBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECORD_BEAN".
*/
public class RecordBeanDao extends AbstractDao<RecordBean, Long> {

    public static final String TABLENAME = "RECORD_BEAN";

    /**
     * Properties of entity RecordBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property RecordId = new Property(0, Long.class, "recordId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property FileUri = new Property(3, String.class, "fileUri", false, "FILE_URI");
        public final static Property OrderId = new Property(4, String.class, "orderId", false, "ORDER_ID");
        public final static Property SerialNumber = new Property(5, Integer.class, "serialNumber", false, "SERIAL_NUMBER");
        public final static Property Timestamp = new Property(6, String.class, "timestamp", false, "TIMESTAMP");
        public final static Property LocalUri = new Property(7, String.class, "localUri", false, "LOCAL_URI");
    }


    public RecordBeanDao(DaoConfig config) {
        super(config);
    }
    
    public RecordBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECORD_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: recordId
                "\"ID\" TEXT," + // 1: id
                "\"USER_ID\" TEXT," + // 2: userId
                "\"FILE_URI\" TEXT," + // 3: fileUri
                "\"ORDER_ID\" TEXT," + // 4: orderId
                "\"SERIAL_NUMBER\" INTEGER," + // 5: serialNumber
                "\"TIMESTAMP\" TEXT," + // 6: timestamp
                "\"LOCAL_URI\" TEXT);"); // 7: localUri
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECORD_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RecordBean entity) {
        stmt.clearBindings();
 
        Long recordId = entity.getRecordId();
        if (recordId != null) {
            stmt.bindLong(1, recordId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String fileUri = entity.getFileUri();
        if (fileUri != null) {
            stmt.bindString(4, fileUri);
        }
 
        String orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindString(5, orderId);
        }
 
        Integer serialNumber = entity.getSerialNumber();
        if (serialNumber != null) {
            stmt.bindLong(6, serialNumber);
        }
 
        String timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindString(7, timestamp);
        }
 
        String localUri = entity.getLocalUri();
        if (localUri != null) {
            stmt.bindString(8, localUri);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RecordBean entity) {
        stmt.clearBindings();
 
        Long recordId = entity.getRecordId();
        if (recordId != null) {
            stmt.bindLong(1, recordId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String fileUri = entity.getFileUri();
        if (fileUri != null) {
            stmt.bindString(4, fileUri);
        }
 
        String orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindString(5, orderId);
        }
 
        Integer serialNumber = entity.getSerialNumber();
        if (serialNumber != null) {
            stmt.bindLong(6, serialNumber);
        }
 
        String timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindString(7, timestamp);
        }
 
        String localUri = entity.getLocalUri();
        if (localUri != null) {
            stmt.bindString(8, localUri);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public RecordBean readEntity(Cursor cursor, int offset) {
        RecordBean entity = new RecordBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // recordId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fileUri
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // orderId
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // serialNumber
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // timestamp
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // localUri
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RecordBean entity, int offset) {
        entity.setRecordId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFileUri(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setOrderId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSerialNumber(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setTimestamp(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLocalUri(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RecordBean entity, long rowId) {
        entity.setRecordId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RecordBean entity) {
        if(entity != null) {
            return entity.getRecordId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RecordBean entity) {
        return entity.getRecordId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}