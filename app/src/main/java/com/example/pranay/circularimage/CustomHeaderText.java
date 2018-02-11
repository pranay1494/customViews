package com.example.pranay.circularimage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by pranay on 11/02/18.
 */

public class CustomHeaderText extends LinearLayout {
    private View mView;
    private TextView tvHeader;
    private ImageView ivInfo;
    private SpannableString strHeader;
    private Drawable imgInfo;

    public CustomHeaderText(Context context) {
        super(context);
        init();
    }

    public CustomHeaderText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomHeaderText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomHeaderText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mView = inflater.inflate(R.layout.custom_header_text,this);
        tvHeader = mView.findViewById(R.id.tvHeader);
        ivInfo = mView.findViewById(R.id.ivInfo);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setView();
    }

    private void setView() {
        tvHeader.setText(strHeader);
        ivInfo.setImageDrawable(imgInfo);
    }

    public void setText(final SpannableString string){
        tvHeader.post(new Runnable() {
            @Override
            public void run() {
                strHeader = string;
                setView();
            }
        });
    }

    public void setImgInfo(Drawable img){
        imgInfo = imgInfo;
        ivInfo.post(new Runnable() {
            @Override
            public void run() {
                setView();
            }
        });
    }
}
