package com.bwie.test.chatdemo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bwie.test.chatdemo.bean.DataBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DATA_BEAN".
*/
public class DataBeanDao extends AbstractDao<DataBean, Long> {

    public static final String TABLENAME = "DATA_BEAN";

    /**
     * Properties of entity DataBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Area = new Property(1, String.class, "area", false, "AREA");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property Lasttime = new Property(3, long.class, "lasttime", false, "LASTTIME");
        public final static Property Createtime = new Property(4, long.class, "createtime", false, "CREATETIME");
        public final static Property Gender = new Property(5, String.class, "gender", false, "GENDER");
        public final static Property Lng = new Property(6, double.class, "lng", false, "LNG");
        public final static Property Phone = new Property(7, String.class, "phone", false, "PHONE");
        public final static Property Introduce = new Property(8, String.class, "introduce", false, "INTRODUCE");
        public final static Property ImagePath = new Property(9, String.class, "imagePath", false, "IMAGE_PATH");
        public final static Property Nickname = new Property(10, String.class, "nickname", false, "NICKNAME");
        public final static Property UserId = new Property(11, int.class, "userId", false, "USER_ID");
        public final static Property Lat = new Property(12, double.class, "lat", false, "LAT");
    };


    public DataBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DataBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DATA_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"AREA\" TEXT," + // 1: area
                "\"PASSWORD\" TEXT," + // 2: password
                "\"LASTTIME\" INTEGER NOT NULL ," + // 3: lasttime
                "\"CREATETIME\" INTEGER NOT NULL ," + // 4: createtime
                "\"GENDER\" TEXT," + // 5: gender
                "\"LNG\" REAL NOT NULL ," + // 6: lng
                "\"PHONE\" TEXT," + // 7: phone
                "\"INTRODUCE\" TEXT," + // 8: introduce
                "\"IMAGE_PATH\" TEXT," + // 9: imagePath
                "\"NICKNAME\" TEXT," + // 10: nickname
                "\"USER_ID\" INTEGER NOT NULL ," + // 11: userId
                "\"LAT\" REAL NOT NULL );"); // 12: lat
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DATA_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DataBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(2, area);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
        stmt.bindLong(4, entity.getLasttime());
        stmt.bindLong(5, entity.getCreatetime());
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
        stmt.bindDouble(7, entity.getLng());
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
 
        String introduce = entity.getIntroduce();
        if (introduce != null) {
            stmt.bindString(9, introduce);
        }
 
        String imagePath = entity.getImagePath();
        if (imagePath != null) {
            stmt.bindString(10, imagePath);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(11, nickname);
        }
        stmt.bindLong(12, entity.getUserId());
        stmt.bindDouble(13, entity.getLat());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DataBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(2, area);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
        stmt.bindLong(4, entity.getLasttime());
        stmt.bindLong(5, entity.getCreatetime());
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
        stmt.bindDouble(7, entity.getLng());
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
 
        String introduce = entity.getIntroduce();
        if (introduce != null) {
            stmt.bindString(9, introduce);
        }
 
        String imagePath = entity.getImagePath();
        if (imagePath != null) {
            stmt.bindString(10, imagePath);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(11, nickname);
        }
        stmt.bindLong(12, entity.getUserId());
        stmt.bindDouble(13, entity.getLat());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DataBean readEntity(Cursor cursor, int offset) {
        DataBean entity = new DataBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // area
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.getLong(offset + 3), // lasttime
            cursor.getLong(offset + 4), // createtime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gender
            cursor.getDouble(offset + 6), // lng
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // phone
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // introduce
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // imagePath
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // nickname
            cursor.getInt(offset + 11), // userId
            cursor.getDouble(offset + 12) // lat
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DataBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArea(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLasttime(cursor.getLong(offset + 3));
        entity.setCreatetime(cursor.getLong(offset + 4));
        entity.setGender(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLng(cursor.getDouble(offset + 6));
        entity.setPhone(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIntroduce(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setImagePath(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setNickname(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUserId(cursor.getInt(offset + 11));
        entity.setLat(cursor.getDouble(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DataBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DataBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}