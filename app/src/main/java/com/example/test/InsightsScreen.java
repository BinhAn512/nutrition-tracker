package com.example.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import java.util.Locale;
import java.util.Map;

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

        ImageButton btn_account = findViewById(R.id.btn_ivLogo);
        btn_account.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileScreen.class);
            startActivity(intent);
            finish();
        });
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
                float carbsPercent = 40f + (float)(Math.random() * 15);
                float proteinPercent = 15f + (float)(Math.random() * 15);
                float fatPercent = 100f - carbsPercent - proteinPercent;

                weeklyData.put(dateKey, new DayData(
                        baseCalorie,
                        carbsPercent,
                        proteinPercent,
                        fatPercent
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

    private ArrayList<BarEntry> getCurrentWeekCalorieData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar cal = (Calendar) currentWeekStart.clone();

        for (int i = 0; i < 7; i++) {
            String dateKey = getDateKey(cal);
            DayData data = weeklyData.get(dateKey);

            float calories = data != null ? data.getCalories() : 1800f;
            entries.add(new BarEntry(i, calories));

            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return entries;
    }

    private ArrayList<BarEntry> getCurrentWeekNutritionData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar cal = (Calendar) currentWeekStart.clone();

        for (int i = 0; i < 7; i++) {
            String dateKey = getDateKey(cal);
            DayData data = weeklyData.get(dateKey);

            if (data != null) {
                entries.add(new BarEntry(i, new float[]{
                        data.getCarbsPercent(),
                        data.getProteinPercent(),
                        data.getFatPercent()
                }));
            } else {
                entries.add(new BarEntry(i, new float[]{45f, 20f, 35f}));
            }

            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

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
    }

    private void setupCalorieChart() {
        calorieBarChart.getDescription().setEnabled(false);
        calorieBarChart.setDrawGridBackground(false);
        calorieBarChart.setDrawBarShadow(false);
        calorieBarChart.setDrawValueAboveBar(true);
        calorieBarChart.setPinchZoom(false);
        calorieBarChart.setScaleEnabled(false);
        calorieBarChart.setDoubleTapToZoomEnabled(false);

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

        // Add current week data
        ArrayList<BarEntry> entries = getCurrentWeekCalorieData();

        BarDataSet dataSet = new BarDataSet(entries, "Calories");
        dataSet.setColors(ContextCompat.getColor(this, R.color.LightGreen));


        // Add goal line
        LimitLine goalLine = new LimitLine(2000f, "");
        goalLine.setLineWidth(1f);
        goalLine.setLineColor(ContextCompat.getColor(this, R.color.Green));
        goalLine.enableDashedLine(10f, 10f, 0f);
        leftAxis.addLimitLine(goalLine);

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
        ArrayList<BarEntry> entries = getCurrentWeekNutritionData();

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
        private final float carbsPercent;
        private final float proteinPercent;
        private final float fatPercent;

        public DayData(float calories, float carbsPercent, float proteinPercent, float fatPercent) {
            this.calories = calories;
            this.carbsPercent = carbsPercent;
            this.proteinPercent = proteinPercent;
            this.fatPercent = fatPercent;
        }

        public float getCalories() { return calories; }
        public float getCarbsPercent() { return carbsPercent; }
        public float getProteinPercent() { return proteinPercent; }
        public float getFatPercent() { return fatPercent; }
    }
}

