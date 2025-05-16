package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Setup toolbar/header back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setup settings button
        ImageView settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle settings click
                // Add your settings logic here
            }
        });

        // Setup menu items click listeners
        View dailyIntakeItem = findViewById(R.id.daily_intake_item);
        View myMealsItem = findViewById(R.id.my_meals_item);
        View nutritionReportItem = findViewById(R.id.nutrition_report_item);
        View favoritesFoodItem = findViewById(R.id.favorites_food_item);
        View logoutItem = findViewById(R.id.logout_item);

        dailyIntakeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Daily Intake screen
            }
        });

        myMealsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to My Meals screen
            }
        });

        nutritionReportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Nutrition Report screen
            }
        });

        favoritesFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Favorites Food screen
            }
        });

        logoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout
                // Add your logout logic here
            }
        });


        // Setup bottom navigation
        setupNavigationView();

        // Profile nav is already active
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                Intent intent = new Intent(ProfileScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();                return true;
            } else if (itemId == R.id.navigation_insights) {
                Intent intent = new Intent(ProfileScreen.this, InsightsScreen.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.navigation_account) {
                Intent intent = new Intent(ProfileScreen.this, ProfileScreen.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });
    }

}