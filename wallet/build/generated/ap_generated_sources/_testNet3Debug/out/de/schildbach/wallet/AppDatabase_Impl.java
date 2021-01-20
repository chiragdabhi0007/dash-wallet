package de.schildbach.wallet;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import de.schildbach.wallet.data.BlockchainStateDao;
import de.schildbach.wallet.data.BlockchainStateDao_Impl;
import de.schildbach.wallet.rates.ExchangeRatesDao;
import de.schildbach.wallet.rates.ExchangeRatesDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ExchangeRatesDao _exchangeRatesDao;

  private volatile BlockchainStateDao _blockchainStateDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `exchange_rates` (`currencyCode` TEXT NOT NULL, `rate` TEXT, PRIMARY KEY(`currencyCode`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `blockchain_state` (`id` INTEGER NOT NULL, `bestChainDate` INTEGER, `bestChainHeight` INTEGER NOT NULL, `replaying` INTEGER NOT NULL, `impediments` TEXT NOT NULL, `chainlockHeight` INTEGER NOT NULL, `mnlistHeight` INTEGER NOT NULL, `percentageSync` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '77eb849e83b9d0cc8bb55a8b6f0b9d19')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `exchange_rates`");
        _db.execSQL("DROP TABLE IF EXISTS `blockchain_state`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsExchangeRates = new HashMap<String, TableInfo.Column>(2);
        _columnsExchangeRates.put("currencyCode", new TableInfo.Column("currencyCode", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExchangeRates.put("rate", new TableInfo.Column("rate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExchangeRates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExchangeRates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExchangeRates = new TableInfo("exchange_rates", _columnsExchangeRates, _foreignKeysExchangeRates, _indicesExchangeRates);
        final TableInfo _existingExchangeRates = TableInfo.read(_db, "exchange_rates");
        if (! _infoExchangeRates.equals(_existingExchangeRates)) {
          return new RoomOpenHelper.ValidationResult(false, "exchange_rates(de.schildbach.wallet.rates.ExchangeRate).\n"
                  + " Expected:\n" + _infoExchangeRates + "\n"
                  + " Found:\n" + _existingExchangeRates);
        }
        final HashMap<String, TableInfo.Column> _columnsBlockchainState = new HashMap<String, TableInfo.Column>(8);
        _columnsBlockchainState.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("bestChainDate", new TableInfo.Column("bestChainDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("bestChainHeight", new TableInfo.Column("bestChainHeight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("replaying", new TableInfo.Column("replaying", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("impediments", new TableInfo.Column("impediments", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("chainlockHeight", new TableInfo.Column("chainlockHeight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("mnlistHeight", new TableInfo.Column("mnlistHeight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockchainState.put("percentageSync", new TableInfo.Column("percentageSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlockchainState = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlockchainState = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBlockchainState = new TableInfo("blockchain_state", _columnsBlockchainState, _foreignKeysBlockchainState, _indicesBlockchainState);
        final TableInfo _existingBlockchainState = TableInfo.read(_db, "blockchain_state");
        if (! _infoBlockchainState.equals(_existingBlockchainState)) {
          return new RoomOpenHelper.ValidationResult(false, "blockchain_state(de.schildbach.wallet.data.BlockchainState).\n"
                  + " Expected:\n" + _infoBlockchainState + "\n"
                  + " Found:\n" + _existingBlockchainState);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "77eb849e83b9d0cc8bb55a8b6f0b9d19", "c5c0471201b98863bcd4487bb7f08918");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "exchange_rates","blockchain_state");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `exchange_rates`");
      _db.execSQL("DELETE FROM `blockchain_state`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ExchangeRatesDao exchangeRatesDao() {
    if (_exchangeRatesDao != null) {
      return _exchangeRatesDao;
    } else {
      synchronized(this) {
        if(_exchangeRatesDao == null) {
          _exchangeRatesDao = new ExchangeRatesDao_Impl(this);
        }
        return _exchangeRatesDao;
      }
    }
  }

  @Override
  public BlockchainStateDao blockchainStateDao() {
    if (_blockchainStateDao != null) {
      return _blockchainStateDao;
    } else {
      synchronized(this) {
        if(_blockchainStateDao == null) {
          _blockchainStateDao = new BlockchainStateDao_Impl(this);
        }
        return _blockchainStateDao;
      }
    }
  }
}
