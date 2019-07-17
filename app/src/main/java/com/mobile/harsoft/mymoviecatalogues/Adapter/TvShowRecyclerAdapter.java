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

import com.mobile.harsoft.mymoviecatalogues.DataClass.TvShow;
import com.mobile.harsoft.mymoviecatalogues.DetailTvShowActivity;
import com.mobile.harsoft.mymoviecatalogues.R;

import java.util.ArrayList;

public class TvShowRecyclerAdapter extends RecyclerView.Adapter<TvShowRecyclerAdapter.ViewHolder> {

    private ArrayList<TvShow> tvShows;
    private Context context;

    public TvShowRecyclerAdapter(ArrayList<TvShow> tvShows, Context context){
        this.tvShows = tvShows;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleShow, categoryShow, starShow;
        private ImageView ilustShow;
        private ConstraintLayout constraintLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleShow = itemView.findViewById(R.id.title_show);
            categoryShow = itemView.findViewById(R.id.category_show);
            starShow = itemView.findViewById(R.id.star_show);
            ilustShow = itemView.findViewById(R.id.ilust_show);
            constraintLayout = itemView.findViewById(R.id.constraint2);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TvShow tvShow = new TvShow();
                    tvShow.setTitle(tvShows.get(getAdapterPosition()).getTitle());
                    tvShow.setIlustration(tvShows.get(getAdapterPosition()).getIlustration());
                    tvShow.setCategory(tvShows.get(getAdapterPosition()).getCategory());
                    tvShow.setYear(tvShows.get(getAdapterPosition()).getYear());
                    tvShow.setSynopsis(tvShows.get(getAdapterPosition()).getSynopsis());
                    tvShow.setRate(tvShows.get(getAdapterPosition()).getRate());
                    tvShow.setStars(tvShows.get(getAdapterPosition()).getStars());
                    Intent intent = new Intent(context, DetailTvShowActivity.class);
                    intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShow);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design_tv_show, viewGroup, false);
        return new TvShowRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String title = tvShows.get(i).getTitle();
        final String category = tvShows.get(i).getCategory();
        final String stars = tvShows.get(i).getStars();
        final int ilust = tvShows.get(i).getIlustration();

        viewHolder.titleShow.setText(title);
        viewHolder.categoryShow.setText(category);
        viewHolder.starShow.setText(stars);
        viewHolder.ilustShow.setImageResource(ilust);

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public void setFilter(ArrayList<TvShow> tvArrayList){
        tvShows = new ArrayList<>();
        tvShows.addAll(tvArrayList);
        notifyDataSetChanged();
    }
}
