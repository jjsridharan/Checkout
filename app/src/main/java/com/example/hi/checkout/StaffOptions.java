package com.example.hi.checkout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * Created by HI on 7/29/2017.
 */
public class StaffOptions extends Activity
{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.staff_options);
    }
    public void onBackPressed()
    {
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String unm=sp1.getString("sunm", null);
        String password = sp1.getString("spw", null);

        if(unm!=null && password!=null)
        {
            Intent myIntent = new Intent(StaffOptions.this,
                    MainActivity.class);
            startActivity(myIntent);
        }
    }
    public void Changestatus(View v)
    {
        Intent myIntent = new Intent(StaffOptions.this,
                StaffStatus.class);
        startActivity(myIntent);
    }
    public void ChangePass(View v)
    {
        Intent myIntent = new Intent(StaffOptions.this,
                ChangePass.class);
        startActivity(myIntent);
    }
    public void Announce(View v)
    {
        Intent myIntent = new Intent(StaffOptions.this,
                Announcement.class);
        startActivity(myIntent);
    }
}
