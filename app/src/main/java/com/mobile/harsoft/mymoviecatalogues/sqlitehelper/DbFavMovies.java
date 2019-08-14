package com.mobile.harsoft.mymoviecatalogues.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbFavMovies extends SQLiteOpenHelper {
    public DbFavMovies(Context context) {
        super(context, "movies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_movie = "create table tb_movie (id integer primary key, title varchar not null," +
                "poster_path varchar not null, release_date varchar not null, overview text not null, " +
                "popularity double not null, vote_average double not null, vote_count integer not null)";
        db.execSQL(tb_movie);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_movie");
        onCreate(db);
    }

    public void insertFavMovie(int id, String title, String poster_path, String release_date,
                               String overview, double popularity, double vote_average,
                               int vote_count) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("title", title);
        values.put("poster_path", poster_path);
        values.put("release_date", release_date);
        values.put("overview", overview);
        values.put("popularity", popularity);
        values.put("vote_average", vote_average);
        values.put("vote_count", vote_count);

        db.insert("tb_movie", null, values);
    }

    public void deleteFavMovie(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tb_movie", "ID=?", new String[]{id});
    }
}
