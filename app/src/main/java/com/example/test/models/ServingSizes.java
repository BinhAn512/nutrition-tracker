package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class ServingSizes {
    @SerializedName("serving_id")
    private int id;

    @SerializedName("food_id")
    private int foodId;

    @SerializedName("serving_name")
    private String servingName;

    @SerializedName("serving_weight")
    private float servingWeight;

    @SerializedName("serving_unit")
    private String servingUnit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getServingName() {
        return servingName;
    }

    public void setServingName(String servingName) {
        this.servingName = servingName;
    }

    public float getServingWeight() {
        return servingWeight;
    }

    public void setServingWeight(float servingWeight) {
        this.servingWeight = servingWeight;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }
}
