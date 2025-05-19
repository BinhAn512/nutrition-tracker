package com.example.test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.test.R;

public class WeightActivity extends AppCompatActivity {

    private Button btnKg;
    private Button btnLbs;
    private SeekBar weightSlider;
    private TextView tvWeightValue;

    private boolean isKgSelected = true;
    private int weightKg = 62;
    private int weightLbs = 137; // Roughly 62kg in lbs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_select);

        // Get the selected age from the previous activity
        int age = getIntent().getIntExtra("AGE", 19);

        // Find views
        btnKg = findViewById(R.id.btn_kg);
        btnLbs = findViewById(R.id.btn_lbs);
        weightSlider = findViewById(R.id.weight_slider);
        tvWeightValue = findViewById(R.id.tv_weight_value);
        Button btnContinue = findViewById(R.id.btn_continue);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // Set up click listeners for unit toggles
        setupUnitToggles();

        // Set up weight slider
        setupWeightSlider();

        // Set up continue button
        btnContinue.setOnClickListener(v -> {
            // Create intent to go to next screen
            Intent intent = new Intent(WeightActivity.this, GoalActivity.class);

            // Pass data to next activity
            intent.putExtra("AGE", age);
            intent.putExtra("WEIGHT", weightKg); // Always pass weight in kg for consistency
            intent.putExtra("WEIGHT_UNIT", isKgSelected ? "kg" : "lbs");

            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(WeightActivity.this, AgeSelectActivity.class);
            finish();
        });
    }

    private void setupUnitToggles() {
        btnKg.setOnClickListener(v -> {
            if (!isKgSelected) {
                // Switch to kg
                isKgSelected = true;
                updateUnitToggleAppearance();

                // Convert lbs to kg for the display
                weightKg = Math.round(weightLbs / 2.20462f);
                updateWeightDisplay();

                // Update slider position based on kg value
                setupSliderRangeForKg();
            }
        });

        btnLbs.setOnClickListener(v -> {
            if (isKgSelected) {
                // Switch to lbs
                isKgSelected = false;
                updateUnitToggleAppearance();

                // Convert kg to lbs for the display
                weightLbs = Math.round(weightKg * 2.20462f);
                updateWeightDisplay();

                // Update slider position based on lbs value
                setupSliderRangeForLbs();
            }
        });
    }

    private void updateUnitToggleAppearance() {
        if (isKgSelected) {
            btnKg.setBackground(getResources().getDrawable(R.drawable.unit_toggle_selected));
            btnKg.setTextColor(getResources().getColor(android.R.color.black));

            btnLbs.setBackground(getResources().getDrawable(R.drawable.unit_toggle_unselected));
            btnLbs.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            btnKg.setBackground(getResources().getDrawable(R.drawable.unit_toggle_unselected));
            btnKg.setTextColor(getResources().getColor(android.R.color.white));

            btnLbs.setBackground(getResources().getDrawable(R.drawable.unit_toggle_selected));
            btnLbs.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    private void setupWeightSlider() {
        setupSliderRangeForKg(); // Initial setup for kg

        weightSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isKgSelected) {
                    weightKg = progress;
                } else {
                    weightLbs = progress;
                    // Convert to keep kg value updated
                    weightKg = Math.round(weightLbs / 2.20462f);
                }
                updateWeightDisplay();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });
    }

    private void setupSliderRangeForKg() {
        // Set appropriate slider range for Kg
        weightSlider.setMax(150); // 0-150 kg
        weightSlider.setProgress(weightKg);
    }

    private void setupSliderRangeForLbs() {
        // Set appropriate slider range for Lbs
        weightSlider.setMax(330); // 0-330 lbs
        weightSlider.setProgress(weightLbs);
    }

    private void updateWeightDisplay() {
        if (isKgSelected) {
            tvWeightValue.setText(weightKg + " Kg");
        } else {
            tvWeightValue.setText(weightLbs + " Lbs");
        }
    }
}