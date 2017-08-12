package com.example.hi.checkout;

import android.annotation.TargetApi;
import android.app.Activity;
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
 * Created by SRIDHARAN JOTHIRAMAN on 8/12/2017.
 */

public class Announcement extends Activity
{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.announce);
    }
    public void MakeAnnouncement(View v)
    {
        TextView editText2 = (TextView) findViewById(R.id.editText11);
        String message = editText2.getText().toString();
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String dept=sp1.getString("dept", null);
        Announce(message,dept);
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void Announce(final String message,final String dept){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            protected void onPreExecute(){}
            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://checkoutstaff.000webhostapp.com/Announce.php"); // here is your URL path
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("me",message);
                    postDataParams.put("de",dept);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //    conn.setReadTimeout(15000000 /* milliseconds */);
                    //      conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader in=new BufferedReader(
                                new InputStreamReader(
                                        conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";

                        while((line = in.readLine()) != null) {

                            sb.append(line);
                            break;
                        }

                        in.close();
                        return sb.toString();

                    }
                    else {
                        return new String("false : "+responseCode);
                    }
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }

            @Override
            protected void onPostExecute(String result) {
                if(result.contains("Succes"))
                {
                    Toast.makeText(getApplicationContext(), "Announcement Updated Successfully!",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(Announcement.this,
                            AdminOptions.class);
                    finishAffinity();
                    startActivity(myIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), result,
                            Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(Announcement.this,
                            AdminOptions.class);
                    finishAffinity();
                    startActivity(myIntent);
                }
            }
            public String getPostDataString(JSONObject params) throws Exception {

                StringBuilder result = new StringBuilder();
                boolean first = true;

                Iterator<String> itr = params.keys();

                while(itr.hasNext()){

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
        sendPostReqAsyncTask.execute(message,dept);
    }

}
