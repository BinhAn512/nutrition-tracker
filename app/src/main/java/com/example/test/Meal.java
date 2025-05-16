package com.example.test;

public class Meal {
    private int id;
    private String name;
    private int icon;
    private int maxCalories;
    private java.util.List<FoodItem> foodItems;

    public Meal(int id, String name, int icon, int maxCalories) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.maxCalories = maxCalories;
        this.foodItems = new java.util.ArrayList<>();
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

    public java.util.List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    public int getTotalCalories() {
        int total = 0;
        for (FoodItem item : foodItems) {
            total += item.getCalories();
        }
        return total;
    }

    public int getTotalCarbs() {
        int total = 0;
        for (FoodItem item : foodItems) {
            total += item.getCarbs();
        }
        return total;
    }

    public int getTotalProtein() {
        int total = 0;
        for (FoodItem item : foodItems) {
            total += item.getProtein();
        }
        return total;
    }

    public int getTotalFat() {
        int total = 0;
        for (FoodItem item : foodItems) {
            total += item.getFat();
        }
        return total;
    }
}