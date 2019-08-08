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

import com.mobile.harsoft.mymoviecatalogues.DetailTvShowActivity;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.api.BuildConfig;
import com.mobile.harsoft.mymoviecatalogues.model.TvShow;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class TvShowRecyclerAdapter extends RecyclerView.Adapter<TvShowRecyclerAdapter.ViewHolder> {

    private ArrayList<TvShow> tvShows;
    private Context context;

    public TvShowRecyclerAdapter(ArrayList<TvShow> tvShows, Context context) {
        this.tvShows = tvShows;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design_tv_show, viewGroup, false);
        return new TvShowRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = tvShows.get(i).getName();
        String[] origin_country = tvShows.get(i).getOrigin_country();
        String poster_path = tvShows.get(i).getPoster_path();
        StringBuilder country = new StringBuilder();
        for (String s : origin_country) {
            country.append(s).append(" ");
        }

        new DownloadImage(viewHolder.posterPathShow).execute(BuildConfig.IMG_URL+poster_path);
        viewHolder.nameShow.setText(name);
        viewHolder.originCountryShow.setText(country.toString());


    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public void setFilter(ArrayList<TvShow> tvArrayList) {
        tvShows = new ArrayList<>();
        tvShows.addAll(tvArrayList);
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

        private TextView nameShow, originCountryShow;
        private ImageView posterPathShow;
        private ConstraintLayout constraintLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameShow = itemView.findViewById(R.id.name_show);
            originCountryShow = itemView.findViewById(R.id.country_show);
            posterPathShow = itemView.findViewById(R.id.poster_path_show);
            constraintLayout = itemView.findViewById(R.id.constraint2);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TvShow tvShow = new TvShow();
                    tvShow.setId(tvShows.get(getAdapterPosition()).getId());
                    tvShow.setName(tvShows.get(getAdapterPosition()).getName());
                    tvShow.setFirst_air_date(tvShows.get(getAdapterPosition()).getFirst_air_date());
                    tvShow.setOrigin_country(tvShows.get(getAdapterPosition()).getOrigin_country());
                    tvShow.setOverview(tvShows.get(getAdapterPosition()).getOverview());
                    tvShow.setPoster_path(tvShows.get(getAdapterPosition()).getPoster_path());
                    tvShow.setVote_average(tvShows.get(getAdapterPosition()).getVote_average());
                    tvShow.setPopularity(tvShows.get(getAdapterPosition()).getPopularity());
                    Intent intent = new Intent(context, DetailTvShowActivity.class);
                    intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShow);
                    context.startActivity(intent);
                }
            });
        }
    }
}
