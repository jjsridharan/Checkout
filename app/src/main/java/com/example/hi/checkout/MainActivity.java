package com.example.hi.checkout;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onBackPressed()
    {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Intent nextActivity = new Intent(MainActivity.this,AboutCheckout.class);
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
                            String packageName = getApplicationContext().getPackageName();
                            Runtime runtime = Runtime.getRuntime();
                            runtime.exec("pm clear " + packageName);
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
            builder.setMessage("This will clear all data related to this app. Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }
        return true;
    }

    public void AdminLogin(View v)
    {
        Intent nextActivity = new Intent(MainActivity.this,AdminLogin.class);
        startActivity(nextActivity);
    }

    public void StaffLogin(View v)
    {
        Intent nextActivity = new Intent(MainActivity.this,StaffLogin.class);
        startActivity(nextActivity);
    }

    public void Student(View v)
    {
        Intent nextActivity = new Intent(MainActivity.this,StudentOptions.class);
        startActivity(nextActivity);
      /* try {
           Uri mUri = Uri.parse("smsto:8122864966");
           Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
           mIntent.setPackage("com.whatsapp");
           mIntent.putExtra("sms_body", "The text goes here");
           mIntent.putExtra("chat", true);
           startActivity(Intent.createChooser(mIntent, ""));
       }
       catch (Exception e)
       {
           Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
       }*/
    }
}
