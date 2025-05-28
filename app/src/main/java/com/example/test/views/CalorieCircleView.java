package com.example.test.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CalorieCircleView extends View {
    private Paint circlePaint;
    private Paint progressCirclePaint;
    private Paint textPaint;
    private Paint subTextPaint;
    private RectF rect;

    private int caloriesConsumed = 0;
    private int caloriesTotal = 2560; // Default daily target

    public CalorieCircleView(Context context) {
        super(context);
        init();
    }

    public CalorieCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalorieCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(15f);
        circlePaint.setColor(Color.LTGRAY);

        // Progress circle (colored)
        progressCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressCirclePaint.setStyle(Paint.Style.STROKE);
        progressCirclePaint.setStrokeWidth(15f);
        progressCirclePaint.setColor(Color.parseColor("#FF5722")); // Orange/red color
        progressCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setStyle(Paint.Style.FILL);
        subTextPaint.setColor(Color.GRAY);
        subTextPaint.setTextSize(20f);
        subTextPaint.setTextAlign(Paint.Align.CENTER);

        rect = new RectF();
    }

    public void setCalorieData(int consumed, int total) {
        this.caloriesConsumed = consumed;
        this.caloriesTotal = total;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2;
        float centerY = height / 2;

        // Draw outer circle
        float radius = (Math.min(width, height) / 2) - circlePaint.getStrokeWidth() / 2;
        rect.set(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
//        canvas.drawArc(rect, 0f, 360f, false, circlePaint);

        // Draw progress arc (based on calories consumed)
        if (caloriesTotal > 0) {
            float sweepAngle = (360f * caloriesConsumed) / caloriesTotal;
            canvas.drawArc(rect, -90f, sweepAngle, false, progressCirclePaint);
        }

        // Draw the calorie text
        String caloriesText = String.valueOf(caloriesTotal - caloriesConsumed);
        canvas.drawText(caloriesText, centerX, centerY, textPaint);

        // Draw the "kcal left" text below
        canvas.drawText("kcal left", centerX, centerY + 30, subTextPaint);
    }
}
