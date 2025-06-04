package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.activities.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileScreen extends AppCompatActivity {
    TextView tvUsername;
    TextView tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tvUsername = findViewById(R.id.profile_username);
        tvEmail = findViewById(R.id.profile_email);

        tvUsername.setText(HomeScreen.username);
        tvEmail.setText(HomeScreen.email);

        // Setup toolbar/header back button
//        ImageButton backButton = findViewById(R.id.back_button);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        // Setup settings button
//        ImageView settingsButton = findViewById(R.id.settings_button);
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle settings click
//                // Add your settings logic here
//            }
//        });

        // Setup menu items click listeners
        ImageButton dailyIntakeItem = findViewById(R.id.btn_daily_intake_arrow);
        ImageButton nutritionReportItem = findViewById(R.id.btn_nutrition_report_arrow);
        ImageButton favoritesFoodItem = findViewById(R.id.btn_favorites_food_arrow);
        ImageButton logoutItem = findViewById(R.id.btn_logout_arrow);

        dailyIntakeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        nutritionReportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScreen.this, InsightsScreen.class);
                startActivity(intent);
                finish();
            }
        });

        favoritesFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScreen.this, FoodAdd.class);
                intent.putExtra("SHOW_FAVORITES", true); // Gửi cờ hiển thị tab yêu thích
                intent.putExtra("MEAL_ID", 1); // bạn có thể điều chỉnh MEAL_ID phù hợp
                startActivity(intent);
            }
        });

        logoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
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