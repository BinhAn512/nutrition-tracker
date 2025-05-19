package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class FavouriteFood {
    @SerializedName("favourite_food_id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("food_id")
    private int foodId;

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
}
