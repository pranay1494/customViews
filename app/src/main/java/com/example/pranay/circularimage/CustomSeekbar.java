package com.example.pranay.circularimage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pranay on 06/02/18.
 */

public class CustomSeekbar extends FrameLayout {
    private LayoutInflater inflater;
    private View mView = null;
    private SeekBar mSeekbar;
    private RelativeLayout relativeLayout;
    private int mFactor;
    private int maxRange;
    private int minRange;
    private ArrayList<Integer> mRangeList;
    private int mThumb;
    private float mTotalSeekbarWidth = 0;
    private int numberOfIntervals = 0;
    private float intervalWidth = 0;
    private float segmentsCount=0;
    private float ratio = 1;
    private ImageView imgHeader;
    private int headerImgRes;
    private ArrayList<String> mRangeTexts;


    public CustomSeekbar(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomSeekbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSeekbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomSeekbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init(){
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mView = inflater.inflate(R.layout.custom_seebar,this);
        mSeekbar = mView.findViewById(R.id.seek_bar);
        relativeLayout = mView.findViewById(R.id.relative_layout);
        imgHeader = mView.findViewById(R.id.ivHeader);
        imgHeader.setY(mSeekbar.getY()+getKnownDPI(5));
        //imgHeader.setX(mSeekbar.getX());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mSeekbar != null) {
                    Rect thumbBounds = mSeekbar.getThumb().getBounds();
                    if (imgHeader!=null){
                        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                            imgHeader.setX(thumbBounds.exactCenterX()+(imgHeader.getWidth()/3));
                        }else{
                            imgHeader.setX(thumbBounds.exactCenterX()-(imgHeader.getWidth()/3));
                        }
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setViews() {
        ratio = 1;
        float offsetX = 40;
        float textOffsetY = 50;
        int seekbarMax = (maxRange - minRange) / mFactor;
        mSeekbar.setPadding(40, 0, 40, 100);
        mTotalSeekbarWidth = mSeekbar.getWidth() - 80;
        mSeekbar.setMax(seekbarMax);
        //mSeekbar.incrementProgressBy(mFactor);
        numberOfIntervals = (maxRange - minRange)/mFactor;
        intervalWidth = mTotalSeekbarWidth/numberOfIntervals;

        if (mTotalSeekbarWidth > (maxRange - minRange)) {
            ratio = mTotalSeekbarWidth / (maxRange - minRange);
        }
        for (int i = 0; i < mRangeList.size(); i++) {
            if (i != 0) {
                segmentsCount = (mRangeList.get(i) - mRangeList.get(i-1));
            }
            float numberOfIntervalsInBracket = 0;
            if (mTotalSeekbarWidth > (maxRange - minRange)){
                numberOfIntervalsInBracket = ((float) (numberOfIntervals) * segmentsCount * ratio)/mTotalSeekbarWidth;
            }else{
                numberOfIntervalsInBracket = (((float) numberOfIntervals) * segmentsCount * ratio)/(maxRange - minRange);
            }
            float segmentDistance = numberOfIntervalsInBracket * intervalWidth;

            View view = new View(getContext());
            view.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(3, 15);
            view.setLayoutParams(params);
            view.setX(offsetX + segmentDistance);
            view.setY(mSeekbar.getBottom()+mSeekbar.getPaddingTop()-10);
            relativeLayout.addView(view);

            TextView tvInfo = new TextView(getContext());
            RelativeLayout.LayoutParams tvparam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvInfo.setLayoutParams(tvparam);
            tvInfo.setText(mRangeTexts.get(i)+"");
            tvInfo.setTextSize(10);
            tvInfo.setTextColor(Color.BLACK);
            if (i == mRangeList.size()-1){
                if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    tvInfo.setX(offsetX + segmentDistance - tvInfo.getTextSize()/2);
                }else{
                    tvInfo.setX(offsetX + segmentDistance - tvInfo.getTextSize()/2-10);
                }
            }else {
                tvInfo.setX(offsetX + segmentDistance - tvInfo.getTextSize()/2);
            }
            tvInfo.setY(view.getY() + textOffsetY - 20);
            relativeLayout.addView(tvInfo);

            offsetX = offsetX + segmentDistance;
        }
        invalidate();
    }

    public void setMinMax(int min , int max , int factor){
        this.minRange = min;
        this.maxRange = max;
        this.mFactor = factor;
    }

    public void setRangeList(ArrayList<Integer> ranges, ArrayList<String> rangeTexts){
        this.mRangeList = ranges;
        this.mRangeTexts = rangeTexts;
        if (mRangeList.size() != mRangeTexts.size()){
            throw new IllegalArgumentException("size of both the lists are not equal");
        }else {
            mSeekbar.post(new Runnable() {
                @Override
                public void run() {
                    setViews();
                    mSeekbar.getWidth();
                }
            });
            invalidate();
        }
    }



    public void setThumbImage(int drawableId){
        this.mThumb = drawableId;
    }

    public void setHeaderImgRes(int headerImgRes){
        this.headerImgRes = headerImgRes;
    }

    public void hideHeaderImg(boolean showImg){
        if (showImg) {
            imgHeader.setVisibility(VISIBLE);
        }else{
            imgHeader.setVisibility(GONE);
        }
    }

    private int getKnownDPI(int pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,pixels,getContext().getResources().getDisplayMetrics());
    }
}
