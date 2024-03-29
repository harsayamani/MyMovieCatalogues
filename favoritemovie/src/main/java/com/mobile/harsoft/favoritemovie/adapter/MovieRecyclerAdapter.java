package com.mobile.harsoft.favoritemovie.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.harsoft.favoritemovie.BuildConfig;
import com.mobile.harsoft.favoritemovie.R;
import com.mobile.harsoft.favoritemovie.model.Movie;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private ArrayList<Movie> movies;

    public MovieRecyclerAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String title = movies.get(i).getTitle();
        double popularity = movies.get(i).getPopularity();
        String poster_path = movies.get(i).getPoster_path();
        double vote_average = movies.get(i).getVote_average();

        new DownloadImage(viewHolder.ilustMovie).execute(BuildConfig.IMG_URL + poster_path);
        viewHolder.titleMovie.setText(title);
        viewHolder.rateMovie.setText(vote_average + "");
        viewHolder.popularityMovie.setText(popularity + "");
    }

    @Override
    public int getItemCount() {
        return movies.size();
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

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleMovie, popularityMovie, rateMovie;
        private ImageView ilustMovie;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.title_movie);
            popularityMovie = itemView.findViewById(R.id.popularity_movie);
            rateMovie = itemView.findViewById(R.id.vote_average_movie);
            ilustMovie = itemView.findViewById(R.id.poster_path_movie);
        }
    }
}
