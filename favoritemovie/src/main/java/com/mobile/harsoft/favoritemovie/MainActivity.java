package com.mobile.harsoft.favoritemovie;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mobile.harsoft.favoritemovie.adapter.MovieRecyclerAdapter;
import com.mobile.harsoft.favoritemovie.model.Movie;

import java.util.ArrayList;

import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.ID;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.TITLE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.VOTE_COUNT;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        getData();
    }

    private void getData() {
        ContentResolver resolver = getContentResolver();
        ArrayList<Movie> movies = new ArrayList<>();
        String[] projection = new String[]{ID, TITLE, POSTER_PATH, RELEASE_DATE, OVERVIEW, POPULARITY, VOTE_AVERAGE, VOTE_COUNT};
        @SuppressLint("Recycle") Cursor cursor = resolver.query(CONTENT_URI, projection, null, null, null);
        assert cursor != null;
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
            movies.add(movie);
        }

        MovieRecyclerAdapter adapter = new MovieRecyclerAdapter(movies);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }
}
