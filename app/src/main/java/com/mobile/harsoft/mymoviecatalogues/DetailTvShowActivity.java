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
import com.mobile.harsoft.mymoviecatalogues.model.TvShow;
import com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DbFavTvShows;

import java.io.InputStream;
import java.net.URL;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tvShow";
    private TextView nameShow, popularityShow, overviewShow, voteAverageShow, originCountryShow, firstAirDateShow;
    private ConstraintLayout ilustShow;
    private DbFavTvShows dbFavTvShows;
    private Menu opMenu;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        prepareView();
        getDataItem();
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
                TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);

                String[] origin_country = tvShow.getOrigin_country();
                StringBuilder country = new StringBuilder();
                for (String s : origin_country) {
                    country.append(s).append(" ");
                }

                nameShow.setText(tvShow.getName());
                popularityShow.setText(tvShow.getPopularity() + "");
                overviewShow.setText(tvShow.getOverview());
                voteAverageShow.setText(tvShow.getVote_average() + "");
                originCountryShow.setText(country);
                firstAirDateShow.setText(tvShow.getFirst_air_date());
                new DownloadImage(ilustShow).execute(BuildConfig.IMG_URL + tvShow.getPoster_path());
                progressDialog.dismiss();
            }
        }, 1000);
    }

    private void prepareView() {
        nameShow = findViewById(R.id.title);
        popularityShow = findViewById(R.id.popularity);
        overviewShow = findViewById(R.id.overview);
        voteAverageShow = findViewById(R.id.vote_average);
        originCountryShow = findViewById(R.id.origin_country);
        ilustShow = findViewById(R.id.ilust);
        firstAirDateShow = findViewById(R.id.release_date);
        dbFavTvShows = new DbFavTvShows(this);
        opMenu = findViewById(R.id.unfavorite);
        progressDialog = new ProgressDialog(this);
    }

    private Cursor getFavorite() {
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        SQLiteDatabase database = dbFavTvShows.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from tb_tvShow where id = '" + tvShow.getId() + "'", null);
        cursor.moveToFirst();

        return cursor;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (getFavorite().getCount() > 0) {
            getMenuInflater().inflate(R.menu.favorite, menu);
        } else if (getFavorite().getCount() < 1) {
            getMenuInflater().inflate(R.menu.unfavorite, menu);
        }

        this.opMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        String messageInsert = getString(R.string.message_add_favorite);
        String messageDelete = getString(R.string.message_delete_favorite);
        String[] origin_country = tvShow.getOrigin_country();
        StringBuilder country = new StringBuilder();
        for (String s : origin_country) {
            country.append(s).append(" ");
        }

        if (item.getItemId() == R.id.unfavorite) {
            if (getFavorite().getCount() < 1) {
                dbFavTvShows.insertFavTvShow(tvShow.getId(), tvShow.getName(), tvShow.getPoster_path(),
                        tvShow.getFirst_air_date(), tvShow.getOverview(), tvShow.getPopularity(),
                        tvShow.getVote_average(), tvShow.getOrigin_country());
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));

                Toast.makeText(this, messageInsert, Toast.LENGTH_SHORT).show();
            } else if (getFavorite().getCount() > 0) {
                dbFavTvShows.deleteFavTvShow(String.valueOf(getFavorite().getInt(0)));
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp));

                Toast.makeText(this, messageDelete, Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.favorite) {
            if (getFavorite().getCount() > 0) {
                dbFavTvShows.deleteFavTvShow(String.valueOf(getFavorite().getInt(0)));
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp));

                Toast.makeText(this, messageDelete, Toast.LENGTH_SHORT).show();
            } else if (getFavorite().getCount() < 1) {
                dbFavTvShows.insertFavTvShow(tvShow.getId(), tvShow.getName(), tvShow.getPoster_path(),
                        tvShow.getFirst_air_date(), tvShow.getOverview(), tvShow.getPopularity(),
                        tvShow.getVote_average(), tvShow.getOrigin_country());
                opMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));

                Toast.makeText(this, messageInsert, Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
