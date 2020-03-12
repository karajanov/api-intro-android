package com.example.starwarsapigetrequests;

import com.google.gson.annotations.SerializedName;

public class Planet {

    private String name;

    private String climate;

    private String terrain;

    private String population;

    public String getName() {
        return name;
    }

    public String getClimate() {
        return climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getPopulation() {
        return population;
    }

}