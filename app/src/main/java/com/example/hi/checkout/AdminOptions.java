package com.example.hi.checkout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * Created by HI on 7/29/2017.
 */
public class AdminOptions extends Activity
{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.admin_options);
    }
    @Override
    public void onBackPressed()
    {
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String unm=sp1.getString("Unm", null);
        String password = sp1.getString("Psw", null);

        if(unm!=null && password!=null)
        {
            Intent myIntent = new Intent(AdminOptions.this,
                    MainActivity.class);
            startActivity(myIntent);
            finish();
        }
        else
        {
            Intent myIntent = new Intent(AdminOptions.this,
                   AdminLogin.class);
            startActivity(myIntent);
            finish();
        }
    }
    public void RegisterStaff(View v)
    {
        Intent myIntent = new Intent(AdminOptions.this,
                RegisterStaff.class);
        startActivity(myIntent);
    }
    public void ChangeStatus(View v)
    {
        Intent myIntent = new Intent(AdminOptions.this,
                ChangeStatus.class);
        startActivity(myIntent);
    }
    public void LogOut(View v)
    {
        SharedPreferences sp=getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("Unm",null );
        Ed.putString("Psw",null);
        Ed.putString("dept",null);
        Ed.commit();
        Intent myIntent = new Intent(AdminOptions.this,
                MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finishAffinity();
        startActivity(myIntent);
    }
    public void Announce(View v)
    {
        Intent myIntent = new Intent(AdminOptions.this,
                Announcement.class);
        startActivity(myIntent);
    }
}
