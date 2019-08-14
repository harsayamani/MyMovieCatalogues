package com.mobile.harsoft.mymoviecatalogues.api;

import com.mobile.harsoft.mymoviecatalogues.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static Retrofit retrofit;
    private static APIClient mInstance;

    private APIClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized APIClient getInstance() {
        if (mInstance == null) {
            mInstance = new APIClient();
        }
        return mInstance;
    }

    public BaseAPI baseAPI() {
        return retrofit.create(BaseAPI.class);
    }
}
