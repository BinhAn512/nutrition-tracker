package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.adapters.MealAdapter;
import com.example.test.api.ApiService;
import com.example.test.models.Food;
import com.example.test.models.FoodLog;
import com.example.test.models.FoodNutrition;
import com.example.test.models.Macronutrients;
import com.example.test.views.CalorieCircleView;
import com.example.test.views.NutrientCircleView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity implements MealAdapter.OnMealClickListener {

    private TextView dateTextView;
    private TextView eatenCaloriesTextView;
    private CalorieCircleView calorieCircleView;
    private NutrientCircleView carbsCircleView;
    private NutrientCircleView proteinCircleView;
    private NutrientCircleView fatCircleView;
    private RecyclerView mealsRecyclerView;
    private MealAdapter mealAdapter;
    private Calendar currentDate;

    private DailyNutrition dailyNutrition;
    public static int userId;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                bundle = intent.getExtras();
                userId = bundle.getInt("USER_ID");
            }
        } catch (Exception e) {

        }

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
        currentDate = Calendar.getInstance();

        // Set current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        updateDateData();

        // Set date navigation
        ImageButton prevDateButton = findViewById(R.id.prevDateButton);
        ImageButton nextDateButton = findViewById(R.id.nextDateButton);

        prevDateButton.setOnClickListener(v -> {
            Toast.makeText(HomeScreen.this, "Previous day", Toast.LENGTH_SHORT).show();
            // Implement date change logic here
            currentDate.add(Calendar.DAY_OF_MONTH, -1);
            updateDateData();
        });

        nextDateButton.setOnClickListener(v -> {
            Toast.makeText(HomeScreen.this, "Next day", Toast.LENGTH_SHORT).show();
            // Implement date change logic here
            currentDate.add(Calendar.DAY_OF_MONTH, +1);
            updateDateData();
        });
    }

    // Hàm cập nhật ngày lên TextView
    private void updateDateData() {
        // Update time
        SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat displayDateFormat = new SimpleDateFormat(" EEEE, dd MMM", Locale.getDefault());
        String formattedDate = displayDateFormat.format(currentDate.getTime());
        dateTextView.setText(formattedDate);

        String dateData = dataDateFormat.format(currentDate.getTime());
        ApiService.apiService.getFoodByDate(dateData, userId).enqueue(new Callback<List<FoodNutrition>>() {
            @Override
            public void onResponse(Call<List<FoodNutrition>> call, Response<List<FoodNutrition>> response) {
                List<FoodNutrition> foods = response.body();
                int totalCalories = 0;
                int totalCarbs = 0;
                int totalProtein = 0;
                int totalFat = 0;
                if (response.isSuccessful() && foods != null) {
                    for (FoodNutrition food : foods) {
                        totalCalories += food.getCalories() * food.getServingSize();
                        totalFat += food.getFat() * food.getServingSize();
                        totalProtein += food.getProtein() * food.getServingSize();
                        totalCarbs += food.getCarbs() * food.getServingSize();
                    }
                    eatenCaloriesTextView.setText(String.valueOf(totalCalories));
                    carbsCircleView.setNutrientData(totalCarbs, 10);
                    proteinCircleView.setNutrientData(totalProtein, 100);
                    fatCircleView.setNutrientData(totalFat, 85);
                    calorieCircleView.setCalorieData(totalCarbs, 2560);
                }
            }

            @Override
            public void onFailure(Call<List<FoodNutrition>> call, Throwable t) {

            }
        });

//        ApiService.apiService.getMacroByDate(dateData).enqueue(new Callback<List<Macronutrients>>() {
//            @Override
//            public void onResponse(Call<List<Macronutrients>> call, Response<List<Macronutrients>> response) {
//                List<Macronutrients> macros = response.body();
//                int totalCarbs = 0;
//                int totalProtein = 0;
//                int totalFat = 0;
//                if (response.isSuccessful() && macros != null) {
//                    for (Macronutrients macro : macros) {
//                        totalFat += macro.getFat();
//                        totalProtein += macro.getProtein();
//                        totalCarbs += macro.getCarbs();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Macronutrients>> call, Throwable t) {
//
//            }
//        });

//        ApiService.apiService.getFoodByMealDate(dateData, "breakfast").enqueue(new Callback<List<Food>>() {
//            @Override
//            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
//                List<Food> foods = response.body();
//                int totalCalories = 0;
//                if (response.isSuccessful() && foods != null) {
//                    for (Food food : foods) {
//                        totalCalories += food.getCalories();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Food>> call, Throwable t) {
//
//            }
//        });
        // Update nutritions data in that day

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
//        int totalCalories = dailyNutrition.getTotalCalories();
//        eatenCaloriesTextView.setText(String.valueOf(totalCalories));

        // Update the main calorie circle
//        calorieCircleView.setCaloriesLeft(dailyNutrition.getCaloriesLeft());

        // Update nutrient circles
//        carbsCircleView.setNutrientData(dailyNutrition.getTotalCarbs(), 224);
//        proteinCircleView.setNutrientData(dailyNutrition.getTotalProtein(), 98);
//        fatCircleView.setNutrientData(dailyNutrition.getTotalFat(), 85);
    }

    @Override
    public void onAddFoodClick(Meal meal) {
        // Launch add food activity
        Intent intent = new Intent(this, FoodAdd.class);
        intent.putExtra("MEAL_ID", meal.getId());  // Truyền meal ID sang FoodAdd nếu cần

        SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateData = dataDateFormat.format(currentDate.getTime());
        intent.putExtra("CURRENT_DATE", dateData);
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