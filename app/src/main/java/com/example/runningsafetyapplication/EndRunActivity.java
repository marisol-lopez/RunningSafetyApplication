package com.example.runningsafetyapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class EndRunActivity extends AppCompatActivity {

    private static final String TAG = "EndRunActivity";

    Intent intent;
    String jsonString;
    JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_run);
    }

    public void updateRunStatus(View v) {
        try {
            jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


//         JSONObject jsonObj = new JSONObject();
//            try {
//                jsonObj.put("status", "complete");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (jsonObj.length() > 0) {
                new SendStatusJsonDataToServer().execute(String.valueOf(jsonObj));
//            #call to async class
        }
    }
    class SendStatusJsonDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            System.out.println("selection");
            System.out.println(JsonDATA);
            System.out.println("selection");

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://10.0.2.2:3000/runs/end_run?");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writer
                urlConnection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
//////set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);

////// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//////input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
//response data
                System.out.println("*****");
                System.out.println(JsonResponse);
                System.out.println("******");
                Log.e(TAG, JsonResponse);
                try {
//send to post execute
                    return JsonResponse;
                } catch (Error e) {
                    e.printStackTrace();
                }
                return null;


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}
