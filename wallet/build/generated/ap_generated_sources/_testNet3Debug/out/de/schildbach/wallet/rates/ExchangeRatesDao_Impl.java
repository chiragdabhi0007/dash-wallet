package de.schildbach.wallet.rates;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExchangeRatesDao_Impl implements ExchangeRatesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ExchangeRate> __insertionAdapterOfExchangeRate;

  public ExchangeRatesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExchangeRate = new EntityInsertionAdapter<ExchangeRate>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `exchange_rates` (`currencyCode`,`rate`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExchangeRate value) {
        if (value.getCurrencyCode() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getCurrencyCode());
        }
        if (value.getRate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRate());
        }
      }
    };
  }

  @Override
  public void insertAll(final List<ExchangeRate> exchangeRates) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExchangeRate.insert(exchangeRates);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<ExchangeRate>> getAll() {
    final String _sql = "SELECT * FROM exchange_rates ORDER BY currencyCode";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"exchange_rates"}, false, new Callable<List<ExchangeRate>>() {
      @Override
      public List<ExchangeRate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currencyCode");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final List<ExchangeRate> _result = new ArrayList<ExchangeRate>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ExchangeRate _item;
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final String _tmpRate;
            _tmpRate = _cursor.getString(_cursorIndexOfRate);
            _item = new ExchangeRate(_tmpCurrencyCode,_tmpRate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<ExchangeRate> getRate(final String currencyCode) {
    final String _sql = "SELECT * FROM exchange_rates WHERE currencyCode = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (currencyCode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, currencyCode);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"exchange_rates"}, false, new Callable<ExchangeRate>() {
      @Override
      public ExchangeRate call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currencyCode");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final ExchangeRate _result;
          if(_cursor.moveToFirst()) {
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final String _tmpRate;
            _tmpRate = _cursor.getString(_cursorIndexOfRate);
            _result = new ExchangeRate(_tmpCurrencyCode,_tmpRate);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public ExchangeRate getRateSync(final String currencyCode) {
    final String _sql = "SELECT * FROM exchange_rates WHERE currencyCode = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (currencyCode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, currencyCode);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currencyCode");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final ExchangeRate _result;
      if(_cursor.moveToFirst()) {
        final String _tmpCurrencyCode;
        _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
        final String _tmpRate;
        _tmpRate = _cursor.getString(_cursorIndexOfRate);
        _result = new ExchangeRate(_tmpCurrencyCode,_tmpRate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ExchangeRate>> searchRates(final String currencyCode) {
    final String _sql = "SELECT * FROM exchange_rates WHERE currencyCode LIKE ? || '%' ORDER BY currencyCode";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (currencyCode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, currencyCode);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"exchange_rates"}, false, new Callable<List<ExchangeRate>>() {
      @Override
      public List<ExchangeRate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currencyCode");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final List<ExchangeRate> _result = new ArrayList<ExchangeRate>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ExchangeRate _item;
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final String _tmpRate;
            _tmpRate = _cursor.getString(_cursorIndexOfRate);
            _item = new ExchangeRate(_tmpCurrencyCode,_tmpRate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int count() {
    final String _sql = "SELECT count(*) FROM exchange_rates";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
