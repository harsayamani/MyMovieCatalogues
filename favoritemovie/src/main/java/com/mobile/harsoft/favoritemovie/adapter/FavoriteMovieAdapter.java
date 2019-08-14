package com.mobile.harsoft.favoritemovie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.harsoft.favoritemovie.R;

import java.io.InputStream;
import java.net.URL;

import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.TITLE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.getColumnDouble;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.getColumnString;

public class FavoriteMovieAdapter extends CursorAdapter {


    public FavoriteMovieAdapter(Context context, Cursor c, Boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.design_movie, parent, false);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView titleMovie = view.findViewById(R.id.title_movie);
            TextView popularityMovie = view.findViewById(R.id.popularity_movie);
            TextView rateMovie = view.findViewById(R.id.vote_average_movie);
            ImageView ilustMovie = view.findViewById(R.id.poster_path_movie);

            String title = getColumnString(cursor, TITLE);
            String poster_path = getColumnString(cursor, POSTER_PATH);
            double vote_average = getColumnDouble(cursor, VOTE_AVERAGE);
            double popularity = getColumnDouble(cursor, POPULARITY);

            titleMovie.setText(title);
            rateMovie.setText(String.valueOf(vote_average));
            popularityMovie.setText(String.valueOf(popularity));
            new DownloadImage(ilustMovie).execute(BuildConfig.IMG_URL + poster_path);
        }
    }

    public static class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ImageView imageView;

        DownloadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            Drawable drawable = new BitmapDrawable(result);
            imageView.setBackground(drawable);
        }
    }
}
