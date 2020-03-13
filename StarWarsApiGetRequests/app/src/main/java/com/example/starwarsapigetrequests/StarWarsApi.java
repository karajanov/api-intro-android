package com.example.starwarsapigetrequests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StarWarsApi {

    @GET("planets")
    Call<PlanetCounter> getPlanetCount();

    @GET("planets/{id}")
    Call<Planet> getPlanetInfo(@Path("id") int id);
}
