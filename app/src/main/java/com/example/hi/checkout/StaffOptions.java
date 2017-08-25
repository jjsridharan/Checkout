package com.example.hi.checkout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by HI on 7/29/2017.
 */
public class StaffOptions extends AppCompatActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_options);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(myToolbar);
    }

    public void onBackPressed()
    {
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String unm=sp1.getString("Sname", null);
        String password = sp1.getString("Spass", null);
        if(unm!=null && password!=null)
        {
            Intent myIntent = new Intent(StaffOptions.this,MainActivity.class);
            startActivity(myIntent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings2)
        {
            Intent nextActivity = new Intent(StaffOptions.this,AboutCheckout.class);
            startActivity(nextActivity);
        }
        else
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    try
                    {
                        if(which==DialogInterface.BUTTON_POSITIVE)
                        {
                            SharedPreferences sp=getSharedPreferences("Login", 0);
                            SharedPreferences.Editor Ed=sp.edit();
                            Ed.putString("Sname",null );
                            Ed.putString("Spass",null);
                            Ed.putString("dept",null);
                            Ed.commit();
                            Intent nextActivity = new Intent(StaffOptions.this,MainActivity.class);
                            finishAffinity();
                            startActivity(nextActivity);
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Request Failed try after some time",
                                Toast.LENGTH_LONG).show();
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to logout?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }
        return true;
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
        Intent myIntent = new Intent(StaffOptions.this,Announcement.class);
        startActivity(myIntent);
    }
}
