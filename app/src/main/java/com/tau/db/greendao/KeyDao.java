package com.mofei.tau.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mofei.tau.transaction.Key.BigIntegerConverter;
import java.math.BigInteger;

import com.mofei.tau.transaction.Key;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "KEY".
*/
public class KeyDao extends AbstractDao<Key, Long> {

    public static final String TABLENAME = "KEY";

    /**
     * Properties of entity Key.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Pubkey = new Property(1, String.class, "pubkey", false, "PUBKEY");
        public final static Property Privkey = new Property(2, String.class, "privkey", false, "PRIVKEY");
        public final static Property Address = new Property(3, String.class, "address", false, "ADDRESS");
        public final static Property Utxo = new Property(4, String.class, "utxo", false, "UTXO");
        public final static Property Reward = new Property(5, String.class, "reward", false, "REWARD");
        public final static Property Balance = new Property(6, String.class, "balance", false, "BALANCE");
    }

    private final BigIntegerConverter utxoConverter = new BigIntegerConverter();
    private final BigIntegerConverter rewardConverter = new BigIntegerConverter();
    private final BigIntegerConverter balanceConverter = new BigIntegerConverter();

    public KeyDao(DaoConfig config) {
        super(config);
    }
    
    public KeyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"KEY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PUBKEY\" TEXT," + // 1: pubkey
                "\"PRIVKEY\" TEXT," + // 2: privkey
                "\"ADDRESS\" TEXT," + // 3: address
                "\"UTXO\" TEXT," + // 4: utxo
                "\"REWARD\" TEXT," + // 5: reward
                "\"BALANCE\" TEXT);"); // 6: balance
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"KEY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Key entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String pubkey = entity.getPubkey();
        if (pubkey != null) {
            stmt.bindString(2, pubkey);
        }
 
        String privkey = entity.getPrivkey();
        if (privkey != null) {
            stmt.bindString(3, privkey);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
 
        BigInteger utxo = entity.getUtxo();
        if (utxo != null) {
            stmt.bindString(5, utxoConverter.convertToDatabaseValue(utxo));
        }
 
        BigInteger reward = entity.getReward();
        if (reward != null) {
            stmt.bindString(6, rewardConverter.convertToDatabaseValue(reward));
        }
 
        BigInteger balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(7, balanceConverter.convertToDatabaseValue(balance));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Key entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String pubkey = entity.getPubkey();
        if (pubkey != null) {
            stmt.bindString(2, pubkey);
        }
 
        String privkey = entity.getPrivkey();
        if (privkey != null) {
            stmt.bindString(3, privkey);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
 
        BigInteger utxo = entity.getUtxo();
        if (utxo != null) {
            stmt.bindString(5, utxoConverter.convertToDatabaseValue(utxo));
        }
 
        BigInteger reward = entity.getReward();
        if (reward != null) {
            stmt.bindString(6, rewardConverter.convertToDatabaseValue(reward));
        }
 
        BigInteger balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(7, balanceConverter.convertToDatabaseValue(balance));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Key readEntity(Cursor cursor, int offset) {
        Key entity = new Key( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // pubkey
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // privkey
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // address
            cursor.isNull(offset + 4) ? null : utxoConverter.convertToEntityProperty(cursor.getString(offset + 4)), // utxo
            cursor.isNull(offset + 5) ? null : rewardConverter.convertToEntityProperty(cursor.getString(offset + 5)), // reward
            cursor.isNull(offset + 6) ? null : balanceConverter.convertToEntityProperty(cursor.getString(offset + 6)) // balance
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Key entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPubkey(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPrivkey(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAddress(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUtxo(cursor.isNull(offset + 4) ? null : utxoConverter.convertToEntityProperty(cursor.getString(offset + 4)));
        entity.setReward(cursor.isNull(offset + 5) ? null : rewardConverter.convertToEntityProperty(cursor.getString(offset + 5)));
        entity.setBalance(cursor.isNull(offset + 6) ? null : balanceConverter.convertToEntityProperty(cursor.getString(offset + 6)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Key entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Key entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Key entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
