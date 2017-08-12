package com.example.hi.checkout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
public class RegisterStaff extends Activity
{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.register_staff);
    }
    public void Student(View v)
    {
        Button t = (Button) findViewById(R.id.button8);
        t.setText("Student Login");
    }
    public void Register(View v)
    {
        TextView editText = (TextView)findViewById(R.id.editText5);
        TextView editText2 = (TextView) findViewById(R.id.editText6);
        String user = editText.getText().toString();
        String pass = editText2.getText().toString();
        RegisterStaffDetails(user,pass);
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void RegisterStaffDetails(final String name, final String pass){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            protected void onPreExecute(){}

            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://checkoutstaff.000webhostapp.com/register.php"); // here is your URL path
                    SharedPreferences sp1=getSharedPreferences("Login",0);
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("us", name);
                    postDataParams.put("pa", pass);
                    postDataParams.put("de",sp1.getString("dept", null));
                    postDataParams.put("status","---");
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
                    Toast.makeText(getApplicationContext(), "Registered Successfully!",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(RegisterStaff.this,
                            AdminOptions.class);
                    finishAffinity();
                    startActivity(myIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to Register",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(RegisterStaff.this,
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
        sendPostReqAsyncTask.execute(name,pass);
    }
}
