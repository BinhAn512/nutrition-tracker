package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AgeSelectActivity extends AppCompatActivity implements AgeAdapter.OnAgeSelectedListener {

    private RecyclerView ageRecyclerView;
    private AgeAdapter ageAdapter;
    private int selectedAge = 19; // Default selected age

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_select);

        // Find views
        ageRecyclerView = findViewById(R.id.age_recycler_view);
        Button btnContinue = findViewById(R.id.btn_continue);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // Setup age picker
        setupAgePicker();

        // Set click listeners
        btnContinue.setOnClickListener(v -> {
            // Get the selected age and pass it to the next activity
            int age = ageAdapter.getSelectedAge();
            Intent intent = new Intent(AgeSelectActivity.this, WeightActivity.class);
            intent.putExtra("AGE", age);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AgeSelectActivity.this, MainActivity.class);
            finish();
        });
    }

    private void setupAgePicker() {
        // Create and set the adapter (ages 12-80)
        ageAdapter = new AgeAdapter(12, 80, this);
        ageRecyclerView.setAdapter(ageAdapter);

        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        ageRecyclerView.setLayoutManager(layoutManager);

        // Add snap helper to snap to the closest age
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(ageRecyclerView);

        // Scroll to initial position (19)
        ageRecyclerView.scrollToPosition(7); // Position for age 19 (12 + 7 = 19)

        // Add scroll listener to update selection
        ageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // When scrolling stops, find the center view
                    View centerView = snapHelper.findSnapView(layoutManager);
                    if (centerView != null) {
                        int position = recyclerView.getChildAdapterPosition(centerView);
                        ageAdapter.setSelectedPosition(position);
                    }
                }
            }
        });
    }

    @Override
    public void onAgeSelected(int age) {
        selectedAge = age;
    }
}