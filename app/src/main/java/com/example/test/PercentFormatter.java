package com.example.test;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class PercentFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return Math.round(value) + "%";
    }
}