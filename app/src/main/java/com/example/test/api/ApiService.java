package com.example.test.api;

import com.example.test.models.FavouriteFood;
import com.example.test.models.Food;
import com.example.test.models.FoodLog;
import com.example.test.models.FoodNutrition;
import com.example.test.models.Macronutrients;
import com.example.test.models.Minerals;
import com.example.test.models.Vitamins;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("foods")
    Call<List<Food>> getFoods();
    @GET("food/{id}")
    Call<Food> getFood(@Path("id") int foodId);

    @GET("macronutrient/{id}")
    Call<Macronutrients> getMacro(@Path("id") int foodId);

    @GET("mineral/{id}")
    Call<Minerals> getMineral(@Path("id") int foodId);

    @GET("vitamin/{id}")
    Call<Vitamins> getVitamin(@Path("id") int foodId);

    @GET("favourite_foods/{user_id}")
    Call<List<Food>> getFavouriteFoods(@Path("user_id") int userId);
    @GET("favourite_food")
    Call<FavouriteFood> getFavouriteFood(@Query("user_id") int userId,
                                         @Query("food_id") int foodId);
    @POST("favourite_food")
    Call<FavouriteFood> createFavouriteFood(@Body FavouriteFood favouriteFood);
    @DELETE("favourite_food")
    Call<FavouriteFood> deleteFavouriteFood(@Query("user_id") int userId,
                                            @Query("food_id") int foodId);

    @GET("food_log/{id}")
    Call<FoodLog> getFoodLog(@Path("id") int foodLogId);
    @GET("food_logs/{date}")
    Call<List<FoodLog>> getFoodLogByDate(@Path("date") String date);
    @GET("food_logs/food/{date}")
    Call<List<FoodNutrition>> getFoodByDate(@Path("date") String date);
    @GET("food_logs/food")
    Call<List<Food>> getFoodByMealDate(@Query("date") String date,
                                       @Query("meal_type") String mealType);
    @GET("food_logs/macro/{date}")
    Call<List<Macronutrients>> getMacroByDate(@Path("date") String date);
    @POST("food_log")
    Call<FoodLog> createFoodLog(@Body FoodLog foodLog);
}