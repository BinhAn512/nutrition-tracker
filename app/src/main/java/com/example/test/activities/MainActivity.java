package com.example.test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // Find views
        Button btnContinue = findViewById(R.id.btn_continue);


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

}