package com.example.test.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.test.R;

public class GoalActivity extends AppCompatActivity {

    private RadioButton rbLoseWeight;
    private ImageButton btnBack;
    private RadioButton rbMaintainWeight;
    private RadioButton rbGainWeight;
    private String selectedGoal = "maintain weight"; // Default selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_screen);

        // Find views
        rbLoseWeight = findViewById(R.id.rb_lose_weight);
        rbMaintainWeight = findViewById(R.id.rb_maintain_weight);
        rbGainWeight = findViewById(R.id.rb_gain_weight);
        btnBack = findViewById(R.id.btn_back);
        Button btnContinue = findViewById(R.id.btn_continue);

        // Set up default selection
        rbMaintainWeight.setChecked(true);
        updateRadioUI(rbMaintainWeight);

        // Set up click listeners for radio buttons
        setupGoalSelectionListeners();

        // Set up continue button
        btnContinue.setOnClickListener(v -> {
            // Create intent to go to gender selection screen
            Intent intent = new Intent(GoalActivity.this, GenderActivity.class);

            // Pass selected goal to next activity
            intent.putExtra("GOAL", selectedGoal);

            startActivity(intent);
        });
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(GoalActivity.this, WeightActivity.class);
            finish();
        });

    }

    private void setupGoalSelectionListeners() {
        rbLoseWeight.setOnClickListener(v -> {
            selectedGoal = "lose weight";
            rbLoseWeight.setChecked(true);
            updateRadioUI(rbLoseWeight);
        });

        rbMaintainWeight.setOnClickListener(v -> {
            selectedGoal = "maintain weight";
            rbMaintainWeight.setChecked(true);
            updateRadioUI(rbMaintainWeight);
        });

        rbGainWeight.setOnClickListener(v -> {
            selectedGoal = "gain weight";
            rbGainWeight.setChecked(true);
            updateRadioUI(rbGainWeight);
        });

    }

    private void updateRadioUI(RadioButton selected) {
        // Reset all to unselected background
        rbLoseWeight.setBackgroundResource(R.drawable.goal_selector_background);
        rbMaintainWeight.setBackgroundResource(R.drawable.goal_selector_background);
        rbGainWeight.setBackgroundResource(R.drawable.goal_selector_background);

        // Set selected button to selected background
        selected.setBackgroundResource(R.drawable.goal_selector_background_selected);

        // Uncheck others (optional but safe)
        rbLoseWeight.setChecked(rbLoseWeight == selected);
        rbMaintainWeight.setChecked(rbMaintainWeight == selected);
        rbGainWeight.setChecked(rbGainWeight == selected);
    }
}