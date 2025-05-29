package com.example.test;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.adapters.FoodAdapter;
import com.example.test.api.ApiService;
import com.example.test.models.FavouriteFood;
import com.example.test.models.Food;
import com.example.test.models.FoodLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodAdd extends AppCompatActivity implements FoodAdapter.OnFoodItemClickListener {

    private RecyclerView foodListRecyclerView;
    private FoodAdapter foodAdapter;
    private TextView tabRecent;
    private TextView tabFavorites;
    private ConstraintLayout selectionContainer;
    private TextView kcalValue;
    private Button btnAddSelection;
    List<Food> foods;
    List<FavouriteFood> favouriteFoods;
    public static int mealId;
    public static String dateData;
    int selectedFoodPosition;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);

        intent = getIntent();
        // add day selected
        dateData = intent.getStringExtra("CURRENT_DATE");
        mealId = intent.getIntExtra("MEAL_ID", -1);

        // Initialize views
        foodListRecyclerView = findViewById(R.id.food_list);
        tabRecent = findViewById(R.id.tab_recent);
        tabFavorites = findViewById(R.id.tab_favorites);
        selectionContainer = findViewById(R.id.selection_container);
        kcalValue = findViewById(R.id.kcal_value);
        btnAddSelection = findViewById(R.id.btn_add_selection);
        foods = new ArrayList<>();
        favouriteFoods = new ArrayList<>();

        boolean showFavorites = intent.getBooleanExtra("SHOW_FAVORITES", false);
        if (showFavorites) {
            setActiveTab(tabFavorites);
        } else {
            setActiveTab(tabRecent);
        }

        // Set up tab click listeners
        tabRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTab(tabRecent);
            }
        });

        tabFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTab(tabFavorites);
            }
        });

//        FetchFoodData();

        // Set click listener for Add button
        btnAddSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement add functionality
                // For example: add to meal plan, history, etc.
                LogFood();

                // Reset selection and hide container
                foodAdapter.setSelectedPosition(-1);
                selectionContainer.setVisibility(View.GONE);
            }
        });

        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> {
            intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
            finish();
        });


    }

    private void setActiveTab(TextView activeTab) {
        // Reset all tabs
        tabRecent.setBackground(null);
        tabRecent.setTextColor(getResources().getColor(android.R.color.darker_gray));

        tabFavorites.setBackground(null);
        tabFavorites.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Set active tab
        activeTab.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
        activeTab.setTextColor(getResources().getColor(android.R.color.black));

        // Change list of food
        if (activeTab.getId() == tabFavorites.getId()) {
            FetchFavouriteFoodData();
        } else {
            FetchFoodData();
        }
    }

    @Override
    public void onFoodItemClick(Food item, int position) {
        // Update adapter's selected position
        foodAdapter.setSelectedPosition(position);

        // Update bottom selection info
        kcalValue.setText(String.valueOf(item.getCalories()));

        // Show selection container
        selectionContainer.setVisibility(View.VISIBLE);

        // Update the add button text to show count
        btnAddSelection.setText("Add (1)");

        selectedFoodPosition = position;
    }

    private void FetchFoodData() {
        // Set up RecyclerView
        foodListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example food data (sushi items based on the screenshot)


//        foods.add(new Food("Sushi, Salmon (Sake)", 48, 30));
//        foods.add(new Food("Sushi, Yellowtail (Hamachi)", 65, 30));
//        foods.add(new Food("Sushi, Eel (Unagi)", 100, 35));

        ApiService.apiService.getFoods().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                foods = response.body();
                Log.d("Foods", String.valueOf(foods.get(1).getName()));
                // Set up adapter
                foodAdapter = new FoodAdapter(foods, FoodAdd.this);
                foodListRecyclerView.setAdapter(foodAdapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.e("Api Connection Failed", "Failed");
            }
        });
    }

    private void FetchFavouriteFoodData() {
        // Set up RecyclerView
        foodListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example food data (sushi items based on the screenshot)


//        foods.add(new Food("Sushi, Salmon (Sake)", 48, 30));
//        foods.add(new Food("Sushi, Yellowtail (Hamachi)", 65, 30));
//        foods.add(new Food("Sushi, Eel (Unagi)", 100, 35));

        ApiService.apiService.getFavouriteFoods(1).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                foods = response.body();
//                Log.d("Foods", String.valueOf(foods.get(1).getName()));
                // Set up adapter
                foodAdapter = new FoodAdapter(foods, FoodAdd.this);
                foodListRecyclerView.setAdapter(foodAdapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.e("Api Connection Failed", "Failed");
            }
        });
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
            Toast.makeText(FoodAdd.this, "Invalid meal", Toast.LENGTH_SHORT).show();
            return;
        }
        Food selectedFood = foodAdapter.getSelectedItem();

        FoodLog foodLog = new FoodLog(1, selectedFood.getId(),
                1, "kg", meal, dateData, "");
        ApiService.apiService.createFoodLog(foodLog).enqueue(new Callback<FoodLog>() {
            @Override
            public void onResponse(Call<FoodLog> call, Response<FoodLog> response) {
                Toast.makeText(FoodAdd.this, "Food logged!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FoodLog> call, Throwable t) {
                Toast.makeText(FoodAdd.this, "Log failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}