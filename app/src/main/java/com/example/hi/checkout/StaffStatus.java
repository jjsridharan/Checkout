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
public class StaffStatus extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_status);
    }

    public void ChangeStatus(View v)
    {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner6);
        String status=String.valueOf(spinner1.getSelectedItem());
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner7);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner8);
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner9);
        String dept=sp1.getString("dept", null);
        String cid=sp1.getString("scid",null);
        String user=(sp1.getString("Sname",null));
        String time=String.valueOf(spinner2.getSelectedItem())+":"+String.valueOf(spinner3.getSelectedItem())+" "+String.valueOf(spinner4.getSelectedItem());;
        StaffStatusChange(user,status,dept,time,cid);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void StaffStatusChange(final String name, final String status,final String dept,final String time,final String cid)
    {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            ProgressDialog pd = new ProgressDialog(StaffStatus.this);
            protected void onPreExecute()
            {
                pd.setMessage("Updating...");
                pd.show();
            }

            protected String doInBackground(String... arg0)
            {
                try
                {
                    URL url = new URL("http://checkoutstaff.000webhostapp.com/update.php"); // here is your URL path
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("us", name);
                    postDataParams.put("st", status);
                    postDataParams.put("de",dept);
                    postDataParams.put("ti",time);
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
                pd.dismiss();
                if(result.contains("Success"))
                {
                    Toast.makeText(getApplicationContext(), "Status Updated Successfully!",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();

                }
                Intent myIntent = new Intent(StaffStatus.this,StaffOptions.class);
                finishAffinity();
                startActivity(myIntent);
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
        sendPostReqAsyncTask.execute(name,status,dept,time,cid);
    }
}
