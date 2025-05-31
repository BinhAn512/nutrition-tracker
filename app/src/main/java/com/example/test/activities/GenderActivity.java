package com.example.test.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.test.HomeScreen;
import com.example.test.R;
import com.example.test.api.ApiService;
import com.example.test.models.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenderActivity extends AppCompatActivity {

    private RadioButton rbFemale;
    private ImageButton btnBack;
    private RadioButton rbMale;
    private RadioButton rbOther;
    private String selectedGender = "male"; // Default selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        // Find views
        rbFemale = findViewById(R.id.rb_female);
        rbMale = findViewById(R.id.rb_male);
        rbOther = findViewById(R.id.rb_other);
        ImageButton btnBack = findViewById(R.id.btn_back);

        Button btnContinue = findViewById(R.id.btn_continue);

        // Set up default selection
        rbMale.setChecked(true);
        updateRadioUI(rbMale);

        // Set up click listeners for radio buttons
        setupGoalSelectionListeners();

        Bundle bundle = getIntent().getExtras();
        Log.d("Test bundle goal", bundle.getString("GOAL", ""));
        Log.d("Test bundle weight", String.valueOf(bundle.getInt("WEIGHT", 0)));

        // Set up continue button
        btnContinue.setOnClickListener(v -> {
            // Create intent to go to gender selection screen
            Intent intent = new Intent(GenderActivity.this, HomeScreen.class);

            // Pass selected goal to next activity
            bundle.putString("GENDER", selectedGender);
            intent.putExtras(bundle);

            // Insert data into database
            UserProfile userProfile = new UserProfile(
                    bundle.getInt("USER_ID"),
                    bundle.getInt("AGE"),
                    bundle.getString("GENDER"),
                    bundle.getInt("WEIGHT"),
                    bundle.getString("GOAL"),
                    2560
            );
            ApiService.apiService.createUserProfile(userProfile).enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {

                }
            });

            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(GenderActivity.this, GoalActivity.class);
            finish();
        });
    }

    private void setupGoalSelectionListeners() {
        rbFemale.setOnClickListener(v -> {
            selectedGender = "female";
            rbFemale.setChecked(true);
            updateRadioUI(rbFemale);
        });

        rbMale.setOnClickListener(v -> {
            selectedGender = "male";
            rbMale.setChecked(true);
            updateRadioUI(rbMale);
        });

        rbOther.setOnClickListener(v -> {
            selectedGender = "other";
            rbOther.setChecked(true);
            updateRadioUI(rbOther);
        });
    }

    private void updateRadioUI(RadioButton selected) {
        // Reset all to unselected background
        rbFemale.setBackgroundResource(R.drawable.goal_selector_background);
        rbMale.setBackgroundResource(R.drawable.goal_selector_background);
        rbOther.setBackgroundResource(R.drawable.goal_selector_background);

        // Set selected button to selected background
        selected.setBackgroundResource(R.drawable.goal_selector_background_selected);

        // Uncheck others (optional but safe)
        rbFemale.setChecked(rbFemale == selected);
        rbMale.setChecked(rbMale == selected);
        rbOther.setChecked(rbOther == selected);
    }
}