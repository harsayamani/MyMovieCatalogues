package com.mobile.harsoft.mymoviecatalogues.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.DetailMovieActivity;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.api.BuildConfig;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private ArrayList<Movie> movies;
    private Context context;

    public MovieRecyclerAdapter(ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
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

        new DownloadImage(viewHolder.ilustMovie).execute(BuildConfig.IMG_URL+poster_path);
        viewHolder.titleMovie.setText(title);
        viewHolder.rateMovie.setText(vote_average + "");
        viewHolder.popularityMovie.setText(popularity + "");
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setFilter(ArrayList<Movie> movieArrayList) {
        movies = new ArrayList<>();
        movies.addAll(movieArrayList);
        notifyDataSetChanged();
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
        private ConstraintLayout constraintLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.title_movie);
            popularityMovie = itemView.findViewById(R.id.popularity_movie);
            rateMovie = itemView.findViewById(R.id.vote_average_movie);
            ilustMovie = itemView.findViewById(R.id.poster_path_movie);
            constraintLayout = itemView.findViewById(R.id.list_movie);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = new Movie();
                    movie.setId(movies.get(getAdapterPosition()).getId());
                    movie.setTitle(movies.get(getAdapterPosition()).getTitle());
                    movie.setOverview(movies.get(getAdapterPosition()).getOverview());
                    movie.setPoster_path(movies.get(getAdapterPosition()).getPoster_path());
                    movie.setRelease_date(movies.get(getAdapterPosition()).getRelease_date());
                    movie.setVote_average(movies.get(getAdapterPosition()).getVote_average());
                    movie.setVote_count(movies.get(getAdapterPosition()).getVote_count());
                    movie.setGenre_ids(movies.get(getAdapterPosition()).getGenre_ids());
                    movie.setPopularity(movies.get(getAdapterPosition()).getPopularity());
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                    context.startActivity(intent);
                }
            });
        }
    }
}
