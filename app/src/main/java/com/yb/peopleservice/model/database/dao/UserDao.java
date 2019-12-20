package com.yb.peopleservice.model.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yb.peopleservice.model.database.bean.User.StringConverter;
import java.util.List;

import com.yb.peopleservice.model.database.bean.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, String> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Account = new Property(0, String.class, "account", true, "ACCOUNT");
        public final static Property Access_token = new Property(1, String.class, "access_token", false, "ACCESS_TOKEN");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property UserName = new Property(3, String.class, "userName", false, "USER_NAME");
        public final static Property QuickCode = new Property(4, String.class, "quickCode", false, "QUICK_CODE");
        public final static Property TokenType = new Property(5, String.class, "tokenType", false, "TOKEN_TYPE");
        public final static Property AccountType = new Property(6, String.class, "accountType", false, "ACCOUNT_TYPE");
    }

    private final StringConverter accountTypeConverter = new StringConverter();

    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"ACCOUNT\" TEXT PRIMARY KEY NOT NULL ," + // 0: account
                "\"ACCESS_TOKEN\" TEXT," + // 1: access_token
                "\"PASSWORD\" TEXT," + // 2: password
                "\"USER_NAME\" TEXT," + // 3: userName
                "\"QUICK_CODE\" TEXT," + // 4: quickCode
                "\"TOKEN_TYPE\" TEXT," + // 5: tokenType
                "\"ACCOUNT_TYPE\" TEXT);"); // 6: accountType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(1, account);
        }
 
        String access_token = entity.getAccess_token();
        if (access_token != null) {
            stmt.bindString(2, access_token);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(4, userName);
        }
 
        String quickCode = entity.getQuickCode();
        if (quickCode != null) {
            stmt.bindString(5, quickCode);
        }
 
        String tokenType = entity.getTokenType();
        if (tokenType != null) {
            stmt.bindString(6, tokenType);
        }
 
        List accountType = entity.getAccountType();
        if (accountType != null) {
            stmt.bindString(7, accountTypeConverter.convertToDatabaseValue(accountType));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(1, account);
        }
 
        String access_token = entity.getAccess_token();
        if (access_token != null) {
            stmt.bindString(2, access_token);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(4, userName);
        }
 
        String quickCode = entity.getQuickCode();
        if (quickCode != null) {
            stmt.bindString(5, quickCode);
        }
 
        String tokenType = entity.getTokenType();
        if (tokenType != null) {
            stmt.bindString(6, tokenType);
        }
 
        List accountType = entity.getAccountType();
        if (accountType != null) {
            stmt.bindString(7, accountTypeConverter.convertToDatabaseValue(accountType));
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // account
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // access_token
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // quickCode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // tokenType
            cursor.isNull(offset + 6) ? null : accountTypeConverter.convertToEntityProperty(cursor.getString(offset + 6)) // accountType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setAccount(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAccess_token(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setQuickCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTokenType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAccountType(cursor.isNull(offset + 6) ? null : accountTypeConverter.convertToEntityProperty(cursor.getString(offset + 6)));
     }
    
    @Override
    protected final String updateKeyAfterInsert(User entity, long rowId) {
        return entity.getAccount();
    }
    
    @Override
    public String getKey(User entity) {
        if(entity != null) {
            return entity.getAccount();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getAccount() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
