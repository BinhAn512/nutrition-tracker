package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("food_id")
    private int id;

    @SerializedName("food_name")
    private String name;

    @SerializedName("caloric_value")
    private int calories;

    @SerializedName("nutrition_density")
    private int nutritionDensity;
    private int carbs;
    private int protein;
    private int fat;

    private int weight;

    public Food(int id, String name, int calories, int carbs, int protein, int fat, int weight) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }
    public int getWeight() {
        return weight;
    }
}