package com.example.hi.checkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
            Intent myIntent = new Intent(MainActivity.this,
                    AboutCheckout.class);
            startActivity(myIntent);
            return true;
        }
        else
        {
            return true;
        }
    }

    public void AdminLogin(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,
                AdminLogin.class);
        startActivity(myIntent);
    }

    public void StaffLogin(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,
                StaffLogin.class);
        startActivity(myIntent);
    }

    public void Student(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,
                StudentOptions.class);
        startActivity(myIntent);
    }
}
