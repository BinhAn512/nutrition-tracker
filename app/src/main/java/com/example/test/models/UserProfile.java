package com.example.test.models;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("profile_id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("age")
    private int age;

    @SerializedName("gender")
    private String gender;

    @SerializedName("height")
    private float height;

    @SerializedName("weight")
    private float weight;

    @SerializedName("weight_goal")
    private String weight_goal;

    @SerializedName("daily_calorie_goal")
    private int dailyCalorieGoal;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getWeight_goal() {
        return weight_goal;
    }

    public void setWeight_goal(String weight_goal) {
        this.weight_goal = weight_goal;
    }

    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public void setDailyCalorieGoal(int dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }
}
