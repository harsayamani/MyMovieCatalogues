package com.mobile.harsoft.mymoviecatalogues.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
    private String name;
    private String[] origin_country;
    private String overview;
    private String poster_path;
    private String first_air_date;
    private double popularity;
    private double vote_average;

    public TvShow() {
    }

    private TvShow(Parcel in) {
        this.name = in.readString();
        this.origin_country = in.createStringArray();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.first_air_date = in.readString();
        this.popularity = in.readDouble();
        this.vote_average = in.readDouble();
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringArray(this.origin_country);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.first_air_date);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.vote_average);
    }
}
