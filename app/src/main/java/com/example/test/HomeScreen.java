package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity implements MealAdapter.OnMealClickListener {

    private TextView dateTextView;
    private TextView eatenCaloriesTextView;
    private CalorieCircleView calorieCircleView;
    private NutrientCircleView carbsCircleView;
    private NutrientCircleView proteinCircleView;
    private NutrientCircleView fatCircleView;
    private RecyclerView mealsRecyclerView;
    private MealAdapter mealAdapter;

    private DailyNutrition dailyNutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        initViews();
        setupNavigationView();
        setupDateControls();
        initData();
        updateUI();


    }

    private void initViews() {
        dateTextView = findViewById(R.id.dateTextView);
        eatenCaloriesTextView = findViewById(R.id.eatenCaloriesTextView);
        calorieCircleView = findViewById(R.id.calorieCircleView);
        carbsCircleView = findViewById(R.id.carbsCircleView);
        proteinCircleView = findViewById(R.id.proteinCircleView);
        fatCircleView = findViewById(R.id.fatCircleView);
        mealsRecyclerView = findViewById(R.id.mealsRecyclerView);

        // Set up RecyclerView
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Already in home, do nothing or reload if needed
                return true;
            } else if (itemId == R.id.navigation_insights) {
                Intent intent = new Intent(HomeScreen.this, InsightsScreen.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.navigation_account) {
                Intent intent = new Intent(HomeScreen.this, ProfileScreen.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });
    }

    private void setupDateControls() {
        // Set current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        String today = "Today, " + dateFormat.format(new Date());
        dateTextView.setText(today);

        // Set date navigation
        ImageButton prevDateButton = findViewById(R.id.prevDateButton);
        ImageButton nextDateButton = findViewById(R.id.nextDateButton);

        prevDateButton.setOnClickListener(v -> {
            Toast.makeText(HomeScreen.this, "Previous day", Toast.LENGTH_SHORT).show();
            // Implement date change logic here
        });

        nextDateButton.setOnClickListener(v -> {
            Toast.makeText(HomeScreen.this, "Next day", Toast.LENGTH_SHORT).show();
            // Implement date change logic here
        });
    }

    private void initData() {
        // Create meal data
        List<Meal> meals = new ArrayList<>();

        Meal breakfast = new Meal(1, "Breakfast", R.drawable.ic_breakfast, 708);
        Meal lunch = new Meal(2, "Lunch", R.drawable.ic_lunch, 708);
        Meal dinner = new Meal(3, "Dinner", R.drawable.ic_dinner, 708);

        meals.add(breakfast);
        meals.add(lunch);
        meals.add(dinner);

        // Create daily nutrition data
        dailyNutrition = new DailyNutrition(dateTextView.getText().toString(), meals);

        // Set up adapter
        mealAdapter = new MealAdapter(meals, this);
        mealsRecyclerView.setAdapter(mealAdapter);
    }

    private void updateUI() {
        // Update calorie stats
        int totalCalories = dailyNutrition.getTotalCalories();
        eatenCaloriesTextView.setText(String.valueOf(totalCalories));

        // Update the main calorie circle
        calorieCircleView.setCaloriesLeft(dailyNutrition.getCaloriesLeft());

        // Update nutrient circles
        carbsCircleView.setNutrientData(dailyNutrition.getTotalCarbs(), 224);
        proteinCircleView.setNutrientData(dailyNutrition.getTotalProtein(), 98);
        fatCircleView.setNutrientData(dailyNutrition.getTotalFat(), 85);
    }

    @Override
    public void onAddFoodClick(Meal meal) {
        // Launch add food activity
        Intent intent = new Intent(this, FoodAdd.class);
        intent.putExtra("MEAL_ID", meal.getId());  // Truyền meal ID sang FoodAdd nếu cần
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            // Handle the added food
            updateUI();
        }
    }
}