package com.example.test;

public class FoodItem {
    private int id;
    private String name;
    private int calories;
    private int carbs;
    private int protein;
    private int fat;

    private int weight;

    public FoodItem(int id, String name, int calories, int carbs, int protein, int fat, int weight) {
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