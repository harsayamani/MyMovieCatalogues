package com.mobile.harsoft.mymoviecatalogues.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.harsoft.mymoviecatalogues.BuildConfig;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DbFavMovies;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> bitmaps = new ArrayList<>();
    private Context context;
    private int appWidgetId;

    StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        bitmaps = new ArrayList<>();
        getFavoriteMovies();
    }

    private void getFavoriteMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        DbFavMovies dbFavMovies = new DbFavMovies(context);
        SQLiteDatabase database = dbFavMovies.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery("select*from tb_movie", null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setPoster_path(cursor.getString(2));
            movie.setRelease_date(cursor.getString(3));
            movie.setOverview(cursor.getString(4));
            movie.setPopularity(cursor.getDouble(5));
            movie.setVote_average(cursor.getDouble(6));
            movie.setVote_count(cursor.getInt(7));
            movie.setGenre_ids(null);
            movies.add(movie);
        }

        for (Movie m : movies) {
            try {
                Bitmap preview = Glide.with(context)
                        .asBitmap()
                        .load(BuildConfig.IMG_URL + m.getPoster_path())
                        .apply(new RequestOptions().fitCenter())
                        .submit()
                        .get();
                bitmaps.add(preview);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        bitmaps.clear();
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.design_widget_item);
        remoteViews.setImageViewBitmap(R.id.imageView, bitmaps.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
