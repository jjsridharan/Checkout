package com.example.hi.checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

/**
 * Created by HI on 7/29/2017.
 */
public class StudentOptions extends Activity
{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.student_options);
    }
    public void Fetch(View v)
    {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
        Bundle bundle = new Bundle();
        bundle.putString("department",String.valueOf(spinner1.getSelectedItem()));
        Intent myIntent = new Intent(StudentOptions.this,
                StudentChoice.class);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }
}
