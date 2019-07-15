package com.mobile.harsoft.mymoviecatalogues.DataClass;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private int ilustration;
    private String year;
    private String synopsis;
    private String category;
    private double rate;
    private double metascore;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIlustration() {
        return ilustration;
    }

    public void setIlustration(int ilustration) {
        this.ilustration = ilustration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMetascore() {
        return metascore;
    }

    public void setMetascore(double metascore) {
        this.metascore = metascore;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.ilustration);
        dest.writeString(this.year);
        dest.writeString(this.synopsis);
        dest.writeString(this.category);
        dest.writeDouble(this.rate);
        dest.writeDouble(this.metascore);
    }

    public Movie() {
    }

    private Movie(Parcel in) {
        this.title = in.readString();
        this.ilustration = in.readInt();
        this.year = in.readString();
        this.synopsis = in.readString();
        this.category = in.readString();
        this.rate = in.readDouble();
        this.metascore = in.readDouble();
    }

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
}
