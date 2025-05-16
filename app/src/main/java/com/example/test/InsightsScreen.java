package com.example.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;

public class InsightsScreen extends AppCompatActivity {

    private BarChart calorieBarChart;
    private BarChart nutritionStackedBarChart;
    private TextView tvWeekly, tvMonthly, tvYearly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

        // Initialize views
        calorieBarChart = findViewById(R.id.calorieBarChart);
        nutritionStackedBarChart = findViewById(R.id.nutritionStackedBarChart);
        tvWeekly = findViewById(R.id.tvWeekly);
        tvMonthly = findViewById(R.id.tvMonthly);
        tvYearly = findViewById(R.id.tvYearly);

        // Set up tab selection
        tvWeekly.setOnClickListener(v -> selectTab(tvWeekly));
        tvMonthly.setOnClickListener(v -> selectTab(tvMonthly));
        tvYearly.setOnClickListener(v -> selectTab(tvYearly));

        // Initialize charts
        setupCalorieChart();
        setupNutritionChart();
        setupNavigationView();
    }

    private void selectTab(TextView selectedTab) {
        // Reset all tabs
        tvWeekly.setBackgroundResource(R.drawable.rounded_button_unselected);
        tvWeekly.setTextColor(Color.BLACK);
        tvMonthly.setBackgroundResource(R.drawable.rounded_button_unselected);
        tvMonthly.setTextColor(Color.BLACK);
        tvYearly.setBackgroundResource(R.drawable.rounded_button_unselected);
        tvYearly.setTextColor(Color.BLACK);

        // Set selected tab
        selectedTab.setBackgroundResource(R.drawable.rounded_button_selected);
        selectedTab.setTextColor(Color.WHITE);

        // Update charts based on selected tab
        if (selectedTab == tvWeekly) {
            setupCalorieChart();
            setupNutritionChart();
        } else if (selectedTab == tvMonthly) {
            // Setup monthly data
            // This would be implemented based on your data source
        } else {
            // Setup yearly data
            // This would be implemented based on your data source
        }
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

        String[] days = new String[]{"16", "17", "18", "19", "20", "21", "22"};
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

        // Add data
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 1800));  // Dec 16
        entries.add(new BarEntry(1, 2100));  // Dec 17 - highlighted
        entries.add(new BarEntry(2, 1700));  // Dec 18
        entries.add(new BarEntry(3, 1900));  // Dec 19
        entries.add(new BarEntry(4, 2200));  // Dec 20
        entries.add(new BarEntry(5, 1600));  // Dec 21
        entries.add(new BarEntry(6, 1400));  // Dec 22

        BarDataSet dataSet = new BarDataSet(entries, "Calories");
        dataSet.setColors(ContextCompat.getColor(this, R.color.LightGreen));

        // Highlight the selected day (Dec 17)
        dataSet.setHighLightColor(ContextCompat.getColor(this, R.color.DarkGreen));

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
        calorieBarChart.highlightValue(1, 0);
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

        String[] days = new String[]{"16", "17", "18", "19", "20", "21", "22"};
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
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Each entry has 3 values: [carbs, protein, fat]
        entries.add(new BarEntry(0, new float[]{45f, 20f, 35f}));  // Dec 16
        entries.add(new BarEntry(1, new float[]{42f, 23f, 35f}));  // Dec 17
        entries.add(new BarEntry(2, new float[]{48f, 18f, 34f}));  // Dec 18
        entries.add(new BarEntry(3, new float[]{46f, 19f, 35f}));  // Dec 19
        entries.add(new BarEntry(4, new float[]{40f, 25f, 35f}));  // Dec 20
        entries.add(new BarEntry(5, new float[]{50f, 15f, 35f}));  // Dec 21
        entries.add(new BarEntry(6, new float[]{46f, 20f, 34f}));  // Dec 22

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
}

