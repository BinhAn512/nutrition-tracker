package com.example.test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.api.ApiService;
import com.example.test.models.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // Find views
        Button btnContinue = findViewById(R.id.btn_continue);
        tvDescription = findViewById(R.id.tv_description);


        // Set click listener for continue button
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to next screen
                Intent intent = new Intent(MainActivity.this, AgeSelectActivity.class);
                startActivity(intent);
//                finish(); // Optional: close this activity so user can't go back
            }
        });
    }

    private void CallAPI() {
        ApiService.apiService.getFood(64).enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Toast.makeText(MainActivity.this, "Call Api Success", Toast.LENGTH_SHORT).show();

                Food food = response.body();
                if (food != null && response.isSuccessful()) {
                    tvDescription.setText(food.getName());
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}