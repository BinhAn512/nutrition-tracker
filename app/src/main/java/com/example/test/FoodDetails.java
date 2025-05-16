package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class FoodDetails extends AppCompatActivity {
   @Override
    public void onCreate (Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_food_details);

       ImageButton btn_back = findViewById(R.id.btnBack);
       btn_back.setOnClickListener(v -> {
           finish(); // Quay lại màn trước
       });

       AppCompatButton btn_add = findViewById(R.id.btnAdd);
       btn_add.setOnClickListener(v -> {
           finish();
       });
   }
}
