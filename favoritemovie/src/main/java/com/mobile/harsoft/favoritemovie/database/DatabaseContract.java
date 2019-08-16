package com.mobile.harsoft.favoritemovie.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    private static final String AUTHORITY = "com.mobile.harsoft.mymoviecatalogues";
    private static final String SCHEME = "content";

    public DatabaseContract() {
    }

    public static final class MovieColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
        public static String POSTER_PATH = "poster_path";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String VOTE_AVERAGE = "vote_average";
        public static String VOTE_COUNT = "vote_count";
        public static String POPULARITY = "popularity";
        static String TABLE_NAME = "tb_movie";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }
}
