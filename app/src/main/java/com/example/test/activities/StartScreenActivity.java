package com.example.test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.test.R;
import com.example.test.api.ApiService;
import com.example.test.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartScreenActivity extends AppCompatActivity {
    int userId;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // Find views
        Button btnContinue = findViewById(R.id.btn_continue);

        bundle = getIntent().getExtras();
        getUserId();

        // Set click listener for continue button
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to next screen
                Intent intent = new Intent(StartScreenActivity.this, AgeSelectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                finish(); // Optional: close this activity so user can't go back
            }
        });
    }

    private void getUserId() {
        ApiService.apiService.getUserInfo(bundle.getString("USERNAME"),
                bundle.getString("PASSWORD")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (response.isSuccessful() && user != null) {
                    bundle.putInt("USER_ID", user.getId());
                    Log.d("User Id put successfully", String.valueOf(user.getId()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}