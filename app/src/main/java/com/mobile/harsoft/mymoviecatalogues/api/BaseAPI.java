package com.mobile.harsoft.mymoviecatalogues.api;

import com.mobile.harsoft.mymoviecatalogues.response.ResultMovie;
import com.mobile.harsoft.mymoviecatalogues.response.ResultTV;

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
    Call<ResultTV> getTvAiringToday(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );
}
