package com.mobile.harsoft.mymoviecatalogues.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.mobile.harsoft.mymoviecatalogues";
    private static final String SCHEME = "content";

    private DatabaseContract() {
    }

    public static final class MovieColumns implements BaseColumns {
        public static String TABLE_NAME = "tb_movie";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

        static String ID = "id";
    }
}
