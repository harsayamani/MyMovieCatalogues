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

import com.mobile.harsoft.mymoviecatalogues.datamodel.TvShow;
import com.mobile.harsoft.mymoviecatalogues.api.BuildConfig;

import java.io.InputStream;
import java.net.URL;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tvShow";
    private TextView nameShow, popularityShow, overviewShow, voteAverageShow, originCountryShow, firstAirDateShow;
    private ConstraintLayout ilustShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        prepareView();
        getDataItem();
    }

    @SuppressLint("SetTextI18n")
    private void getDataItem() {
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
    }

    private void prepareView() {
        nameShow = findViewById(R.id.title);
        popularityShow = findViewById(R.id.popularity);
        overviewShow = findViewById(R.id.overview);
        voteAverageShow = findViewById(R.id.vote_average);
        originCountryShow = findViewById(R.id.origin_country);
        ilustShow = findViewById(R.id.ilust);
        firstAirDateShow = findViewById(R.id.release_date);
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
