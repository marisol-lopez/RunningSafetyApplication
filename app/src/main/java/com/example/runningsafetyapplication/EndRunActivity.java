package com.example.runningsafetyapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class EndRunActivity extends AppCompatActivity {

    private static final String TAG = "EndRunActivity";

    Intent intent;
    String jsonString;
    JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_run);

        try {
            TextView timeView = (TextView) findViewById(R.id.textView2);
            jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));
            String userSetEndTime = jsonObj.getString("end_time");
            timeView.setText(userSetEndTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        *** THIS IS CODE FOR GETTING CURRENT TIME AND SHOWS AS JUL 19 2017, 12:25:09PM
//        final TextView countDown = (TextView) findViewById(R.id.textView);
//        String setEndTime = jsonObj.getString("end_time");
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

//        Jul 19, 2017 12:38:55 PM

//        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM DD, yyyy hh:mm:ss aa");
//        SimpleDateFormat setDateFormat = new SimpleDateFormat("hh:mmaa");
//
//        SimpleDateFormat dateFormat2 = new SimpleDateFormat("kk:mm:ss");
//        try {
//            jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));
//            String userSetEndTime = jsonObj.getString("end_time");
//            Date currentDateTime = currentDateFormat.parse(currentDateTimeString);
//            Date setDateTime = setDateFormat.parse(userSetEndTime);
//            String currentTimeString = dateFormat2.format(currentDateTime);
//            String setTimeString = dateFormat2.format(setDateTime);
//            System.out.println("ffffff");
//            System.out.println(currentTimeString);
//            System.out.println(setTimeString);
//            System.out.println(setTimeString.getTime() - currentTimeString.getTime());
//            long subtraction = setTimeString.getTime() - currentTimeString.getTime();
//            System.out.println("ffffff");
//
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



//        &&&&&&&
//        System.out.println("I should have time in ");
//        try{
//            jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));
//            String userSetEndTime = jsonObj.getString("end_time");
//            Calendar c1 = Calendar.getInstance();
//            c1.setTime(new SimpleDateFormat("MMM DD, yyyy hh:mm:ss aa").parse(DateFormat.getDateTimeInstance().format(new Date())));
//            Calendar c2 = Calendar.getInstance();
//            c2.setTime(new SimpleDateFormat("hh:mmaa").parse(userSetEndTime));
//            System.out.println(c2.getTimeInMillis());
//
//            long c2Millis = c2.getTimeInMillis();
//            int seconds = (int) (c2Millis / 1000) % 60 ;
//            int minutes = (int) ((c2Millis / (1000*60)) % 60);
//            int hours   = (int) ((c2Millis / (1000*60*60)) % 24);
//            String urlString = String.format("hours=%s;minutes=%s;seconds=%s;", hours, minutes, seconds);
//            System.out.println(urlString);

//            String string1 = "05:00:00 PM";
//            Date time1 = new SimpleDateFormat("HH:mm:ss aa").parse(string1);
//            Calendar calendar1 = Calendar.getInstance();
//            calendar1.setTime(time1);
//            System.out.println("hhhhh");
//            System.out.println(calendar1);
//            System.out.println("hhhhh");
//
////            Calendar c2 = Calendar.getInstance();
////            c2.setTime(new SimpleDateFormat("hh:mmaa").parse(userSetEndTime));
//////            System.out.println(c2.getTimeInMillis());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("milliseconds within this block");

//      &&&&&&&


//        **** THIS IS CODE TO CREATE COUNTDOWN****
//        final TextView countDown = (TextView) findViewById(R.id.textView);
//        CountDownTimer Count = new CountDownTimer(30000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                countDown.setText("Seconds remaining: " + millisUntilFinished / 1000);
//            }
//            public void onFinish() {
//                countDown.setText("Finished");
//            }
//        };

//        Count.start();
    }

    public void updateRunStatus(View v) {
        try {
            jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));
//            String setEndTime = jsonObj.getString("end_time");
//            System.out.println(convertTo24HoursFormat(setEndTime));

        } catch (JSONException e) {
            e.printStackTrace();
        }
            if (jsonObj.length() > 0) {
                new SendStatusJsonDataToServer().execute(String.valueOf(jsonObj));
        }
//        ***THIS IS CODE TO CHANGE STRING TIME TO TIME OBJECT AND SHOWS AS 18:30:00****
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
//        SimpleDateFormat dateFormat2 = new SimpleDateFormat("kk:mm:ss");
//        try {
//            Date date = dateFormat.parse(jsonObj.getString("end_time"));
//            String out = dateFormat2.format(date);
//            System.out.println("bbbbbb");
//            Log.e("Time", out);
//            System.out.println("bbbbb");
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    class SendStatusJsonDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            System.out.println("selection");
            System.out.println(JsonDATA);
            System.out.println("selection");
//            http://10.0.2.2:3000/runs/end_run?

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://10.0.2.2:3000/runs/end_run?");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
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
                    return null;
                }
                JsonResponse = buffer.toString();
                System.out.println("*****");
                System.out.println(JsonResponse);
                System.out.println("******");
                Log.e(TAG, JsonResponse);
                try {
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
