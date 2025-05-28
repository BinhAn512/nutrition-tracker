package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class Vitamins {
    @SerializedName("vitamin_id")
    private int id;

    @SerializedName("food_id")
    private int foodId;

    @SerializedName("vitamin_a")
    private float vitaminA;

    @SerializedName("vitamin_b1")
    private float vitaminB1;

    @SerializedName("vitamin_b11")
    private float vitaminB11;

    @SerializedName("vitamin_b12")
    private float vitaminB12;

    @SerializedName("vitamin_b2")
    private float vitaminB2;

    @SerializedName("vitamin_b3")
    private float vitaminB3;

    @SerializedName("vitamin_b5")
    private float vitaminB5;

    @SerializedName("vitamin_b6")
    private float vitaminB6;

    @SerializedName("vitamin_c")
    private float vitaminC;

    @SerializedName("vitamin_d")
    private float vitaminD;

    @SerializedName("vitamin_e")
    private float vitaminE;

    @SerializedName("vitamin_k")
    private float vitaminK;

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public float getVitaminA() {
        return vitaminA;
    }

    public float getVitaminB1() {
        return vitaminB1;
    }

    public float getVitaminB11() {
        return vitaminB11;
    }

    public float getVitaminB12() {
        return vitaminB12;
    }

    public float getVitaminB2() {
        return vitaminB2;
    }

    public float getVitaminB3() {
        return vitaminB3;
    }

    public float getVitaminB5() {
        return vitaminB5;
    }

    public float getVitaminB6() {
        return vitaminB6;
    }

    public float getVitaminC() {
        return vitaminC;
    }

    public float getVitaminD() {
        return vitaminD;
    }

    public float getVitaminE() {
        return vitaminE;
    }

    public float getVitaminK() {
        return vitaminK;
    }
}
