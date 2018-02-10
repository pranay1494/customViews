package com.example.pranay.circularimage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by pranay on 11/02/18.
 */

public class CustomEdittext extends android.support.v7.widget.AppCompatEditText{
    public CustomEdittext(Context context) {
        super(context);
        init();
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackGroundImage(R.drawable.edittext_background);
//        setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.ic_launcher_round),null,null,null);
        setDrawables(getContext().getResources().getDrawable(R.mipmap.ic_launcher_round),null,null,null);
    }

    public void setBackGroundImage(int resID){
        this.setBackgroundDrawable(getContext().getResources().getDrawable(resID));
    }

    public void setDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom){
        setCompoundDrawablesWithIntrinsicBounds(left,top,right,bottom);
    }

}
