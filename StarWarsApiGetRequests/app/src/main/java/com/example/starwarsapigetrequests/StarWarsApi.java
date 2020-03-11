package com.example.starwarsapigetrequests;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StarWarsApi {

    @GET("planets")
    Call<PlanetCounter> getPlanetCount();

}
