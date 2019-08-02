package com.mobile.harsoft.mymoviecatalogues.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.DataClass.Movie;
import com.mobile.harsoft.mymoviecatalogues.R;

import java.util.ArrayList;


//Submission 1
public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.design_movie, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        Movie movie = (Movie) getItem(position);
        viewHolder.bind(movie);

        return convertView;
    }

    public class ViewHolder {
        private TextView tvTitle;
        private TextView tvCategory;
        private TextView tvRate;
        private ImageView ivIlustration;

        ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.title_movie);
            tvRate = view.findViewById(R.id.vote_average_movie);
            tvCategory = view.findViewById(R.id.popularity_movie);
            ivIlustration = view.findViewById(R.id.iposter_path_movie);
        }

        @SuppressLint("SetTextI18n")
        void bind(Movie movie) {
//            tvTitle.setText(movie.getTitle());
//            tvCategory.setText(movie.getCategory());
//            tvRate.setText(movie.getRate() + "/10");
//            ivIlustration.setImageResource(movie.getIlustration());
        }
    }
}
