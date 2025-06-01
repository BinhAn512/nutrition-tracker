package com.example.test;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodAdd extends AppCompatActivity implements FoodAdapter.OnFoodItemClickListener {

    private RecyclerView foodListRecyclerView;
    private FoodAdapter foodAdapter;
    private TextView tvTitle;
    private TextView tabRecent;
    private TextView tabFavorites;
    private ConstraintLayout selectionContainer;
    private TextView kcalValue;
    private Button btnAddSelection;
    private EditText searchInput;

    List<Food> originalFoods;
    List<Food> foods; // Store original list for search filtering
    List<FavouriteFood> favouriteFoods;

    public static int mealId;
    public static String dateData;
    int selectedFoodPosition;
    Intent intent;

    private boolean isShowingFavorites = false;

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
        searchInput = findViewById(R.id.search_input);
        tvTitle = findViewById(R.id.title_text);

        foods = new ArrayList<>();
        originalFoods = new ArrayList<>();
        favouriteFoods = new ArrayList<>();

        setupTitle();

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

        // Set up search functionality
        setupSearchFeature();

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

    private void setupTitle() {
        if (mealId == 1) {
            tvTitle.setText("Breakfast");
        } else if (mealId == 2) {
            tvTitle.setText("Lunch");
        } else if (mealId == 3) {
            tvTitle.setText("Dinner");
        } else {
            Toast.makeText(FoodAdd.this, "Invalid meal", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void setupSearchFeature() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the food list as user types
                filterFoodList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    private void filterFoodList(String query) {
        if (originalFoods == null || originalFoods.isEmpty()) {
            return;
        }

        List<Food> filteredList = new ArrayList<>();

        if (query.trim().isEmpty()) {
            // If search is empty, show all foods
            filteredList.addAll(originalFoods);
        } else {
            // Filter foods based on name (case-insensitive)
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Food food : originalFoods) {
                if (food.getName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(food);
                }
            }
        }
        // Update the adapter with filtered list
        foods.clear();
        foods.addAll(filteredList);

        if (foodAdapter != null) {
            foodAdapter.notifyDataSetChanged();
            // Reset selection when search results change
            foodAdapter.setSelectedPosition(-1);
            selectionContainer.setVisibility(View.GONE);
        }
    }

    private void setActiveTab(TextView activeTab) {

        // Clear search when switching tabs
        searchInput.setText("");

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
        btnAddSelection.setText("Add");

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
                if (response.body() != null) {
                    originalFoods.clear();
                    originalFoods.addAll(response.body());

                    foods.clear();
                    foods.addAll(response.body());
                    Log.d("Foods", String.valueOf(originalFoods.get(1).getName()));
                    // Set up adapter
                    foodAdapter = new FoodAdapter(foods, FoodAdd.this);
                    foodListRecyclerView.setAdapter(foodAdapter);
                }
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

        ApiService.apiService.getFavouriteFoods(HomeScreen.userId).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.body() != null) {
                    originalFoods.clear();
                    originalFoods.addAll(response.body());

                    foods.clear();
                    foods.addAll(response.body());
//                Log.d("Foods", String.valueOf(foods.get(1).getName()));
                    // Set up adapter
                    foodAdapter = new FoodAdapter(foods, FoodAdd.this);
                    foodListRecyclerView.setAdapter(foodAdapter);
                }

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

        FoodLog foodLog = new FoodLog(HomeScreen.userId, selectedFood.getId(),
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