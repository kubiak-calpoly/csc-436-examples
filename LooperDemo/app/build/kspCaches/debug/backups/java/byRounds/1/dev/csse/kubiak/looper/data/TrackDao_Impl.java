package dev.csse.kubiak.looper.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TrackDao_Impl implements TrackDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TrackEntity> __insertionAdapterOfTrackEntity;

  private final EntityDeletionOrUpdateAdapter<TrackEntity> __deletionAdapterOfTrackEntity;

  private final EntityDeletionOrUpdateAdapter<TrackEntity> __updateAdapterOfTrackEntity;

  public TrackDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrackEntity = new EntityInsertionAdapter<TrackEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `TrackEntity` (`id`,`loop_id`,`trackNum`,`name`,`size`,`data`,`sound`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrackEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLoopId());
        statement.bindLong(3, entity.getTrackNum());
        statement.bindString(4, entity.getName());
        statement.bindLong(5, entity.getSize());
        statement.bindString(6, entity.getData());
        if (entity.getSound() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSound());
        }
      }
    };
    this.__deletionAdapterOfTrackEntity = new EntityDeletionOrUpdateAdapter<TrackEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `TrackEntity` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrackEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTrackEntity = new EntityDeletionOrUpdateAdapter<TrackEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `TrackEntity` SET `id` = ?,`loop_id` = ?,`trackNum` = ?,`name` = ?,`size` = ?,`data` = ?,`sound` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrackEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLoopId());
        statement.bindLong(3, entity.getTrackNum());
        statement.bindString(4, entity.getName());
        statement.bindLong(5, entity.getSize());
        statement.bindString(6, entity.getData());
        if (entity.getSound() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSound());
        }
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public long addTrack(final TrackEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTrackEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTrack(final TrackEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTrackEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTrack(final TrackEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTrackEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flow<TrackEntity> getTrack(final long id) {
    final String _sql = "SELECT * FROM TrackEntity WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"TrackEntity"}, new Callable<TrackEntity>() {
      @Override
      @Nullable
      public TrackEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLoopId = CursorUtil.getColumnIndexOrThrow(_cursor, "loop_id");
          final int _cursorIndexOfTrackNum = CursorUtil.getColumnIndexOrThrow(_cursor, "trackNum");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfSound = CursorUtil.getColumnIndexOrThrow(_cursor, "sound");
          final TrackEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLoopId;
            _tmpLoopId = _cursor.getLong(_cursorIndexOfLoopId);
            final int _tmpTrackNum;
            _tmpTrackNum = _cursor.getInt(_cursorIndexOfTrackNum);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpSize;
            _tmpSize = _cursor.getInt(_cursorIndexOfSize);
            final String _tmpData;
            _tmpData = _cursor.getString(_cursorIndexOfData);
            final String _tmpSound;
            if (_cursor.isNull(_cursorIndexOfSound)) {
              _tmpSound = null;
            } else {
              _tmpSound = _cursor.getString(_cursorIndexOfSound);
            }
            _result = new TrackEntity(_tmpId,_tmpLoopId,_tmpTrackNum,_tmpName,_tmpSize,_tmpData,_tmpSound);
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
  public Flow<List<TrackEntity>> getTracks(final long loopId) {
    final String _sql = "SELECT * FROM TrackEntity WHERE loop_id = ? ORDER BY trackNum";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, loopId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"TrackEntity"}, new Callable<List<TrackEntity>>() {
      @Override
      @NonNull
      public List<TrackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLoopId = CursorUtil.getColumnIndexOrThrow(_cursor, "loop_id");
          final int _cursorIndexOfTrackNum = CursorUtil.getColumnIndexOrThrow(_cursor, "trackNum");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfSound = CursorUtil.getColumnIndexOrThrow(_cursor, "sound");
          final List<TrackEntity> _result = new ArrayList<TrackEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrackEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLoopId;
            _tmpLoopId = _cursor.getLong(_cursorIndexOfLoopId);
            final int _tmpTrackNum;
            _tmpTrackNum = _cursor.getInt(_cursorIndexOfTrackNum);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpSize;
            _tmpSize = _cursor.getInt(_cursorIndexOfSize);
            final String _tmpData;
            _tmpData = _cursor.getString(_cursorIndexOfData);
            final String _tmpSound;
            if (_cursor.isNull(_cursorIndexOfSound)) {
              _tmpSound = null;
            } else {
              _tmpSound = _cursor.getString(_cursorIndexOfSound);
            }
            _item = new TrackEntity(_tmpId,_tmpLoopId,_tmpTrackNum,_tmpName,_tmpSize,_tmpData,_tmpSound);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
