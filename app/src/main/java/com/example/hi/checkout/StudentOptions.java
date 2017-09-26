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
import android.widget.EditText;
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
public class StudentOptions extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_options);
    }
    public void Fetch(View v)
    {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner10);
        EditText et=(EditText)findViewById(R.id.editText13);
        String cid=et.getText().toString();
        if(spinner1.getVisibility()==View.GONE)
        {
            Loginuser(cid,"");
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putString("department",String.valueOf(spinner1.getSelectedItem()));
            bundle.putString("cid",et.getText().toString());
            Intent myIntent = new Intent(StudentOptions.this,StudentChoice.class);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        }
    }
    public void InsertSpinner(String result)
    {
        String[] deptarr = result.split("###");
        Spinner spinner = (Spinner) findViewById(R.id.spinner10);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, deptarr);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setVisibility(View.VISIBLE);
        TextView et = (TextView)findViewById(R.id.editText13);
        et.setFocusable(false);
        Button b=(Button)findViewById(R.id.button16);
        b.setText("Fetch Details");
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void Loginuser(final String cid,final String dept)
    {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            ProgressDialog pd = new ProgressDialog(StudentOptions.this);
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
        sendPostReqAsyncTask.execute(cid,dept);
    }
}
