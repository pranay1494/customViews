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
import java.util.Map;
import java.util.Set;

/**
 * Created by pranay on 05/02/18.
 */

public class temp extends View {

    Map<Double,String> data = null;
    private Paint paint;
    private Rect rect;
    private int TOTAL_SUM= 0;
    private TextPaint numberPaint;
    private TextPaint headerPaint;
    private Rect textBounds;

    public temp(Context context) {
        super(context);
        init();
    }

    public temp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public temp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public temp(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setUp(Map<Double,String> data,int max){
        this.data = data;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.getClipBounds(rect);
        float totalwidth = rect.width();
        float totalHeight = rect.height();

        int count = 0;
        double percentage = 0;
        String value= "";

        Set s = data.entrySet();
        Iterator it = s.iterator();
        int prevx = 0;
        while(it.hasNext()){
            Map.Entry m = (Map.Entry) it.next();
            percentage = (double)m.getKey();
            value = (String)m.getValue();
            if (count == 0){
                paint.setColor(Color.BLACK);
                rect.top = (int)(rect.top + totalHeight * 0.5f);
                rect.right = (int)(totalwidth * (percentage / TOTAL_SUM));
                prevx = rect.right;
            }else{
                paint.setColor(Color.BLUE);
                canvas.getClipBounds(rect);
                rect.top = (int)(rect.top + totalHeight * 0.5f);
                rect.left = prevx;
                rect.right = rect.left + (int)(totalwidth * (percentage / TOTAL_SUM));
            }
            canvas.drawRect(rect,paint);

            numberPaint.setTextSize(Math.round((totalHeight/9.0) * getResources().getDisplayMetrics().scaledDensity));


            float textWidth = numberPaint.measureText(value);
            float textX = (rect.left + rect.right)/2  - textWidth * 0.5f;
            canvas.drawText(percentage+"%",textX,rect.exactCenterY()+1,numberPaint);

            headerPaint.setTextSize(Math.round((totalHeight/9.0) * getResources().getDisplayMetrics().scaledDensity));

            float headerWidth = headerPaint.measureText(value);
            float headerX = (rect.left + rect.right)/2  - headerWidth * 0.5f;
            float headerY = totalHeight/4;
            canvas.drawText(value,headerX,headerY,headerPaint);
            count++;
        }
    }
}
