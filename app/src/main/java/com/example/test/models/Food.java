package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("food_id")
    private int id;

    @SerializedName("food_name")
    private String name;

    @SerializedName("caloric_value")
    private float calories;

    @SerializedName("nutrition_density")
    private float nutritionDensity;

    public Food(int id, String name, float calories, float nutritionDensity) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.nutritionDensity = nutritionDensity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getNutritionDensity() {
        return nutritionDensity;
    }

    public void setNutritionDensity(float nutritionDensity) {
        this.nutritionDensity = nutritionDensity;
    }
}