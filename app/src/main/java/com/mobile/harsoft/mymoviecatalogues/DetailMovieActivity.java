package com.mobile.harsoft.mymoviecatalogues;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.DataClass.Movie;

import java.io.InputStream;
import java.net.URL;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView titleMovie, popularityMovie, overviewMovie, voteAverageMovie, voteCountMovie, releaseDateMovie;
    private ConstraintLayout ilustMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        prepareView();
        getDataItem();
    }

    private void prepareView() {
        titleMovie = findViewById(R.id.title);
        popularityMovie = findViewById(R.id.popularity);
        overviewMovie = findViewById(R.id.overview);
        voteAverageMovie = findViewById(R.id.vote_average);
        voteCountMovie = findViewById(R.id.vote_count);
        releaseDateMovie = findViewById(R.id.release_date);
        ilustMovie = findViewById(R.id.ilust);
    }

    @SuppressLint("SetTextI18n")
    private void getDataItem() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        titleMovie.setText("" + movie.getTitle());
        popularityMovie.setText("" + movie.getPopularity());
        overviewMovie.setText("" + movie.getOverview());
        voteAverageMovie.setText("" + movie.getVote_average() + "/10");
        voteCountMovie.setText("" + movie.getVote_count());
        releaseDateMovie.setText("" + movie.getRelease_date());
        new DownloadImage(ilustMovie).execute("" + movie.getPoster_path());
    }

    @SuppressLint("StaticFieldLeak")
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ConstraintLayout constraintLayout;

        DownloadImage(ConstraintLayout constraintLayout) {
            this.constraintLayout = constraintLayout;
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
            Drawable drawable = new BitmapDrawable(getResources(), result);
            constraintLayout.setBackground(drawable);
        }
    }
}
