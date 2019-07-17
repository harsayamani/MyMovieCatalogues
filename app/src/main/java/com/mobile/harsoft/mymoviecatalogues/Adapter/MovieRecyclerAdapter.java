package com.mobile.harsoft.mymoviecatalogues.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.DataClass.Movie;
import com.mobile.harsoft.mymoviecatalogues.DetailMovieActivity;
import com.mobile.harsoft.mymoviecatalogues.R;

import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private ArrayList<Movie> movies;
    private Context context;

    public MovieRecyclerAdapter(ArrayList<Movie> movies, Context context){
        this.movies = movies;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleMovie, categoryMovie, rateMovie;
        private ImageView ilustMovie;
        private ConstraintLayout constraintLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.title_movie);
            categoryMovie = itemView.findViewById(R.id.category_movie);
            rateMovie = itemView.findViewById(R.id.rate_movie);
            ilustMovie = itemView.findViewById(R.id.ilust_movie);
            constraintLayout = itemView.findViewById(R.id.list_movie);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = new Movie();
                    movie.setTitle(movies.get(getAdapterPosition()).getTitle());
                    movie.setIlustration(movies.get(getAdapterPosition()).getIlustration());
                    movie.setCategory(movies.get(getAdapterPosition()).getCategory());
                    movie.setYear(movies.get(getAdapterPosition()).getYear());
                    movie.setSynopsis(movies.get(getAdapterPosition()).getSynopsis());
                    movie.setRate(movies.get(getAdapterPosition()).getRate());
                    movie.setMetascore(movies.get(getAdapterPosition()).getMetascore());
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                    context.startActivity(intent);
                }
            });
        }
    }


    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String title = movies.get(i).getTitle();
        final String category = movies.get(i).getCategory();
        final double rate = movies.get(i).getRate();
        final int ilust = movies.get(i).getIlustration();

        viewHolder.titleMovie.setText(title);
        viewHolder.categoryMovie.setText(category);
        viewHolder.rateMovie.setText(String.valueOf(rate));
        viewHolder.ilustMovie.setImageResource(ilust);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setFilter(ArrayList<Movie> movieArrayList){
        movies = new ArrayList<>();
        movies.addAll(movieArrayList);
        notifyDataSetChanged();
    }
}
