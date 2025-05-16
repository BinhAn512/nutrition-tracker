package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class GenderActivity extends AppCompatActivity {

    private RadioButton rbFemale;
    private ImageButton btnBack;
    private RadioButton rbMale;
    private RadioButton rbOther;
    private String selectedGoal = "male"; // Default selection

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

        // Set up continue button
        btnContinue.setOnClickListener(v -> {
            // Create intent to go to gender selection screen
            Intent intent = new Intent(GenderActivity.this, HomeScreen.class);

            // Pass selected goal to next activity
            intent.putExtra("GENDER", selectedGoal);

            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(GenderActivity.this, GoalActivity.class);
            finish();
        });
    }

    private void setupGoalSelectionListeners() {
        rbFemale.setOnClickListener(v -> {
            selectedGoal = "female";
            rbFemale.setChecked(true);
            updateRadioUI(rbFemale);
        });

        rbMale.setOnClickListener(v -> {
            selectedGoal = "male";
            rbMale.setChecked(true);
            updateRadioUI(rbMale);
        });

        rbOther.setOnClickListener(v -> {
            selectedGoal = "other";
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