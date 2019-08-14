package com.mobile.harsoft.mymoviecatalogues.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DatabaseContract.MovieColumns.ID;
import static com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DatabaseContract.MovieColumns.TABLE_NAME;

public class FavoriteMovieHelper {

    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private SQLiteDatabase database;

    public FavoriteMovieHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        DbFavMovies dbFavMovies = new DbFavMovies(context);
        database = dbFavMovies.getWritableDatabase();
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }
}
