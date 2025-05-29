package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class FoodNutrition {
    @SerializedName("food_id")
    private int id;

    @SerializedName("caloric_value")
    private float calories;

    @SerializedName("fat")
    private float fat;

    @SerializedName("carbohydrates")
    private float carbs;

    @SerializedName("protein")
    private float protein;

    @SerializedName("serving_size")
    private int servingSize;

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }
}
