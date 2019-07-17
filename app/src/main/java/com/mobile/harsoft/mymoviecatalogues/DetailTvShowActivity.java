package com.mobile.harsoft.mymoviecatalogues;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.DataClass.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tvShow";
    private TextView titleShow, categoryShow, synopsisShow, rateShow, starShow, yearShow;
    private RelativeLayout ilustShow;

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

        titleShow.setText(tvShow.getTitle());
        categoryShow.setText(tvShow.getCategory());
        synopsisShow.setText(tvShow.getSynopsis());
        rateShow.setText(tvShow.getRate()+"");
        starShow.setText(tvShow.getStars());
        yearShow.setText(tvShow.getYear());
        ilustShow.setBackgroundResource(tvShow.getIlustration());
    }

    private void prepareView() {
        titleShow = findViewById(R.id.title);
        categoryShow = findViewById(R.id.category);
        synopsisShow = findViewById(R.id.synopsis);
        rateShow = findViewById(R.id.rate);
        starShow = findViewById(R.id.stars);
        ilustShow = findViewById(R.id.ilust);
        yearShow = findViewById(R.id.year);
    }
}
