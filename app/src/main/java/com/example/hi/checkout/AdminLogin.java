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
public class AdminLogin extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String Aname=sp1.getString("Aname", null);
        String Apass= sp1.getString("Apass", null);
        if(Aname!=null && Apass!=null)
        {
            Intent nextActivity = new Intent(AdminLogin.this,AdminOptions.class);
            finishAffinity();
            startActivity(nextActivity);
        }
    }
    public void Login(View v)
    {
        TextView et = (TextView)findViewById(R.id.editText);
        TextView et1 = (TextView) findViewById(R.id.editText1);
        String user = et.getText().toString();
        String pass = et1.getText().toString();
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        Loginuser(user,pass,(String.valueOf(spinner1.getSelectedItem())));
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void Loginuser(final String name, final String pass,final String dept)
    {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            ProgressDialog pd = new ProgressDialog(AdminLogin.this);
            protected void onPreExecute()
            {
                pd.setMessage("Attempting Login!");
                pd.show();
            }

            protected String doInBackground(String... arg0)
            {
                try
                {
                    URL url = new URL("http://checkoutstaff.000webhostapp.com/log2.php"); // here is your URL path
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("us", name);
                    postDataParams.put("pa", pass);
                    postDataParams.put("de",dept);
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
                            break;
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
                try
                {
                    pd.dismiss();
                    if (result.contains("Succes"))
                    {
                        SharedPreferences sp = getSharedPreferences("Login", 0);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("Aname", name);
                        Ed.putString("Apass", pass);
                        Ed.putString("dept", dept);
                        Ed.commit();
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(AdminLogin.this, AdminOptions.class);
                        finishAffinity();
                        startActivity(myIntent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_LONG).show();
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
        sendPostReqAsyncTask.execute(name,pass,dept);
    }
}
