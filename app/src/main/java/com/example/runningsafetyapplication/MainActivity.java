package com.example.runningsafetyapplication;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.os.AsyncTask;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import static android.R.attr.value;
import static com.example.runningsafetyapplication.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    EditText usernameField, timeField, contactField;

    String usernameFieldInput, routeFieldInput, timeFieldInput, contactName, contactPhoneNumber;
    JSONObject value;
    private static final String TAG = "MainActivity";

//      ImageView imageView;
    Calendar c = Calendar.getInstance();
    private final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText) findViewById(R.id.usernameForm);
//        routeField = (Fragment) findViewById(R.id.routeForm);
        timeField = (EditText) findViewById(R.id.timeForm);
        contactField = (EditText) findViewById(R.id.contactForm);
//        EditText timeField = (EditText) findViewById(R.id.timeForm);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
//                LatLng coordinates = place.getLatLng();
//                String mapsUrlString = "https://www.google.com/maps/search/?api=1&query=";
                String mapsUrlString = "https://www.google.com/maps/search/?api=1&query=";
                String query = URLEncoder.encode(place.getName().toString());
//
//                String longitude = String.valueOf(coordinates.longitude);
//                String latitude = String.valueOf(coordinates.latitude);
//                routeFieldInput = mapsUrlString + latitude + "," + longitude;

                routeFieldInput = mapsUrlString + query;
                try {
                    URL mapsUrl = new URL(routeFieldInput);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                System.out.println("%%%%%%");
                System.out.println(routeFieldInput);
                System.out.println("%%%%%%");

//                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTimePicker timePicker = new MyTimePicker();
                new TimePickerDialog(MainActivity.this, timePicker, c
                        .get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
            }
        });

//        Button btnCamera = (Button) findViewById(R.id.btnCamera);
//
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,0);
//            }
//        });

    }

    public void callContacts(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//        imageView.setImageBitmap(bitmap);

        EditText contactText = (EditText) findViewById(R.id.contactForm);

        if (requestCode == PICK_CONTACT) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                String phoneNo = null;
                String name = null;
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                c.moveToFirst();

                int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                phoneNo = c.getString(phoneIndex);
                name = c.getString(nameIndex);
                contactText.setText(name + " " + phoneNo);

            }
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//        imageView.setImageBitmap(bitmap);
//
//    }

    public void startRun(View v) {
        usernameFieldInput = usernameField.getText().toString();
        timeFieldInput = timeField.getText().toString();
        String contactFieldInput = contactField.getText().toString();

        String[] splitContactString = contactFieldInput.split("\\s+");
        contactName = splitContactString[0] + " " + splitContactString[1];
        contactPhoneNumber = splitContactString[2];

        JSONObject value = new JSONObject();

        try {
            value.put("username", usernameFieldInput);
            value.put("location", routeFieldInput);
            value.put("end_time", timeFieldInput);
            value.put("contact_name", contactName);
            value.put("contact_phone_number", contactPhoneNumber);
            System.out.println("********");
            System.out.println(value);
            System.out.println("&&&&&&&&");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (value.length() > 0) {
            new SendJsonDataToServer().execute(String.valueOf(value));
//            #call to async class
        }

        Intent intent = new Intent(this, EndRunActivity.class);
        intent.putExtra("jsonObject", value.toString());
        startActivity(intent);
    }
    class SendJsonDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];

//            http://10.0.2.2:3000/runs?

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://10.0.2.2:3000/runs?");
                System.out.println("&&&&&&^^^^");
                System.out.println(url);
                System.out.println("&&&&&&^^^^");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writer
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);

// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//input stream
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

    class MyTimePicker implements TimePickerDialog.OnTimeSetListener {
        EditText editText = (EditText) findViewById(R.id.timeForm);

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            editText.setVisibility(View.VISIBLE);

            String am_pm = "";
            String hourChosen = "";
            String minuteChosen = "";

            if (hourOfDay > 12) {
                hourChosen = Integer.toString(hourOfDay - 12);
                am_pm = "PM";
                if ((hourOfDay - 12) < 10) {
                    hourChosen = Integer.toString(hourOfDay - 12);
                    hourChosen = "0" + hourChosen;
                    am_pm = "PM";
                }
            } else if (hourOfDay == 12) {
                hourChosen = Integer.toString(12);
                am_pm = "PM";
            } else if (hourOfDay == 0) {
                hourChosen = Integer.toString(12);
                am_pm = "AM";
            } else {
                am_pm = "AM";
                hourChosen = Integer.toString(hourOfDay);
                if (hourOfDay < 10) {
                    hourChosen = Integer.toString(hourOfDay);
                    hourChosen = "0" + hourChosen;
                }
            }
            if (minute < 10) {
                minuteChosen = Integer.toString(minute);
                minuteChosen = "0" + minuteChosen;
            } else {
                minuteChosen = Integer.toString(minute);
            }
            editText.setText(hourChosen + ":" + minuteChosen + am_pm);
        }
    }

}



