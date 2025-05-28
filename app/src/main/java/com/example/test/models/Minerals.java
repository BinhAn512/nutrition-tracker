package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class Minerals {
    @SerializedName("mineral_id")
    private int id;

    @SerializedName("food_id")
    private int foodId;

    @SerializedName("calcium")
    private float calcium;

    @SerializedName("copper")
    private float copper;

    @SerializedName("iron")
    private float iron;

    @SerializedName("magnesium")
    private float magnesium;

    @SerializedName("manganese")
    private float manganese;

    @SerializedName("phosphorus")
    private float phosphorus;

    @SerializedName("potassium")
    private float potassium;

    @SerializedName("selenium")
    private float selenium;

    @SerializedName("zinc")
    private float zinc;

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public float getCalcium() {
        return calcium;
    }

    public float getCopper() {
        return copper;
    }

    public float getIron() {
        return iron;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public float getManganese() {
        return manganese;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public float getPotassium() {
        return potassium;
    }

    public float getSelenium() {
        return selenium;
    }

    public float getZinc() {
        return zinc;
    }
}
