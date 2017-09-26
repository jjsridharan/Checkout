package com.example.hi.checkout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by HI on 7/29/2017.
 */
public class StaffLogin extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String unm=sp1.getString("Sname", null);
        String password = sp1.getString("Spass", null);
        if(unm!=null && password!=null)
        {
            Intent myIntent = new Intent(StaffLogin.this,
                    StaffOptions.class);
            startActivity(myIntent);
        }
    }

    public void Login(View v)
    {
        TextView et = (TextView)findViewById(R.id.editText6);
        TextView et1 = (TextView) findViewById(R.id.editText7);
        TextView et2 = (TextView) findViewById(R.id.editText12);
        String user = et.getText().toString();
        String pass = et1.getText().toString();
        String cid=et2.getText().toString();
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner5);
        if(spinner1.getVisibility()==View.GONE)
        {
            Loginuser(user, pass,"", cid);
        }
        else
        {
            Loginuser(user,pass,(String.valueOf(spinner1.getSelectedItem())),cid);
        }

    }
    public void InsertSpinner(String result)
    {
        String[] deptarr = result.split("###");
        Spinner spinner = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, deptarr);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setVisibility(View.VISIBLE);
        TextView et = (TextView)findViewById(R.id.editText6);
        TextView et1 = (TextView) findViewById(R.id.editText7);
        TextView et2 = (TextView) findViewById(R.id.editText12);
        et.setFocusable(false);
        et1.setFocusable(false);
        et2.setFocusable(false);
        Button b=(Button)findViewById(R.id.button3);
        b.setText("Login");
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void Loginuser(final String name, final String pass,final String dept,final String cid)
    {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            ProgressDialog pd = new ProgressDialog(StaffLogin.this);
            protected void onPreExecute()
            {
                pd.setMessage("Attempting Login...");
                pd.show();
            }

            protected String doInBackground(String... arg0)
            {
                try
                {
                    String urlscr;
                    if(dept.length()==0)
                    {
                        urlscr="http://checkoutstaff.000webhostapp.com/log3.php";
                    }
                    else
                        urlscr="http://checkoutstaff.000webhostapp.com/log2.php";
                    URL url = new URL(urlscr); // here is your URL path
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("us", name);
                    postDataParams.put("pa", pass);
                    postDataParams.put("de",dept);
                    postDataParams.put("ci",cid);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode=conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK)
                    {
                        BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";
                        while((line = in.readLine()) != null)
                        {
                            sb.append(line);
                        }
                        in.close();
                        return sb.toString();
                    }
                    else
                    {
                        return new String("false : "+responseCode);
                    }
                }
                catch(Exception e)
                {
                    return new String("Exception: " + e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String result)
            {
                pd.dismiss();
                if(result.contains("Retry"))
                {
                    Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_LONG).show();
                }
                else if(dept.length()==0)
                {
                    InsertSpinner(result);
                }
                else
                {
                    SharedPreferences sp=getSharedPreferences("Login", 0);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("Sname",name);
                    Ed.putString("Spass",pass);
                    Ed.putString("dept",dept);
                    Ed.putString("scid",cid);
                    Ed.commit();
                    Intent myIntent = new Intent(StaffLogin.this,
                            StaffOptions.class);
                    startActivity(myIntent);
                }
            }
            public String getPostDataString(JSONObject params) throws Exception
            {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                Iterator<String> itr = params.keys();
                while(itr.hasNext())
                {
                    String key= itr.next();
                    Object value = params.get(key);
                    if (first)
                        first = false;
                    else
                        result.append("&");
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
                }
                return result.toString();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name,pass,dept,cid);
    }
}
