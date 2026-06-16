package com.example.workingbuddy.data;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.workingbuddy.model.WorkEntry;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkEntryDao_Impl implements WorkEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkEntry> __insertionAdapterOfWorkEntry;

  private final EntityDeletionOrUpdateAdapter<WorkEntry> __deletionAdapterOfWorkEntry;

  private final EntityDeletionOrUpdateAdapter<WorkEntry> __updateAdapterOfWorkEntry;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public WorkEntryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkEntry = new EntityInsertionAdapter<WorkEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `work_entries` (`id`,`serviceType`,`customServiceName`,`customerName`,`notes`,`amount`,`isPaid`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkEntry value) {
        stmt.bindLong(1, value.getId());
        if (value.getServiceType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getServiceType());
        }
        if (value.getCustomServiceName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCustomServiceName());
        }
        if (value.getCustomerName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCustomerName());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNotes());
        }
        stmt.bindDouble(6, value.getAmount());
        final int _tmp = value.isPaid() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        stmt.bindLong(8, value.getTimestamp());
      }
    };
    this.__deletionAdapterOfWorkEntry = new EntityDeletionOrUpdateAdapter<WorkEntry>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `work_entries` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkEntry value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfWorkEntry = new EntityDeletionOrUpdateAdapter<WorkEntry>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `work_entries` SET `id` = ?,`serviceType` = ?,`customServiceName` = ?,`customerName` = ?,`notes` = ?,`amount` = ?,`isPaid` = ?,`timestamp` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkEntry value) {
        stmt.bindLong(1, value.getId());
        if (value.getServiceType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getServiceType());
        }
        if (value.getCustomServiceName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCustomServiceName());
        }
        if (value.getCustomerName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCustomerName());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNotes());
        }
        stmt.bindDouble(6, value.getAmount());
        final int _tmp = value.isPaid() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        stmt.bindLong(8, value.getTimestamp());
        stmt.bindLong(9, value.getId());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM work_entries";
        return _query;
      }
    };
  }

  @Override
  public Object insertEntry(final WorkEntry entry, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWorkEntry.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertAll(final List<WorkEntry> entries,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWorkEntry.insert(entries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteEntry(final WorkEntry entry, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateEntry(final WorkEntry entry, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<WorkEntry>> getAllEntries() {
    final String _sql = "SELECT * FROM work_entries ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"work_entries"}, new Callable<List<WorkEntry>>() {
      @Override
      public List<WorkEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfServiceType = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceType");
          final int _cursorIndexOfCustomServiceName = CursorUtil.getColumnIndexOrThrow(_cursor, "customServiceName");
          final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customerName");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<WorkEntry> _result = new ArrayList<WorkEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpServiceType;
            if (_cursor.isNull(_cursorIndexOfServiceType)) {
              _tmpServiceType = null;
            } else {
              _tmpServiceType = _cursor.getString(_cursorIndexOfServiceType);
            }
            final String _tmpCustomServiceName;
            if (_cursor.isNull(_cursorIndexOfCustomServiceName)) {
              _tmpCustomServiceName = null;
            } else {
              _tmpCustomServiceName = _cursor.getString(_cursorIndexOfCustomServiceName);
            }
            final String _tmpCustomerName;
            if (_cursor.isNull(_cursorIndexOfCustomerName)) {
              _tmpCustomerName = null;
            } else {
              _tmpCustomerName = _cursor.getString(_cursorIndexOfCustomerName);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new WorkEntry(_tmpId,_tmpServiceType,_tmpCustomServiceName,_tmpCustomerName,_tmpNotes,_tmpAmount,_tmpIsPaid,_tmpTimestamp);
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
  public Flow<List<WorkEntry>> getEntriesInRange(final long startTime, final long endTime) {
    final String _sql = "SELECT * FROM work_entries WHERE timestamp >= ? AND timestamp <= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"work_entries"}, new Callable<List<WorkEntry>>() {
      @Override
      public List<WorkEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfServiceType = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceType");
          final int _cursorIndexOfCustomServiceName = CursorUtil.getColumnIndexOrThrow(_cursor, "customServiceName");
          final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customerName");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<WorkEntry> _result = new ArrayList<WorkEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpServiceType;
            if (_cursor.isNull(_cursorIndexOfServiceType)) {
              _tmpServiceType = null;
            } else {
              _tmpServiceType = _cursor.getString(_cursorIndexOfServiceType);
            }
            final String _tmpCustomServiceName;
            if (_cursor.isNull(_cursorIndexOfCustomServiceName)) {
              _tmpCustomServiceName = null;
            } else {
              _tmpCustomServiceName = _cursor.getString(_cursorIndexOfCustomServiceName);
            }
            final String _tmpCustomerName;
            if (_cursor.isNull(_cursorIndexOfCustomerName)) {
              _tmpCustomerName = null;
            } else {
              _tmpCustomerName = _cursor.getString(_cursorIndexOfCustomerName);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new WorkEntry(_tmpId,_tmpServiceType,_tmpCustomServiceName,_tmpCustomerName,_tmpNotes,_tmpAmount,_tmpIsPaid,_tmpTimestamp);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
