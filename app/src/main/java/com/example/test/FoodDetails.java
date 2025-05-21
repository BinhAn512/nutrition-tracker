package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.test.api.ApiService;
import com.example.test.models.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetails extends AppCompatActivity {
    private TextView tvFoodName;
    private TextView tvCaloriesValue;
    private TextView tvCarbsValue;
    private TextView tvProteinValue;
    private TextView tvFatValue;
    private TextView tvCholesterol;
    private TextView tvSodium;
    private TextView tvFat;
    private TextView tvSaturateFat;
    private TextView tvMonosaturateFat;
    private TextView tvPolyunsaturateFat;
    private TextView tvSugars;
    private TextView tvDietaryFiber;
    private TextView tvWater;

    private TextView tvCalcium;
    private TextView tvIron;
    private TextView tvPotassium;
    private TextView tvCopper;
    private TextView tvMagnesium;
    private TextView tvManganese;
    private TextView tvPhosphorus;
    private TextView tvSelenium;
    private TextView tvZinc;

    private TextView tvVitaminA;
    private TextView tvVitaminB1;
    private TextView tvVitaminB11;
    private TextView tvVitaminB12;
    private TextView tvVitaminB2;
    private TextView tvVitaminB3;
    private TextView tvVitaminB5;
    private TextView tvVitaminB6;
    private TextView tvVitaminC;
    private TextView tvVitaminD;
    private TextView tvVitaminE;
    private TextView tvVitaminK;

   @Override
    public void onCreate (Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_food_details);
        AssignView();

       ImageButton btn_back = findViewById(R.id.btnBack);
       btn_back.setOnClickListener(v -> {
           finish(); // Quay lại màn trước
       });

       Intent intent = getIntent();
       int foodIndex = intent.getIntExtra("foodIndex", -1);
       GetFoodInfo(foodIndex);

       AppCompatButton btn_add = findViewById(R.id.btnAdd);
       btn_add.setOnClickListener(v -> {
           finish();
       });
   }

   private void AssignView() {
       tvFoodName = findViewById(R.id.foodName);
       tvCaloriesValue = findViewById(R.id.caloriesValue);
       tvCarbsValue = findViewById(R.id.carbsValue);
       tvProteinValue = findViewById(R.id.proteinValue);
       tvFatValue = findViewById(R.id.fatValue);
       tvCholesterol = findViewById(R.id.cholesterol);
       tvSodium = findViewById(R.id.sodium);
       tvCalcium = findViewById(R.id.calcium);
       tvIron = findViewById(R.id.iron);
       tvPotassium = findViewById(R.id.potassium);
   }

   private void GetFoodInfo(int foodIndex) {
       Log.d("food index", String.valueOf(foodIndex));
       ApiService.apiService.getFood(foodIndex).enqueue(new Callback<Food>() {
           @Override
           public void onResponse(Call<Food> call, Response<Food> response) {
               if (response.isSuccessful() && response.body() != null) {
                   Food food = response.body();
//                   Log.d("Api res name", food.getName());
                   tvFoodName.setText(food.getName());
                   tvCaloriesValue.setText(String.valueOf(food.getCalories()));
               }
           }

           @Override
           public void onFailure(Call<Food> call, Throwable t) {

           }
       });
   }
}
