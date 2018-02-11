package com.example.pranay.circularimage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by pranay on 11/02/18.
 */

public class CustomButtonWithimageView extends LinearLayout {
    private View mView;
    private ImageView ivYesBtn;
    private Button btnYes;
    private ImageView ivNoBtn;
    private Button btnNo;
    private Drawable left;
    private Drawable right;
    private String rightText;
    private String leftText;
    private OnClickListener clickListener;
    public static int ENABLE_LEFT_DEFAULT = 1;
    public static int ENABLE_RIGHT_DEFAULT = 0;
    private int defaultSelected;

    public CustomButtonWithimageView(Context context) {
        super(context);
        init();
    }

    public CustomButtonWithimageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonWithimageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomButtonWithimageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mView = inflater.inflate(R.layout.custom_button,this);
        ivYesBtn = mView.findViewById(R.id.ivYesBtn);
        btnYes = mView.findViewById(R.id.btnYes);
        ivNoBtn = mView.findViewById(R.id.ivNoBtn);
        btnNo = mView.findViewById(R.id.btnNo);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setView();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNoBtn.setVisibility(INVISIBLE);
                ivYesBtn.setVisibility(VISIBLE);
                btnNo.setSelected(false);
                btnYes.setSelected(true);
                if (clickListener!=null)
                    clickListener.onPositiveBtnClicked();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivYesBtn.setVisibility(INVISIBLE);
                ivNoBtn.setVisibility(VISIBLE);
                btnNo.setSelected(true);
                btnYes.setSelected(false);
                if (clickListener!=null)
                    clickListener.onNegativeBtnClicked();
            }
        });
    }

    private void setView() {
        if (defaultSelected == ENABLE_LEFT_DEFAULT){
            ivNoBtn.setVisibility(INVISIBLE);
            ivYesBtn.setVisibility(VISIBLE);
            btnNo.setSelected(false);
            btnYes.setSelected(true);
        }else{
            ivYesBtn.setVisibility(INVISIBLE);
            ivNoBtn.setVisibility(VISIBLE);
            btnNo.setSelected(true);
            btnYes.setSelected(false);
        }
        if (left != null){
            ivYesBtn.setImageDrawable(left);
        }if (right != null){
            ivNoBtn.setImageDrawable(right);
        }if (!TextUtils.isEmpty(leftText)){
            btnYes.setText(leftText);
        }if (!TextUtils.isEmpty(rightText)){
            btnNo.setText(rightText);
        }
    }

    public void setImages(@Nullable Drawable left,@Nullable Drawable right){
        this.left = left;
        this.right = right;
        setView();
    }

    public void setBtnTexts(String left, String right){
        this.leftText = left;
        this.rightText = right;
        setView();
    }

    public void setOnclickListener(OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setDefaultSelected(int val){
        defaultSelected = val;
    }


    interface OnClickListener{
        void onPositiveBtnClicked();
        void onNegativeBtnClicked();
    }
}
