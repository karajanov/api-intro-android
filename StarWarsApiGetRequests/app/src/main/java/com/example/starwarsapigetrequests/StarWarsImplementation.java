package com.example.starwarsapigetrequests;

import retrofit2.Retrofit;

public class StarWarsImplementation {

    private Retrofit builder;
    private StarWarsApi apiReference;

    public StarWarsImplementation(Retrofit builder, StarWarsApi apiReference) {
        this.builder = builder;
        this.apiReference = apiReference;
    }
}
