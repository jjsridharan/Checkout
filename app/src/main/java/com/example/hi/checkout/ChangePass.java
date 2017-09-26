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
public class ChangePass extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_pass);
    }
    public void ChangePass(View v)
    {
        TextView editText = (TextView) findViewById(R.id.editText8);
        TextView editText2 = (TextView) findViewById(R.id.editText9);
        TextView editText3 = (TextView) findViewById(R.id.editText10);
        String oldpass = editText.getText().toString();
        String newpass = editText2.getText().toString();
        String newcpass = editText3.getText().toString();
        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        String old = sp1.getString("Spass", null);
        String user = sp1.getString("Sname",null);
        String dept = sp1.getString("dept",null);
        String cid=sp1.getString("scid",null);
        if (old.compareTo(oldpass)==0 && newcpass.compareTo(newpass)==0)
        {
            ChangePassword(user,newpass,dept,cid);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Incorrect password!" ,
                    Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(ChangePass.this,
                    StaffOptions.class);
            finishAffinity();
            startActivity(myIntent);
        }
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void ChangePassword(final String name, final String pass,final String dept,final String cid){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            ProgressDialog pd = new ProgressDialog(ChangePass.this);
            protected void onPreExecute()
            {
                pd.setMessage("Updating...");
                pd.show();
            }

            protected String doInBackground(String... arg0)
            {

                try {
                    URL url = new URL("http://checkoutstaff.000webhostapp.com/updatepass.php");
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
                    Toast.makeText(getApplicationContext(), "Successfully Updated!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to Update!", Toast.LENGTH_LONG).show();
                }
                Intent myIntent = new Intent(ChangePass.this, StaffOptions.class);
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
        sendPostReqAsyncTask.execute(name,pass,dept,cid);
    }
}
