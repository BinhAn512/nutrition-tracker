package com.example.test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Meal;
import com.example.test.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;
    private OnMealClickListener mealClickListener;

    public interface OnMealClickListener {
        void onAddFoodClick(Meal meal);
    }

    public MealAdapter(List<Meal> meals, OnMealClickListener listener) {
        this.meals = meals;
        this.mealClickListener = listener;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealIconImageView;
        TextView mealNameTextView;
//        TextView mealCaloriesTextView;
        ImageButton addFoodButton;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealIconImageView = itemView.findViewById(R.id.mealIconImageView);
            mealNameTextView = itemView.findViewById(R.id.mealNameTextView);
//            mealCaloriesTextView = itemView.findViewById(R.id.mealCaloriesTextView);
            addFoodButton = itemView.findViewById(R.id.addFoodButton);
        }
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.mealIconImageView.setImageResource(meal.getIcon());
        holder.mealNameTextView.setText(meal.getName());

        String caloriesText = meal.getTotalCalories() + " / " + meal.getMaxCalories() + " kcal";
//        holder.mealCaloriesTextView.setText(caloriesText);

        holder.addFoodButton.setOnClickListener(v -> {
            if (mealClickListener != null) {
                mealClickListener.onAddFoodClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateData(List<Meal> newMeals) {
        this.meals = newMeals;
        notifyDataSetChanged();
    }
}