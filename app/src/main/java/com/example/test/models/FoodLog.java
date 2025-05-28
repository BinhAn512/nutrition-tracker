package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class FoodLog {
    @SerializedName("log_id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("food_id")
    private int foodId;

    @SerializedName("serving_size")
    private int servingSize;

    @SerializedName("serving_unit")
    private String servingUnit;

    @SerializedName("meal_type")
    private String mealType;

    @SerializedName("consumed_at")
    private String consumedAt;

    @SerializedName("notes")
    private String notes;

    public FoodLog(int userId, int foodId, int servingSize, String servingUnit, String mealType, String notes) {
        this.userId = userId;
        this.foodId = foodId;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
        this.mealType = mealType;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getConsumedAt() {
        return consumedAt;
    }

    public void setConsumedAt(String consumedAt) {
        this.consumedAt = consumedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
