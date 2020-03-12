package com.example.starwarsapigetrequests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static final String starWarsApiBaseUrl = "https://swapi.co/api/";

    public static Retrofit getBuilder() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(starWarsApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static StarWarsApi getApiReference(Retrofit builder) {

        StarWarsApi apiRef = builder.create(StarWarsApi.class);

        return apiRef;
    }

}
