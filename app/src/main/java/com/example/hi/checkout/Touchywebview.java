package com.example.hi.checkout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by SRIDHARAN JOTHIRAMAN on 10/27/2017.
 */

public class Touchywebview extends WebView {

    public Touchywebview(Context context) {
        super(context);
    }

    public Touchywebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Touchywebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }
}