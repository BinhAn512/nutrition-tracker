package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.AgeViewHolder> {

    private final List<Integer> ageList;
    private int selectedPosition = 2; // Default to position 2 (which corresponds to age 19 in the UI)
    private OnAgeSelectedListener listener;

    public interface OnAgeSelectedListener {
        void onAgeSelected(int age);
    }

    public AgeAdapter(int minAge, int maxAge, OnAgeSelectedListener listener) {
        this.listener = listener;
        ageList = new ArrayList<>();
        for (int i = minAge; i <= maxAge; i++) {
            ageList.add(i);
        }
    }

    @NonNull
    @Override
    public AgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_age, parent, false);
        return new AgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgeViewHolder holder, int position) {
        int age = ageList.get(position);
        holder.tvAge.setText(String.valueOf(age));

        // Set the text appearance based on whether this item is selected
        if (position == selectedPosition) {
            holder.tvAge.setTextColor(0xFFFFFFFF); // White color for selected item
            holder.tvAge.setTextSize(32); // Larger text for selected item
        } else {
            int distance = Math.abs(position - selectedPosition);
            float alpha = 1.0f - (distance * 0.2f);
            if (alpha < 0.3f) alpha = 0.3f;

            holder.tvAge.setTextColor(0x99000000 | (Math.round(alpha * 255) << 24)); // Transparent black with alpha
            holder.tvAge.setTextSize(24 - (distance * 2)); // Smaller text for items further from selection
        }
    }

    @Override
    public int getItemCount() {
        return ageList.size();
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(selectedPosition);

        // Notify the items around the old and new positions to update their appearance
        for (int i = -2; i <= 2; i++) {
            int pos = oldPosition + i;
            if (pos >= 0 && pos < getItemCount() && pos != oldPosition && pos != selectedPosition) {
                notifyItemChanged(pos);
            }

            pos = selectedPosition + i;
            if (pos >= 0 && pos < getItemCount() && pos != oldPosition && pos != selectedPosition) {
                notifyItemChanged(pos);
            }
        }

        // Notify the listener
        if (listener != null) {
            listener.onAgeSelected(ageList.get(selectedPosition));
        }
    }

    public int getSelectedAge() {
        return ageList.get(selectedPosition);
    }

    public static class AgeViewHolder extends RecyclerView.ViewHolder {
        TextView tvAge;

        public AgeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAge = itemView.findViewById(R.id.tv_age);
        }
    }
}