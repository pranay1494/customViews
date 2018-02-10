package com.example.pranay.circularimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pranay on 05/02/18.
 */

public class CustomProgressBar extends View {

    List<Double> percentText;
    List<String> headerTexts;
    List<Integer> colors;
    private Paint paint;
    private Rect rect;
    private int TOTAL_SUM= 0;
    private TextPaint numberPaint;
    private TextPaint headerPaint;
    private Rect textBounds;

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint = new Paint();
        rect = new Rect();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        numberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        numberPaint.setTextSize(Math.round(12f * getResources().getDisplayMetrics().scaledDensity));
        headerPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        headerPaint.setTextSize(Math.round(12f * getResources().getDisplayMetrics().scaledDensity));
        textBounds = new Rect();
    }

    public void setUp(List<Double> percentage,List<String> headerTexts,List<Integer> colors, int max){
        this.percentText = percentage;
        this.headerTexts = headerTexts;
        this.colors = colors;
        this.TOTAL_SUM = max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int givenWidth  = MeasureSpec.getSize(widthMeasureSpec);
        int givenHeight = MeasureSpec.getSize(heightMeasureSpec);

        switch(widthMode){
            case MeasureSpec.EXACTLY:
                setMeasuredDimension(givenWidth,givenHeight);
                break;
            case MeasureSpec.AT_MOST:
                setMeasuredDimension(givenWidth,getKnownHeight());
                break;
            case MeasureSpec.UNSPECIFIED:
                setMeasuredDimension(givenWidth,getKnownHeight());
                break;
        }
    }

    private int getKnownHeight() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,getContext().getResources().getDisplayMetrics());
    }

    /**
     * add a check to restrict total sum to value > 0
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) throws ArithmeticException{
        super.onDraw(canvas);

        canvas.getClipBounds(rect);
        float totalwidth = rect.width();
        float totalHeight = rect.height();

        int count = 0;
        double percentage = 0;
        String value= "";
        int color;

        int prevx = 0;
        for (int i = 0; i < percentText.size(); i++){
            percentage = percentText.get(i);
            value = headerTexts.get(i);
            color = colors.get(i);
            if (count == 0){
                paint.setColor(color);
                rect.top = (int)(rect.top + totalHeight * 0.5f);
                rect.right = (int)(totalwidth * (percentage / TOTAL_SUM));
                prevx = rect.right;
            }else{
                paint.setColor(color);
                canvas.getClipBounds(rect);
                rect.top = (int)(rect.top + totalHeight * 0.5f);
                rect.left = prevx;
                rect.right = rect.left + (int)(totalwidth * (percentage / TOTAL_SUM));
                prevx = rect.right;
            }
            canvas.drawRect(rect,paint);

            numberPaint.setTextSize(Math.round((totalHeight/11.0) * getResources().getDisplayMetrics().scaledDensity));


            float textWidth = numberPaint.measureText(value);
            float textX = (rect.left + rect.right)/2  - textWidth * 0.5f;
            float textY = (rect.top + rect.bottom)/2 + (numberPaint.descent() - numberPaint.ascent())/3;
            canvas.drawText(percentage+"%",textX,textY,numberPaint);

            headerPaint.setTextSize(Math.round((totalHeight/9.0) * getResources().getDisplayMetrics().scaledDensity));

            float headerWidth = headerPaint.measureText(value);
            float headerX = (rect.left + rect.right)/2  - headerWidth * 0.5f;
            float headerY = totalHeight/4;
            canvas.drawText(value,headerX,headerY,headerPaint);
            count++;
        }
    }
}
