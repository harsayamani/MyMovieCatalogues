package com.mobile.harsoft.mymoviecatalogues;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.harsoft.mymoviecatalogues.api.BuildConfig;
import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DbFavMovies;

import java.io.InputStream;
import java.net.URL;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView titleMovie, popularityMovie, overviewMovie, voteAverageMovie, voteCountMovie, releaseDateMovie;
    private ConstraintLayout ilustMovie;
    private DbFavMovies dbFavMovies;
    private Menu opMenu;
    private ProgressDialog progressDialog;

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
        dbFavMovies= new DbFavMovies(this);
        opMenu = findViewById(R.id.unfavorite);
        progressDialog = new ProgressDialog(this);
    }

    @SuppressLint("SetTextI18n")
    private void getDataItem() {
        progressDialog.setTitle(R.string.movie_catalogues);
        progressDialog.setIcon(R.drawable.ic_movie_white_24dp);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
                titleMovie.setText("" + movie.getTitle());
                popularityMovie.setText("" + movie.getPopularity());
                overviewMovie.setText("" + movie.getOverview());
                voteAverageMovie.setText("" + movie.getVote_average() + "/10");
                voteCountMovie.setText("" + movie.getVote_count());
                releaseDateMovie.setText("" + movie.getRelease_date());
                new DownloadImage(ilustMovie).execute(BuildConfig.IMG_URL+movie.getPoster_path());

                progressDialog.dismiss();
            }
        }, 1000);
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

    private Cursor getFavorite(){
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        SQLiteDatabase database = dbFavMovies.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from tb_movie where id = '"+movie.getId()+"'", null);
        cursor.moveToFirst();

        return  cursor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (getFavorite().getCount()>0){
            getMenuInflater().inflate(R.menu.favorite, menu);
        }else if (getFavorite().getCount()<1){
            getMenuInflater().inflate(R.menu.unfavorite, menu);
        }

        this.opMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String messageInsert = getString(R.string.message_add_favorite);
        String messageDelete = getString(R.string.message_delete_favorite);

        if (item.getItemId() == R.id.unfavorite) {
            if (getFavorite().getCount() < 1) {
                dbFavMovies.insertFavMovie(movie.getId(),movie.getTitle(), movie.getPoster_path(),
                        movie.getRelease_date(), movie.getOverview(), movie.getPopularity(),
                        movie.getVote_average(), movie.getVote_count());
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));

                Toast.makeText(this, messageInsert, Toast.LENGTH_SHORT).show();
            } else if (getFavorite().getCount() > 0) {
                dbFavMovies.deleteFavMovie(String.valueOf(getFavorite().getInt(0)));
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp));

                Toast.makeText(this, messageDelete, Toast.LENGTH_SHORT).show();
            }
        }
        else if (item.getItemId() == R.id.favorite) {
            if (getFavorite().getCount() > 0){
                dbFavMovies.deleteFavMovie(String.valueOf(getFavorite().getInt(0)));
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp));

                Toast.makeText(this, messageDelete, Toast.LENGTH_SHORT).show();
            }else if (getFavorite().getCount() < 1){
                dbFavMovies.insertFavMovie(movie.getId(), movie.getTitle(), movie.getPoster_path(),
                        movie.getRelease_date(), movie.getOverview(), movie.getPopularity(),
                        movie.getVote_average(), movie.getVote_count());
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));

                Toast.makeText(this, messageInsert, Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
