package com.mobile.harsoft.mymoviecatalogues.provider;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DbFavMovies;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.ID;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.OVERVIEW;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.POPULARITY;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.TABLE_NAME;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.TITLE;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.MovieColumns.VOTE_COUNT;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.getColumnDouble;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.getColumnInt;
import static com.mobile.harsoft.mymoviecatalogues.provider.DatabaseContract.getColumnString;

public class FavoriteMovieHelper {

    private static String DATABASE_TABLE = TABLE_NAME;
    @SuppressLint("StaticFieldLeak")
    private static FavoriteMovieHelper INSTANCE;
    private Context context;
    private DbFavMovies dbFavMovies;
    private SQLiteDatabase database;

    public FavoriteMovieHelper(Context context) {
        dbFavMovies = new DbFavMovies(context);
    }

    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        dbFavMovies = new DbFavMovies(context);
        database = dbFavMovies.getWritableDatabase();
    }

    public void close() {
        dbFavMovies.close();

        if (database.isOpen())
            database.close();
    }

//    public boolean checkId(String id) {
//        String query = "Select * from " + DATABASE_TABLE + " where " + ID+ " = " + id;
//        Cursor cursor = database.rawQuery(query, null);
//        if (cursor.getCount() > 0) {
//            cursor.close();
//            return true;
//        } else {
//            cursor.close();
//            return false;
//        }
//    }

    public ArrayList<Movie> query() {
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC"
                , null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(getColumnInt(cursor, ID));
                movie.setTitle(getColumnString(cursor, TITLE));
                movie.setPoster_path(getColumnString(cursor, POSTER_PATH));
                movie.setRelease_date(getColumnString(cursor, RELEASE_DATE));
                movie.setOverview(getColumnString(cursor, OVERVIEW));
                movie.setPopularity(getColumnDouble(cursor, POPULARITY));
                movie.setVote_average(getColumnDouble(cursor, VOTE_AVERAGE));
                movie.setVote_count(getColumnInt(cursor, VOTE_COUNT));
                movie.setGenre_ids(null);

                movies.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movies;
    }

    Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , ID + " ASC");
    }

    long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }
}
