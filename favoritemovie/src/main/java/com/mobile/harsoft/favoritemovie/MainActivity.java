package com.mobile.harsoft.favoritemovie;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.mobile.harsoft.favoritemovie.adapter.FavoriteMovieAdapter;

import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FavoriteMovieAdapter favoriteMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }

    private void getData() {
        ListView listView = findViewById(R.id.list_movie);
        favoriteMovieAdapter = new FavoriteMovieAdapter(this, null, true);
        listView.setAdapter(favoriteMovieAdapter);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        favoriteMovieAdapter.swapCursor(cursor);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favoriteMovieAdapter.swapCursor(null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
