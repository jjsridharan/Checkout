package com.example.hi.checkout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by SRIDHARAN JOTHIRAMAN on 10/27/2017.
 */

public class TimeTable extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        Touchywebview t=(Touchywebview)findViewById(R.id.description_web);
        t.getSettings().setBuiltInZoomControls(true);
        t.loadUrl("https://checkoutstaff.000webhostapp.com/Time.html");
    }
}
