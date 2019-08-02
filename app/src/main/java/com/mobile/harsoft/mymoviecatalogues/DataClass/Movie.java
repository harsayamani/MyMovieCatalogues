package com.mobile.harsoft.mymoviecatalogues.DataClass;

import android.os.Parcel;
import android.os.Parcelable;

//Submission 2

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private double vote_average;
    private int vote_count;
    private int[] genre_ids;
    private double popularity;

    public Movie() {
    }

    private Movie(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
        this.genre_ids = in.createIntArray();
        this.popularity = in.readDouble();
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
        dest.writeIntArray(this.genre_ids);
        dest.writeDouble(this.popularity);
    }
}
