package de.schildbach.wallet.data;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BlockchainStateDao_Impl extends BlockchainStateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BlockchainState> __insertionAdapterOfBlockchainState;

  private final RoomConverters __roomConverters = new RoomConverters();

  public BlockchainStateDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBlockchainState = new EntityInsertionAdapter<BlockchainState>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `blockchain_state` (`id`,`bestChainDate`,`bestChainHeight`,`replaying`,`impediments`,`chainlockHeight`,`mnlistHeight`,`percentageSync`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BlockchainState value) {
        stmt.bindLong(1, value.getId());
        final Long _tmp;
        _tmp = __roomConverters.dateToTimestamp(value.getBestChainDate());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        stmt.bindLong(3, value.getBestChainHeight());
        final int _tmp_1;
        _tmp_1 = value.getReplaying() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        final String _tmp_2;
        _tmp_2 = __roomConverters.toImpedimentsString(value.getImpediments());
        if (_tmp_2 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_2);
        }
        stmt.bindLong(6, value.getChainlockHeight());
        stmt.bindLong(7, value.getMnlistHeight());
        stmt.bindLong(8, value.getPercentageSync());
      }
    };
  }

  @Override
  public void insert(final BlockchainState arg0) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBlockchainState.insert(arg0);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<BlockchainState> load() {
    final String _sql = "SELECT * FROM blockchain_state LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"blockchain_state"}, false, new Callable<BlockchainState>() {
      @Override
      public BlockchainState call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBestChainDate = CursorUtil.getColumnIndexOrThrow(_cursor, "bestChainDate");
          final int _cursorIndexOfBestChainHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "bestChainHeight");
          final int _cursorIndexOfReplaying = CursorUtil.getColumnIndexOrThrow(_cursor, "replaying");
          final int _cursorIndexOfImpediments = CursorUtil.getColumnIndexOrThrow(_cursor, "impediments");
          final int _cursorIndexOfChainlockHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "chainlockHeight");
          final int _cursorIndexOfMnlistHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "mnlistHeight");
          final int _cursorIndexOfPercentageSync = CursorUtil.getColumnIndexOrThrow(_cursor, "percentageSync");
          final BlockchainState _result;
          if(_cursor.moveToFirst()) {
            final Date _tmpBestChainDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfBestChainDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfBestChainDate);
            }
            _tmpBestChainDate = __roomConverters.fromTimestamp(_tmp);
            final int _tmpBestChainHeight;
            _tmpBestChainHeight = _cursor.getInt(_cursorIndexOfBestChainHeight);
            final boolean _tmpReplaying;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReplaying);
            _tmpReplaying = _tmp_1 != 0;
            final Set<BlockchainState.Impediment> _tmpImpediments;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImpediments);
            _tmpImpediments = __roomConverters.fromImpedimentsEnumSet(_tmp_2);
            final int _tmpChainlockHeight;
            _tmpChainlockHeight = _cursor.getInt(_cursorIndexOfChainlockHeight);
            final int _tmpMnlistHeight;
            _tmpMnlistHeight = _cursor.getInt(_cursorIndexOfMnlistHeight);
            final int _tmpPercentageSync;
            _tmpPercentageSync = _cursor.getInt(_cursorIndexOfPercentageSync);
            _result = new BlockchainState(_tmpBestChainDate,_tmpBestChainHeight,_tmpReplaying,_tmpImpediments,_tmpChainlockHeight,_tmpMnlistHeight,_tmpPercentageSync);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
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
  public BlockchainState loadSync() {
    final String _sql = "SELECT * FROM blockchain_state LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBestChainDate = CursorUtil.getColumnIndexOrThrow(_cursor, "bestChainDate");
      final int _cursorIndexOfBestChainHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "bestChainHeight");
      final int _cursorIndexOfReplaying = CursorUtil.getColumnIndexOrThrow(_cursor, "replaying");
      final int _cursorIndexOfImpediments = CursorUtil.getColumnIndexOrThrow(_cursor, "impediments");
      final int _cursorIndexOfChainlockHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "chainlockHeight");
      final int _cursorIndexOfMnlistHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "mnlistHeight");
      final int _cursorIndexOfPercentageSync = CursorUtil.getColumnIndexOrThrow(_cursor, "percentageSync");
      final BlockchainState _result;
      if(_cursor.moveToFirst()) {
        final Date _tmpBestChainDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfBestChainDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfBestChainDate);
        }
        _tmpBestChainDate = __roomConverters.fromTimestamp(_tmp);
        final int _tmpBestChainHeight;
        _tmpBestChainHeight = _cursor.getInt(_cursorIndexOfBestChainHeight);
        final boolean _tmpReplaying;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfReplaying);
        _tmpReplaying = _tmp_1 != 0;
        final Set<BlockchainState.Impediment> _tmpImpediments;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfImpediments);
        _tmpImpediments = __roomConverters.fromImpedimentsEnumSet(_tmp_2);
        final int _tmpChainlockHeight;
        _tmpChainlockHeight = _cursor.getInt(_cursorIndexOfChainlockHeight);
        final int _tmpMnlistHeight;
        _tmpMnlistHeight = _cursor.getInt(_cursorIndexOfMnlistHeight);
        final int _tmpPercentageSync;
        _tmpPercentageSync = _cursor.getInt(_cursorIndexOfPercentageSync);
        _result = new BlockchainState(_tmpBestChainDate,_tmpBestChainHeight,_tmpReplaying,_tmpImpediments,_tmpChainlockHeight,_tmpMnlistHeight,_tmpPercentageSync);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
