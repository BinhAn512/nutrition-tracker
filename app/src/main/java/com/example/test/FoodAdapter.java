package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodItems;
    private OnFoodItemClickListener listener;
    private int selectedPosition = -1;

    public interface OnFoodItemClickListener {
        void onFoodItemClick(FoodItem item, int position);
    }

    public FoodAdapter(List<FoodItem> foodItems, OnFoodItemClickListener listener) {
        this.foodItems = foodItems;
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        int previousSelected = selectedPosition;
        selectedPosition = position;

        if (previousSelected != -1) {
            notifyItemChanged(previousSelected);
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition);
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public FoodItem getSelectedItem() {
        if (selectedPosition != -1 && selectedPosition < foodItems.size()) {
            return foodItems.get(selectedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);
        holder.foodName.setText(item.getName());
        holder.foodDetails.setText(item.getCalories() + " kcal . " + item.getWeight() + " gram");

        // Set selected state
        if (position == selectedPosition) {
            holder.btnAdd.setVisibility(View.GONE);
            holder.btnSelected.setVisibility(View.VISIBLE);
        } else {
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.btnSelected.setVisibility(View.GONE);
        }

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onFoodItemClick(item, adapterPosition);
                }
            }
        });

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodDetails.class);
                intent.putExtra("food_name", item.getName());
                intent.putExtra("food_details", item.getCalories() + " kcal . " + item.getWeight() + " gram");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView foodDetails;
        ImageView btnAdd;
        ImageView btnSelected;
        ImageView btnDetails;
        FrameLayout btnContainer;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodDetails = itemView.findViewById(R.id.food_details);
            btnAdd = itemView.findViewById(R.id.btn_add);
            btnSelected = itemView.findViewById(R.id.btn_selected);
            btnDetails = itemView.findViewById(R.id.btn_details);
            btnContainer = itemView.findViewById(R.id.btn_container);
        }
    }
}
