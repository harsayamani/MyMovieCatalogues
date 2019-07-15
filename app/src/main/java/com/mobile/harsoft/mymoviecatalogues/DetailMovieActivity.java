package com.mobile.harsoft.mymoviecatalogues;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.DataClass.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView titleMovie, categoryMovie, synopsisMovie, rateMovie, metascoreMovie, yearMovie;
    private RelativeLayout ilustMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        prepareView();
        getDataItem();
    }

    private void prepareView() {
        titleMovie = findViewById(R.id.title);
        categoryMovie = findViewById(R.id.category);
        synopsisMovie = findViewById(R.id.synopsis);
        rateMovie = findViewById(R.id.rate);
        metascoreMovie = findViewById(R.id.metascore);
        yearMovie = findViewById(R.id.year);
        ilustMovie = findViewById(R.id.ilust);
    }

    @SuppressLint("SetTextI18n")
    private void getDataItem() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        titleMovie.setText(movie.getTitle());
        categoryMovie.setText(movie.getCategory());
        synopsisMovie.setText(movie.getSynopsis());
        rateMovie.setText(String.valueOf(movie.getRate())+"/10");
        metascoreMovie.setText(String.valueOf(movie.getMetascore()));
        yearMovie.setText(movie.getYear());
        ilustMovie.setBackgroundResource(movie.getIlustration());
    }
}
