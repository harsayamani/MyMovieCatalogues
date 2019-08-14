package com.mobile.harsoft.mymoviecatalogues.api;

import com.mobile.harsoft.mymoviecatalogues.response.ResultMovie;
import com.mobile.harsoft.mymoviecatalogues.response.ResultTv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseAPI {
    @GET("movie/upcoming")
    Call<ResultMovie> getUpcomingMovie(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/airing_today")
    Call<ResultTv> getTvAiringToday(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<ResultMovie> getMovieSearch(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query
    );

    @GET("search/tv")
    Call<ResultTv> getTvSearch(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query
    );

    @GET("movie/top_rated")
    Call<ResultMovie> getMovieTopRated(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("discover/movie")
    Call<ResultMovie> getMovieReleaseToday(
            @Query("api_key") String api_key,
            @Query("primary_release_date.gte") String primary_release_date_gte,
            @Query("primary_release_date.lte") String primary_release_date_lte
    );

    @GET("tv/top_rated")
    Call<ResultTv> getTvTopRated(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );
}
