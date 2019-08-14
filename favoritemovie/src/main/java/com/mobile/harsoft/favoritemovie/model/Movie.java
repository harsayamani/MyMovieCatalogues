package com.mobile.harsoft.favoritemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.ID;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.TITLE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.MovieColumns.VOTE_COUNT;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.getColumnDouble;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.getColumnInt;
import static com.mobile.harsoft.favoritemovie.database.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private int id;
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private double vote_average;
    private int vote_count;
    private double popularity;

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.poster_path = getColumnString(cursor, POSTER_PATH);
        this.popularity = getColumnDouble(cursor, POPULARITY);
        this.release_date = getColumnString(cursor, RELEASE_DATE);
        this.vote_average = getColumnDouble(cursor, VOTE_AVERAGE);
        this.vote_count = getColumnInt(cursor, VOTE_COUNT);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
        this.popularity = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
        dest.writeDouble(this.popularity);
    }
}
