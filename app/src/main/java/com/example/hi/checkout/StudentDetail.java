package com.example.hi.checkout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.TableLayout;
import android.widget.TableRow;
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
public class StudentDetail extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_detail);
        Bundle bundle = getIntent().getExtras();
        String dept = bundle.getString("department");
        String cid=bundle.getString("cid");
        RetrieveDetails(dept,cid);
    }
    public void draw(String res)
    {
        try {
            String[] arr = res.split("#");
            TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row1.setLayoutParams(lp1);
            TextView tv1 = new TextView(this);
            TextView t11 = new TextView(this);
            TextView t12 = new TextView(this);
            tv1.setText("Staff Name");
            tv1.setTypeface(null, Typeface.BOLD);
            tv1.setPadding(20, 30, 40, 50);
            tv1.setBackgroundResource(R.drawable.cell_shape);
            t11.setText("Current Status");
            t11.setTypeface(null, Typeface.BOLD);
            t11.setPadding(20, 30, 40, 50);
            t11.setBackgroundResource(R.drawable.cell_shape);
            t12.setText("Return time to Cabin");
            t12.setTypeface(null, Typeface.BOLD);
            t12.setPadding(20, 30, 40, 50);
            t12.setBackgroundResource(R.drawable.cell_shape);
            row1.addView(tv1);
            row1.addView(t11);
            row1.addView(t12);
            ll.addView(row1);
            for (int i = 0; i < arr.length; i += 3) {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tv = new TextView(this);
                TextView t1 = new TextView(this);
                TextView t2 = new TextView(this);
                tv.setText(arr[i]);
                tv.setPadding(20, 30, 40, 50);
                tv.setBackgroundResource(R.drawable.cell_shape);
                t1.setText(arr[i + 1]);
                t1.setPadding(20, 30, 40, 50);
                t1.setBackgroundResource(R.drawable.cell_shape);
                t2.setText(arr[i + 2]);
                t2.setPadding(20, 30, 40, 50);
                t2.setBackgroundResource(R.drawable.cell_shape);
                row.addView(tv);
                row.addView(t1);
                row.addView(t2);
                ll.addView(row);
            }
            Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Failed to Update",Toast.LENGTH_LONG).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void RetrieveDetails(final String dept,final String cid){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            ProgressDialog pd = new ProgressDialog(StudentDetail.this);
            protected void onPreExecute()
            {
                pd.setMessage("Fetching...");
                pd.show();
            }

            protected String doInBackground(String... arg0)
            {
                try
                {
                    URL url = new URL("http://checkoutstaff.000webhostapp.com/retrieve.php");
                    JSONObject postDataParams = new JSONObject();
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
                draw(result);
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
        sendPostReqAsyncTask.execute(dept,cid);
    }
}
