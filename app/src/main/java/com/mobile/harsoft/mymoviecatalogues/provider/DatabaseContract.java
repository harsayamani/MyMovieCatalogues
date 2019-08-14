package com.mobile.harsoft.mymoviecatalogues.provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

class DatabaseContract {
    static final String AUTHORITY = "com.mobile.harsoft.mymoviecatalogues";
    private static final String SCHEME = "content";

    private DatabaseContract() {
    }

    static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    static final class MovieColumns implements BaseColumns {
        static String TABLE_NAME = "tb_movie";
        static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
        static String ID = "id";
        static String TITLE = "title";
        static String POSTER_PATH = "poster_path";
        static String RELEASE_DATE = "release_date";
        static String OVERVIEW = "overview";
        static String VOTE_AVERAGE = "vote_average";
        static String VOTE_COUNT = "vote_count";
        static String POPULARITY = "popularity";

    }
}
