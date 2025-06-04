package com.example.test;

import static com.example.test.FoodAdd.mealId;
import static com.example.test.FoodAdd.dateData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.test.api.ApiService;
import com.example.test.models.FavouriteFood;
import com.example.test.models.Food;
import com.example.test.models.FoodLog;
import com.example.test.models.Macronutrients;
import com.example.test.models.Minerals;
import com.example.test.models.Vitamins;

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

    private ImageView btnIncrease;
    private ImageView btnDecrease;
    private TextView tvQuantity;
    private ImageButton btnFavourite;
    private boolean isFavourite;
    int quantity;
    int foodId;

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
       foodId = intent.getIntExtra("foodId", -1);
       GetFoodInfo(foodId);

       AppCompatButton btn_add = findViewById(R.id.btnAdd);
       btn_add.setOnClickListener(v -> {
           // Log Food
           LogFood();
           finish();
       });

       CheckFavouriteFood();

       btnIncrease.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               quantity = Integer.valueOf(tvQuantity.getText().toString());
               quantity++;
               tvQuantity.setText(String.valueOf(quantity));
           }
       });

       btnDecrease.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               quantity = Integer.valueOf(tvQuantity.getText().toString());

               if (quantity == 0) return;
               quantity--;
               tvQuantity.setText(String.valueOf(quantity));
           }
       });

       btnFavourite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               isFavourite = !isFavourite;
               if (isFavourite) {
                   // Add to favourite list
                   AddToFavourite();
               } else {
                   // Remove from favourite list
                   RemoveFromFavourite();
               }
           }
       });

       ImageButton btnBack = findViewById(R.id.btnBack);
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(FoodDetails.this, FoodAdd.class);
               startActivity(intent);
               finish();
           }
       });
   }

   private void AddToFavourite() {
       FavouriteFood favouriteFood = new FavouriteFood(HomeScreen.userId, foodId);
       ApiService.apiService.createFavouriteFood(favouriteFood).enqueue(new Callback<FavouriteFood>() {
           @Override
           public void onResponse(Call<FavouriteFood> call, Response<FavouriteFood> response) {
               btnFavourite.setImageResource(R.drawable.favourites);
               Toast.makeText(FoodDetails.this, "Favourite Food Added", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<FavouriteFood> call, Throwable t) {
               Toast.makeText(FoodDetails.this, "Added Failed", Toast.LENGTH_SHORT).show();
               Log.e("Favourite Add Failed", t.getMessage());
           }
       });
   }

   private void RemoveFromFavourite() {
        ApiService.apiService.deleteFavouriteFood(HomeScreen.userId, foodId).enqueue(new Callback<FavouriteFood>() {
            @Override
            public void onResponse(Call<FavouriteFood> call, Response<FavouriteFood> response) {
                btnFavourite.setImageResource(R.drawable.unfavourite);
                Toast.makeText(FoodDetails.this, "Favourite Food Removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FavouriteFood> call, Throwable t) {
                Toast.makeText(FoodDetails.this, "Remove Failed", Toast.LENGTH_SHORT).show();
                Log.e("Favourite Remove Failed", t.getMessage());
            }
        });
   }

   private void AssignView() {
       btnDecrease = findViewById(R.id.btnQuantityMinus);
       btnIncrease = findViewById(R.id.btnQuantityAdd);
       tvQuantity = findViewById(R.id.quantityText);
       btnFavourite = findViewById(R.id.btnFavorite);

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
       tvFat = findViewById(R.id.fat);
       tvSaturateFat = findViewById(R.id.saturatedFat);
       tvMonosaturateFat = findViewById(R.id.monosaturatedFat);
       tvPolyunsaturateFat = findViewById(R.id.polyunsaturatedFat);
       tvSugars = findViewById(R.id.sugars);
       tvDietaryFiber = findViewById(R.id.dietaryFiber);
       tvWater = findViewById(R.id.water);

       tvCalcium = findViewById(R.id.calcium);
       tvIron = findViewById(R.id.iron);
       tvPotassium = findViewById(R.id.potassium);
       tvCopper = findViewById(R.id.copper);
       tvMagnesium = findViewById(R.id.magnesium);
       tvManganese = findViewById(R.id.manganese);
       tvPhosphorus = findViewById(R.id.phosphorus);
       tvSelenium = findViewById(R.id.selenium);
       tvZinc = findViewById(R.id.zinc);

       tvVitaminA = findViewById(R.id.vitaminA);
       tvVitaminB1 = findViewById(R.id.vitaminB1);
       tvVitaminB11 = findViewById(R.id.vitaminB11);
       tvVitaminB12 = findViewById(R.id.vitaminB12);
       tvVitaminB2 = findViewById(R.id.vitaminB2);
       tvVitaminB3 = findViewById(R.id.vitaminB3);
       tvVitaminB5 = findViewById(R.id.vitaminB5);
       tvVitaminB6 = findViewById(R.id.vitaminB6);
       tvVitaminC = findViewById(R.id.vitaminC);
       tvVitaminD = findViewById(R.id.vitaminD);
       tvVitaminE = findViewById(R.id.vitaminE);
       tvVitaminK = findViewById(R.id.vitaminK);
   }

    public void LogFood() {
        // Get Meal based on Meal Id
        String meal;
        if (mealId == 1) {
            meal = "breakfast";
        } else if (mealId == 2) {
            meal = "lunch";
        } else if (mealId == 3) {
            meal = "dinner";
        } else {
            Toast.makeText(FoodDetails.this, "Invalid meal", Toast.LENGTH_SHORT).show();
            return;
        }

        FoodLog foodLog = new FoodLog(HomeScreen.userId, foodId,
                Integer.valueOf(tvQuantity.getText().toString()), "kg", meal, dateData, "");
        ApiService.apiService.createFoodLog(foodLog).enqueue(new Callback<FoodLog>() {
            @Override
            public void onResponse(Call<FoodLog> call, Response<FoodLog> response) {
                Toast.makeText(FoodDetails.this, "Food logged!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FoodLog> call, Throwable t) {
                Toast.makeText(FoodDetails.this, "Log failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckFavouriteFood() {
       ApiService.apiService.getFavouriteFood(HomeScreen.userId, foodId).enqueue(new Callback<FavouriteFood>() {
           @Override
           public void onResponse(Call<FavouriteFood> call, Response<FavouriteFood> response) {
               FavouriteFood favouriteFood = response.body();
               if (response.isSuccessful() && favouriteFood != null) {
                    isFavourite = true;
                   btnFavourite.setImageResource(R.drawable.favourites);
               } else {
                   isFavourite = false;
                   btnFavourite.setImageResource(R.drawable.unfavourite);
               }
           }

           @Override
           public void onFailure(Call<FavouriteFood> call, Throwable t) {
                isFavourite = false;
                btnFavourite.setImageResource(R.drawable.unfavourite);
                Log.e("Favourite Call", "Call Failed!");
           }
       });
    }

   private void GetFoodInfo(int foodIndex) {
       Log.d("food index", String.valueOf(foodIndex));
       ApiService.apiService.getFood(foodIndex).enqueue(new Callback<Food>() {
           @Override
           public void onResponse(Call<Food> call, Response<Food> response) {
               if (response.isSuccessful() && response.body() != null) {
                   Food food = response.body();
                   tvFoodName.setText(food.getName());
                   tvCaloriesValue.setText(String.valueOf(food.getCalories()));
               }
           }

           @Override
           public void onFailure(Call<Food> call, Throwable t) {

           }
       });

       ApiService.apiService.getMacro(foodIndex).enqueue(new Callback<Macronutrients>() {
           @Override
           public void onResponse(Call<Macronutrients> call, Response<Macronutrients> response) {
               Macronutrients macro = response.body();

               if (response.isSuccessful() && macro != null) {
                   tvCarbsValue.setText(String.valueOf(macro.getCarbs()));
                   tvProteinValue.setText(String.valueOf(macro.getProtein()));
                   tvFatValue.setText(String.valueOf(macro.getFat()));

                   tvFat.setText(macro.getFat() + " g");
                   tvSaturateFat.setText(macro.getSaturatedFats() + " g");
                   tvMonosaturateFat.setText(macro.getMonosaturatedFats() + " g");
                   tvPolyunsaturateFat.setText(macro.getMonosaturatedFats() + " g");
                   tvSugars.setText(macro.getSugars() + " g");
                   tvDietaryFiber.setText(macro.getDietaryFiber() + " g");
                   tvWater.setText(macro.getWater() + " g");
                   tvCholesterol.setText(macro.getCholesterol() + " mg");
                   tvSodium.setText(macro.getSodium() + " g");
               }
           }

           @Override
           public void onFailure(Call<Macronutrients> call, Throwable t) {

           }
       });

       ApiService.apiService.getMineral(foodIndex).enqueue(new Callback<Minerals>() {
           @Override
           public void onResponse(Call<Minerals> call, Response<Minerals> response) {
               Minerals minerals = response.body();

               if (response.isSuccessful() && minerals != null) {
                   tvCalcium.setText(minerals.getCalcium() + " mg");
                   tvIron.setText(minerals.getIron() + " mg");
                   tvPotassium.setText(minerals.getPotassium() + " mg");
                   tvCopper.setText(minerals.getCopper() + " mg");
                   tvMagnesium.setText(minerals.getMagnesium() + " mg");
                   tvManganese.setText(minerals.getManganese() + " mg");
                   tvPhosphorus.setText(minerals.getPhosphorus() + " mg");
                   tvSelenium.setText(minerals.getSelenium() + " mg");
                   tvZinc.setText(minerals.getZinc() + " mg");
               }
           }

           @Override
           public void onFailure(Call<Minerals> call, Throwable t) {

           }
       });

       ApiService.apiService.getVitamin(foodIndex).enqueue(new Callback<Vitamins>() {
           @Override
           public void onResponse(Call<Vitamins> call, Response<Vitamins> response) {

               Vitamins vitamins = response.body();

               if (response.isSuccessful() && vitamins != null) {

                   tvVitaminA.setText(vitamins.getVitaminA() + " mg");
                   tvVitaminB1.setText(vitamins.getVitaminB1() + " mg");
                   tvVitaminB11.setText(vitamins.getVitaminB11() + " mg");
                   tvVitaminB12.setText(vitamins.getVitaminB12() + " mg");
                   tvVitaminB2.setText(vitamins.getVitaminB2() + " mg");
                   tvVitaminB3.setText(vitamins.getVitaminB3() + " mg");
                   tvVitaminB5.setText(vitamins.getVitaminB5() + " mg");
                   tvVitaminB6.setText(vitamins.getVitaminB6() + " mg");
                   tvVitaminC.setText(vitamins.getVitaminC() + " mg");
                   tvVitaminD.setText(vitamins.getVitaminD() + " mg");
                   tvVitaminE.setText(vitamins.getVitaminE() + " mg");
                   tvVitaminK.setText(vitamins.getVitaminK() + " mg");
               }
           }

           @Override
           public void onFailure(Call<Vitamins> call, Throwable t) {

           }
       });
   }
}
