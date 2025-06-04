package com.example.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test.api.ApiService;
import com.example.test.models.FoodNutrition;
import com.example.test.models.Macronutrients;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightsScreen extends AppCompatActivity {

    private BarChart calorieBarChart;
    private BarChart nutritionStackedBarChart;
    private TextView tvDateRange;
    private ImageView ivPrevious, ivNext;

    // Calendar management
    private Calendar currentWeekStart;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dayFormat;

    // Sample data storage
    private Map<String, DayData> weeklyData;

    private TextView tvCarbsPercent, tvProteinPercent, tvFatPercent;

    // Callback interface for async data loading
    public interface ChartDataCallback {
        void onDataReady(ArrayList<BarEntry> entries);
        void onError(String error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);
        
        initializeViews();
        initializeCalendar();
        initializeSampleData();
        setupNavigationListeners();
        setupNavigationView();

        // Initialize charts
        updateWeekDisplay();
        setupCalorieChart();
        setupNutritionChart();

//        ImageButton btn_account = findViewById(R.id.btn_ivLogo);
//        btn_account.setOnClickListener(v -> {
//            Intent intent = new Intent(this, ProfileScreen.class);
//            startActivity(intent);
//            finish();
//        });
    }

    private void setupNavigationListeners() {
        ivPrevious.setOnClickListener(v -> {
            currentWeekStart.add(Calendar.WEEK_OF_YEAR, -1);
            updateWeekDisplay();
            setupCalorieChart();
            setupNutritionChart();
        });

        ivNext.setOnClickListener(v -> {
            currentWeekStart.add(Calendar.WEEK_OF_YEAR, 1);
            updateWeekDisplay();
            setupCalorieChart();
            setupNutritionChart();
        });
    }

    private void updateWeekDisplay() {
        Calendar weekEnd = (Calendar) currentWeekStart.clone();
        weekEnd.add(Calendar.DAY_OF_MONTH, 6);

        String startDateStr = dateFormat.format(currentWeekStart.getTime());
        String endDateStr = dateFormat.format(weekEnd.getTime());

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        String yearStr = yearFormat.format(currentWeekStart.getTime());

        tvDateRange.setText(String.format("%s - %s, %s", startDateStr, endDateStr, yearStr));
    }

    private void initializeSampleData() {
        weeklyData = new HashMap<>();

        // Generate sample data for multiple weeks
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -4); // Start 4 weeks ago

        for (int week = 0; week < 8; week++) {
            for (int day = 0; day < 7; day++) {
                String dateKey = getDateKey(cal);

                // Generate varied sample data
                int baseCalorie = 1600 + (int)(Math.random() * 600);
                float carbs = 40f + (float)(Math.random() * 15);
                float protein = 15f + (float)(Math.random() * 15);
                float fat = 100f - carbs - protein;

                weeklyData.put(dateKey, new DayData(
                        baseCalorie,
                        carbs,
                        protein,
                        fat
                ));

                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private String getDateKey(Calendar cal) {
        SimpleDateFormat keyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return keyFormat.format(cal.getTime());
    }

    private String[] getCurrentWeekDays() {
        String[] days = new String[7];
        Calendar cal = (Calendar) currentWeekStart.clone();

        for (int i = 0; i < 7; i++) {
            days[i] = dayFormat.format(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return days;
    }

    private ArrayList<BarEntry> getCurrentWeekCalorieData(ChartDataCallback callback) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar cal = (Calendar) currentWeekStart.clone();
        final int totalDays = 7;
        final AtomicInteger completedCalls = new AtomicInteger(0);
        final Map<Integer, Integer> dataMap = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            int index = i;
            String dateKey = getDateKey(cal);

            ApiService.apiService.getFoodByDate(dateKey, HomeScreen.userId).enqueue(new Callback<List<FoodNutrition>>() {
                @Override
                public void onResponse(Call<List<FoodNutrition>> call, Response<List<FoodNutrition>> response) {
                    int totalCalories = 0;
                    List<FoodNutrition> nutrition = response.body();

//                    Log.d("Nutrition size", String.valueOf(index));

                    if (response.isSuccessful() && nutrition != null && !nutrition.isEmpty()) {

                        for (FoodNutrition item : nutrition) {
                            totalCalories += item.getCalories();
                        }
                    }

                    synchronized (dataMap) {
                        dataMap.put(index, totalCalories);

                        if (completedCalls.incrementAndGet() == totalDays) {
                            // All calls completed, create entries in correct order
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            for (int j = 0; j < totalDays; j++) {
                                float calories = dataMap.getOrDefault(j, 0);
                                entries.add(new BarEntry(j, calories));
                            }
                            callback.onDataReady(entries);
                        }
                    }
                }

                    @Override
                    public void onFailure(Call<List<FoodNutrition>> call, Throwable t) {
                        synchronized (dataMap) {
                            dataMap.put(index, 0); // Default to 0 on failure

                            if (completedCalls.incrementAndGet() == totalDays) {
                                ArrayList<BarEntry> entries = new ArrayList<>();
                                for (int j = 0; j < totalDays; j++) {
                                    float calories = dataMap.getOrDefault(j, 0);
                                    entries.add(new BarEntry(j, calories));
                                }
                                callback.onDataReady(entries);
                            }
                        }
                    }
            });

            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return entries;
    }

    private void AddEntryData(ArrayList<BarEntry> entries, int index, int data) {
        entries.add((new BarEntry(index, data)));
    }

    private ArrayList<BarEntry> getCurrentWeekNutritionData(ChartDataCallback callback) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar cal = (Calendar) currentWeekStart.clone();

        final int totalDays = 7;
        final AtomicInteger completedCalls = new AtomicInteger(0);
        final Map<Integer, float[]> dataMap = new HashMap<>();
        final Map<String, Float> percentMap = new HashMap<String, Float>(){{
            put("carbs", 0f);
            put("protein", 0f);
            put("fat", 0f);
        }};

        for (int i = 0; i < 7; i++) {
            int index = i;
            String dateKey = getDateKey(cal);

            ApiService.apiService.getFoodByDate(dateKey, HomeScreen.userId).enqueue(new Callback<List<FoodNutrition>>() {
                @Override
                public void onResponse(Call<List<FoodNutrition>> call, Response<List<FoodNutrition>> response) {
                    float totalCarbs = 0f;
                    float totalProtein = 0f;
                    float totalFat = 0f;

                    List<FoodNutrition> nutrition = response.body();

//                    Log.d("Nutrition size", String.valueOf(index));

                    if (response.isSuccessful() && nutrition != null && !nutrition.isEmpty()) {

                        for (FoodNutrition item : nutrition) {
                            totalCarbs += item.getCarbs();
                            totalProtein += item.getProtein();
                            totalFat += item.getFat();
                        }
                    }

                    synchronized (dataMap) {
                        float totalMacros = totalCarbs + totalProtein + totalFat;

//                        Log.d("Total macro", String.valueOf(totalMacros));
                        if (totalMacros != 0) {
//                            Log.d("Carbs ratio", String.valueOf(totalCarbs / totalMacros * 100));
//                            Log.d("Protein ratio", String.valueOf(totalProtein / totalMacros * 100));
//                            Log.d("Fat ratio", String.valueOf(totalFat / totalMacros * 100));

                            float currentCarbsPercent = percentMap.getOrDefault("carbs", 0f);
                            currentCarbsPercent += totalCarbs;
                            percentMap.put("carbs", currentCarbsPercent);

                            float currentProteinPercent = percentMap.getOrDefault("protein", 0f);
                            currentProteinPercent += totalProtein;
                            percentMap.put("protein", currentProteinPercent);

                            float currentFatPercent = percentMap.getOrDefault("fat", 0f);
                            currentFatPercent += totalFat;
                            percentMap.put("fat", currentFatPercent);

                            float weeklyTotalMacros = currentCarbsPercent + currentProteinPercent + currentFatPercent;
                            float weeklyCarbsPercent = currentCarbsPercent / weeklyTotalMacros * 100;
                            float weeklyProteinPercent = currentProteinPercent / weeklyTotalMacros * 100;
                            float weeklyFatPercent = currentFatPercent / weeklyTotalMacros * 100;
                            tvCarbsPercent.setText(String.format("%.2f", weeklyCarbsPercent));
                            tvProteinPercent.setText(String.format("%.2f", weeklyProteinPercent));
                            tvFatPercent.setText(String.format("%.2f", weeklyFatPercent));

                            dataMap.put(index, new float[]{totalCarbs / totalMacros * 100,
                                    totalProtein / totalMacros * 100,
                                    totalFat / totalMacros * 100});
                        } else {
                            dataMap.put(index, new float[]{0, 0, 0});
                        }



                        if (completedCalls.incrementAndGet() == totalDays) {
                            // All calls completed, create entries in correct order
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            for (int j = 0; j < totalDays; j++) {
                                float[] macros = dataMap.getOrDefault(j, new float[]{0, 0, 0});
                                entries.add(new BarEntry(j, macros));
                            }
                            callback.onDataReady(entries);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<FoodNutrition>> call, Throwable t) {
                    synchronized (dataMap) {
                        dataMap.put(index, new float[]{0, 0, 0}); // Default to 0 on failure

                        if (completedCalls.incrementAndGet() == totalDays) {
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            for (int j = 0; j < totalDays; j++) {
                                float[] macros = dataMap.getOrDefault(j, new float[]{0, 0, 0});
                                entries.add(new BarEntry(j, macros));
                            }
                            callback.onDataReady(entries);
                        }
                    }
                }
            });
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d("Final Carbs Map", percentMap.get("carbs").toString());
        return entries;
    }


    private void initializeCalendar() {
        currentWeekStart = Calendar.getInstance();
        // Set to start of current week (Monday)
        currentWeekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        currentWeekStart.set(Calendar.HOUR_OF_DAY, 0);
        currentWeekStart.set(Calendar.MINUTE, 0);
        currentWeekStart.set(Calendar.SECOND, 0);
        currentWeekStart.set(Calendar.MILLISECOND, 0);

        dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    }

    private void initializeViews() {
        calorieBarChart = findViewById(R.id.calorieBarChart);
        nutritionStackedBarChart = findViewById(R.id.nutritionStackedBarChart);
        tvDateRange = findViewById(R.id.tvDateRange);
        ivPrevious = findViewById(R.id.ivPrevious);
        ivNext = findViewById(R.id.ivNext);
        tvCarbsPercent = findViewById(R.id.carbs_percent);
        tvProteinPercent = findViewById(R.id.protein_percent);
        tvFatPercent = findViewById(R.id.fat_percent);
    }

    private void setupCalorieChart() {
        calorieBarChart.getDescription().setEnabled(false);
        calorieBarChart.setDrawGridBackground(false);
        calorieBarChart.setDrawBarShadow(false);
        calorieBarChart.setDrawValueAboveBar(true);
        calorieBarChart.setPinchZoom(false);
        calorieBarChart.setScaleEnabled(false);
        calorieBarChart.setDoubleTapToZoomEnabled(false);

        tvCarbsPercent.setText("0");
        tvProteinPercent.setText("0");
        tvFatPercent.setText("0");

        // Set up X-axis
        XAxis xAxis = calorieBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);

        String[] days = getCurrentWeekDays();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // Set up Y-axis
        YAxis leftAxis = calorieBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(3000f);

        calorieBarChart.getAxisRight().setEnabled(false);

        // Hide legend
        Legend legend = calorieBarChart.getLegend();
        legend.setEnabled(false);

        // Add goal line
        LimitLine goalLine = new LimitLine(2000f, "");
        goalLine.setLineWidth(1f);
        goalLine.setLineColor(ContextCompat.getColor(this, R.color.Green));
        goalLine.enableDashedLine(10f, 10f, 0f);
        leftAxis.addLimitLine(goalLine);

        // Add current week data
        ArrayList<BarEntry> entries = getCurrentWeekCalorieData(new ChartDataCallback() {
            @Override
            public void onDataReady(ArrayList<BarEntry> entries) {
                // This runs on the main thread when data is ready
                updateCalorieChart(entries);
            }

            @Override
            public void onError(String error) {
                Log.e("CalorieChart", "Error loading data: " + error);
                // Fallback to empty data or show error message
                updateCalorieChart(new ArrayList<>());
            }
        });


    }

    // Separate method to update the chart once data is available
    private void updateCalorieChart(ArrayList<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "Calories");
        dataSet.setColors(ContextCompat.getColor(this, R.color.LightGreen));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);
        barData.setDrawValues(false);

        calorieBarChart.setData(barData);
        calorieBarChart.animateY(1000);
        calorieBarChart.invalidate();
    }

    private void setupNutritionChart() {
        nutritionStackedBarChart.getDescription().setEnabled(false);
        nutritionStackedBarChart.setDrawGridBackground(false);
        nutritionStackedBarChart.setPinchZoom(false);
        nutritionStackedBarChart.setScaleEnabled(false);
        nutritionStackedBarChart.setDoubleTapToZoomEnabled(false);

        // Set up X-axis
        XAxis xAxis = nutritionStackedBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);

        String[] days = getCurrentWeekDays();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // Set up Y-axis
        YAxis leftAxis = nutritionStackedBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setValueFormatter(new PercentFormatter());

        nutritionStackedBarChart.getAxisRight().setEnabled(false);

        // Hide legend
        Legend legend = nutritionStackedBarChart.getLegend();
        legend.setEnabled(false);

        // Add data for each nutrition type (carbs, protein, fat)
        ArrayList<BarEntry> entries = getCurrentWeekNutritionData(new ChartDataCallback() {
            @Override
            public void onDataReady(ArrayList<BarEntry> entries) {
                updateNutritionChart(entries);
            }

            @Override
            public void onError(String error) {

            }
        });


    }

    private void updateNutritionChart(ArrayList<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "Nutrition");
        dataSet.setColors(
                ContextCompat.getColor(this, R.color.Red),     // Carbs
                ContextCompat.getColor(this, R.color.Orange),  // Protein
                ContextCompat.getColor(this, R.color.Blue)     // Fat
        );
        dataSet.setStackLabels(new String[]{"Carbs", "Protein", "Fat"});

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);
        barData.setDrawValues(false);

        nutritionStackedBarChart.setData(barData);
        nutritionStackedBarChart.animateY(1000);
        nutritionStackedBarChart.invalidate();
    }

    private boolean isInCurrentWeek(Calendar date) {
        Calendar weekEnd = (Calendar) currentWeekStart.clone();
        weekEnd.add(Calendar.DAY_OF_MONTH, 6);

        return !date.before(currentWeekStart) && !date.after(weekEnd);
    }

    private int getTodayIndexInWeek(Calendar today) {
        Calendar cal = (Calendar) currentWeekStart.clone();

        for (int i = 0; i < 7; i++) {
            if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) &&
                    cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
                return i;
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return -1; // Today is not in current week
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                Intent intent = new Intent(InsightsScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.navigation_insights) {
                Intent intent = new Intent(InsightsScreen.this, InsightsScreen.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.navigation_account) {
                Intent intent = new Intent(InsightsScreen.this, ProfileScreen.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });
    }

    // Data class to hold daily nutrition and calorie data
    private static class DayData {
        private final float calories;
        private final float carbs;
        private final float protein;
        private final float fat;

        public DayData(float calories, float carbs, float protein, float fat) {
            this.calories = calories;
            this.carbs = carbs;
            this.protein = protein;
            this.fat = fat;
        }

        public float getCalories() { return calories; }
        public float getCarbsPercent() { return carbs; }
        public float getProteinPercent() { return protein; }
        public float getFatPercent() { return fat; }
    }
}

