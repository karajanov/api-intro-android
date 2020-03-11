package com.example.starwarsapigetrequests;

import com.google.gson.annotations.SerializedName;

public class Planet {

    private String name;

    private String climate;

    private String gravity;

    private String terrain;

    private String population;

    @SerializedName("rotation_period")
    private String rotationPeriod;

    public String getName() {
        return name;
    }

    public String getClimate() {
        return climate;
    }

    public String getGravity() {
        return gravity;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getPopulation() {
        return population;
    }

    public String getRotationPeriod() {
        return rotationPeriod;
    }

}