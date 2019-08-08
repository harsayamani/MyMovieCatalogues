package com.mobile.harsoft.mymoviecatalogues.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbFavTvShows extends SQLiteOpenHelper {
    public DbFavTvShows(Context context) {
        super(context, "tvshow.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_tvShow = "create table tb_tvShow (id integer primary key, name text ," +
                "poster_path text, first_air_date text, overview text, popularity double, vote_average double, origin_country text)";
        db.execSQL(tb_tvShow);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_tvShow");
        onCreate(db);
    }

    public void insertFavTvShow(int id, String name, String poster_path, String first_air_date,
                                String overview, double popularity, double vote_average,
                                String[] origin_country) {

        StringBuilder country = new StringBuilder();
        for (String s : origin_country) {
            country.append(s).append(" ");
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("poster_path", poster_path);
        values.put("first_air_date", first_air_date);
        values.put("overview", overview);
        values.put("popularity", popularity);
        values.put("vote_average", vote_average);
        values.put("origin_country", country.toString());

        db.insert("tb_tvShow", "id", values);
    }

    public void deleteFavTvShow(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tb_tvShow", "ID=?", new String[]{id});
    }
}
