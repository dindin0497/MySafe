package com.my.mysafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import static android.view.View.MeasureSpec.EXACTLY;


public class SquareFrameLayout extends FrameLayout {

    private boolean useWidth=true;
    public SquareFrameLayout(Context context) {
        super(context);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w,h;
        if(useWidth) {
            w = View.MeasureSpec.getSize(widthMeasureSpec);
            h = View.MeasureSpec.makeMeasureSpec(w, EXACTLY);
            w=widthMeasureSpec;
        } else {
            h = View.MeasureSpec.getSize(heightMeasureSpec);
            w = View.MeasureSpec.makeMeasureSpec(h, EXACTLY);
            h=heightMeasureSpec;
        }
        super.onMeasure(w, h);
    }
}
