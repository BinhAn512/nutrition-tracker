package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class Macronutrients {
    @SerializedName("macro_id")
    private int id;

    @SerializedName("food_id")
    private int foodId;

    @SerializedName("fat")
    private float fat;

    @SerializedName("saturated_fats")
    private float saturatedFats;

    @SerializedName("monounsaturated_fats")
    private float monosaturatedFats;

    @SerializedName("polyunsaturated_fats")
    private float polyunsaturatedFats;

    @SerializedName("carbohydrates")
    private float carbs;

    @SerializedName("sugars")
    private float sugars;

    @SerializedName("protein")
    private float protein;

    @SerializedName("dietary_fiber")
    private float dietaryFiber;

    @SerializedName("cholesterol")
    private float cholesterol;

    @SerializedName("sodium")
    private float sodium;

    @SerializedName("water")
    private float water;

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public float getFat() {
        return fat;
    }

    public float getSaturatedFats() {
        return saturatedFats;
    }

    public float getMonosaturatedFats() {
        return monosaturatedFats;
    }

    public float getPolyunsaturatedFats() {
        return polyunsaturatedFats;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getSugars() {
        return sugars;
    }

    public float getProtein() {
        return protein;
    }

    public float getDietaryFiber() {
        return dietaryFiber;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public float getSodium() {
        return sodium;
    }

    public float getWater() {
        return water;
    }
}
