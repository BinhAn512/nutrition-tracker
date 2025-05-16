package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class NutrientCircleView extends View {
    private Paint circlePaint;
    private Paint textPaint;
    private Paint subTextPaint;
    private RectF rect;

    private int nutrientAmount = 0;
    private int nutrientTotal = 224; // Default value

    public NutrientCircleView(Context context) {
        super(context);
        init();
    }

    public NutrientCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NutrientCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6f);
        circlePaint.setColor(Color.LTGRAY);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setStyle(Paint.Style.FILL);
        subTextPaint.setColor(Color.GRAY);
        subTextPaint.setTextSize(14f);
        subTextPaint.setTextAlign(Paint.Align.CENTER);

        rect = new RectF();
    }

    public void setNutrientData(int amount, int total) {
        this.nutrientAmount = amount;
        this.nutrientTotal = total;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2;
        float centerY = height / 2;

        // Draw circle
        float radius = (Math.min(width, height) / 2) - circlePaint.getStrokeWidth();
        rect.set(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );
        canvas.drawArc(rect, 0f, 360f, false, circlePaint);

        // Draw the nutrient text
        String nutrientText = String.valueOf(nutrientAmount);
        canvas.drawText(nutrientText, centerX, centerY, textPaint);

        // Draw the reference text below
        String totalText = "/ " + nutrientTotal + "g";
        canvas.drawText(totalText, centerX, centerY + 20, subTextPaint);
    }
}