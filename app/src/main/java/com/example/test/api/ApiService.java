package com.example.test.api;

import com.example.test.models.FavouriteFood;
import com.example.test.models.Food;
import com.example.test.models.FoodLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    @GET("favoutite_food/{id}")
    Call<FavouriteFood> getFavouriteFood(@Path("id") int favouriteFoodId);

    @GET("food_log/{id}")
    Call<FoodLog> getFoodLog(@Path("id") int foodLogId);
}