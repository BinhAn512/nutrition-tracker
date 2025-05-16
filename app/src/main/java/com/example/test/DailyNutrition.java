package com.example.test;

import java.util.List;

public class DailyNutrition {
    private String date;
    private int targetCalories;
    private List<Meal> meals;

    public DailyNutrition(String date, List<Meal> meals) {
        this.date = date;
        this.targetCalories = 2560; // Default target calories
        this.meals = meals;
    }

    public DailyNutrition(String date, int targetCalories, List<Meal> meals) {
        this.date = date;
        this.targetCalories = targetCalories;
        this.meals = meals;
    }

    public String getDate() {
        return date;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public int getTotalCalories() {
        int total = 0;
        for (Meal meal : meals) {
            total += meal.getTotalCalories();
        }
        return total;
    }

    public int getCaloriesLeft() {
        return targetCalories - getTotalCalories();
    }

    public int getTotalCarbs() {
        int total = 0;
        for (Meal meal : meals) {
            total += meal.getTotalCarbs();
        }
        return total;
    }

    public int getTotalProtein() {
        int total = 0;
        for (Meal meal : meals) {
            total += meal.getTotalProtein();
        }
        return total;
    }

    public int getTotalFat() {
        int total = 0;
        for (Meal meal : meals) {
            total += meal.getTotalFat();
        }
        return total;
    }
}