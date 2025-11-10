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
public final class LoopDao_Impl implements LoopDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LoopEntity> __insertionAdapterOfLoopEntity;

  private final EntityDeletionOrUpdateAdapter<LoopEntity> __deletionAdapterOfLoopEntity;

  private final EntityDeletionOrUpdateAdapter<LoopEntity> __updateAdapterOfLoopEntity;

  public LoopDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoopEntity = new EntityInsertionAdapter<LoopEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `LoopEntity` (`id`,`title`,`created`,`barsToLoop`,`beatsPerBar`,`subdivisions`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LoopEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getCreationTime());
        statement.bindLong(4, entity.getBarsToLoop());
        statement.bindLong(5, entity.getBeatsPerBar());
        statement.bindLong(6, entity.getSubdivisions());
      }
    };
    this.__deletionAdapterOfLoopEntity = new EntityDeletionOrUpdateAdapter<LoopEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `LoopEntity` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LoopEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLoopEntity = new EntityDeletionOrUpdateAdapter<LoopEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `LoopEntity` SET `id` = ?,`title` = ?,`created` = ?,`barsToLoop` = ?,`beatsPerBar` = ?,`subdivisions` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LoopEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getCreationTime());
        statement.bindLong(4, entity.getBarsToLoop());
        statement.bindLong(5, entity.getBeatsPerBar());
        statement.bindLong(6, entity.getSubdivisions());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public long addLoop(final LoopEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfLoopEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLoop(final LoopEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLoopEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateLoop(final LoopEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLoopEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flow<LoopEntity> getLoop(final long id) {
    final String _sql = "SELECT * FROM LoopEntity WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"LoopEntity"}, new Callable<LoopEntity>() {
      @Override
      @Nullable
      public LoopEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCreationTime = CursorUtil.getColumnIndexOrThrow(_cursor, "created");
          final int _cursorIndexOfBarsToLoop = CursorUtil.getColumnIndexOrThrow(_cursor, "barsToLoop");
          final int _cursorIndexOfBeatsPerBar = CursorUtil.getColumnIndexOrThrow(_cursor, "beatsPerBar");
          final int _cursorIndexOfSubdivisions = CursorUtil.getColumnIndexOrThrow(_cursor, "subdivisions");
          final LoopEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCreationTime;
            _tmpCreationTime = _cursor.getLong(_cursorIndexOfCreationTime);
            final int _tmpBarsToLoop;
            _tmpBarsToLoop = _cursor.getInt(_cursorIndexOfBarsToLoop);
            final int _tmpBeatsPerBar;
            _tmpBeatsPerBar = _cursor.getInt(_cursorIndexOfBeatsPerBar);
            final int _tmpSubdivisions;
            _tmpSubdivisions = _cursor.getInt(_cursorIndexOfSubdivisions);
            _result = new LoopEntity(_tmpId,_tmpTitle,_tmpCreationTime,_tmpBarsToLoop,_tmpBeatsPerBar,_tmpSubdivisions);
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
  public Flow<List<LoopEntity>> getLoops() {
    final String _sql = "SELECT * FROM LoopEntity ORDER BY title COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"LoopEntity"}, new Callable<List<LoopEntity>>() {
      @Override
      @NonNull
      public List<LoopEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCreationTime = CursorUtil.getColumnIndexOrThrow(_cursor, "created");
          final int _cursorIndexOfBarsToLoop = CursorUtil.getColumnIndexOrThrow(_cursor, "barsToLoop");
          final int _cursorIndexOfBeatsPerBar = CursorUtil.getColumnIndexOrThrow(_cursor, "beatsPerBar");
          final int _cursorIndexOfSubdivisions = CursorUtil.getColumnIndexOrThrow(_cursor, "subdivisions");
          final List<LoopEntity> _result = new ArrayList<LoopEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoopEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCreationTime;
            _tmpCreationTime = _cursor.getLong(_cursorIndexOfCreationTime);
            final int _tmpBarsToLoop;
            _tmpBarsToLoop = _cursor.getInt(_cursorIndexOfBarsToLoop);
            final int _tmpBeatsPerBar;
            _tmpBeatsPerBar = _cursor.getInt(_cursorIndexOfBeatsPerBar);
            final int _tmpSubdivisions;
            _tmpSubdivisions = _cursor.getInt(_cursorIndexOfSubdivisions);
            _item = new LoopEntity(_tmpId,_tmpTitle,_tmpCreationTime,_tmpBarsToLoop,_tmpBeatsPerBar,_tmpSubdivisions);
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
