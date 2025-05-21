package com.example.test;

import com.example.test.models.Food;

public class Meal {
    private int id;
    private String name;
    private int icon;
    private int maxCalories;
    private java.util.List<Food> foods;

    public Meal(int id, String name, int icon, int maxCalories) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.maxCalories = maxCalories;
        this.foods = new java.util.ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public int getMaxCalories() {
        return maxCalories;
    }

    public java.util.List<Food> getFoodItems() {
        return foods;
    }

    public void addFoodItem(Food item) {
        foods.add(item);
    }

    public int getTotalCalories() {
        int total = 0;
        for (Food item : foods) {
            total += item.getCalories();
        }
        return total;
    }

    public int getTotalCarbs() {
        int total = 0;
        for (Food item : foods) {
//            total += item.getCarbs();
        }
        return total;
    }

    public int getTotalProtein() {
        int total = 0;
        for (Food item : foods) {
//            total += item.getProtein();
        }
        return total;
    }

    public int getTotalFat() {
        int total = 0;
        for (Food item : foods) {
//            total += item.getFat();
        }
        return total;
    }
}