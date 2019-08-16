package com.mobile.harsoft.mymoviecatalogues.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.mobile.harsoft.mymoviecatalogues.database.FavoriteMovieHelper;

import static com.mobile.harsoft.mymoviecatalogues.database.DatabaseContract.AUTHORITY;
import static com.mobile.harsoft.mymoviecatalogues.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.mobile.harsoft.mymoviecatalogues.database.DatabaseContract.MovieColumns.TABLE_NAME;

@SuppressLint("Registered")
public class FavoriteMovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);

    }

    private FavoriteMovieHelper favoriteMovieHelper;

    @Override
    public boolean onCreate() {
        favoriteMovieHelper = new FavoriteMovieHelper(getContext());
        favoriteMovieHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteMovieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favoriteMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (getContext() != null) {
            assert cursor != null;
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        favoriteMovieHelper.open();
        long added;

        if (sUriMatcher.match(uri) == MOVIE) {
            added = favoriteMovieHelper.insertProvider(values);
        } else {
            added = 0;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            deleted = favoriteMovieHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            updated = favoriteMovieHelper.updateProvider(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
