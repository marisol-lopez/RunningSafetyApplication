package com.example.runningsafetyapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//  ImageView imageView;
    Calendar c = Calendar.getInstance();
    private final int PICK_CONTACT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText timeField = (EditText) findViewById(R.id.timeForm);
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
        EditText contactText = (EditText) findViewById(R.id.contactForm);

        if(requestCode == PICK_CONTACT) {
            if(resultCode == AppCompatActivity.RESULT_OK) {
                String phoneNo = null ;
                String name = null;
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                c.moveToFirst();

                int  phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int  nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                phoneNo = c.getString(phoneIndex);
                name = c.getString(nameIndex);
                contactText.setText(name + " " + phoneNo);


//                if(c.moveToFirst()){
//                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//                    Toast.makeText(this, "You picked:" + name, Toast.LENGTH_LONG).show();
//                }
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





    public void startRun(View view) throws JSONException {
        EditText usernameField = (EditText) findViewById(R.id.usernameForm);
        String usernameFieldInput = usernameField.getText().toString();

        EditText routeField = (EditText) findViewById(R.id.routeForm);
        String routeFieldInput = routeField.getText().toString();

        EditText timeField = (EditText) findViewById(R.id.timeForm);
        String timeFieldInput = timeField.getText().toString();

        EditText contactField = (EditText) findViewById(R.id.contactForm);
        String contactFieldInput = contactField.getText().toString();
        String[] splitContactString = contactFieldInput.split("\\s+");
        String name = splitContactString[0] + " " + splitContactString[1];
        String contactPhoneNumber = splitContactString[2];

    JSONObject json = new JSONObject();
    JSONArray inputValues = new JSONArray();
        JSONObject value = new JSONObject();
        value.put("username", usernameFieldInput);
        value.put("route", routeFieldInput);
        value.put("end_time", timeFieldInput);
        value.put("contact_name", name);
        value.put("contact_phone_number", contactPhoneNumber);
    inputValues.put(value);
        json.put("form_data", inputValues);
        System.out.println("$$$$$$");
        System.out.println(json.toString());
        System.out.println("$$$$$$");

    }

    class MyTimePicker implements TimePickerDialog.OnTimeSetListener {
        EditText editText = (EditText) findViewById(R.id.timeForm);

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            editText.setVisibility(View.VISIBLE);

            String am_pm = "";
            String hourChosen = "";
            String minuteChosen = "";

            if(hourOfDay > 12) {
                hourChosen = Integer.toString(hourOfDay - 12);
                am_pm = "PM";
                if((hourOfDay - 12) < 10) {
                    hourChosen = Integer.toString(hourOfDay - 12);
                    hourChosen = "0" + hourChosen;
                    am_pm = "PM";
                }
            } else if(hourOfDay == 12) {
                hourChosen = Integer.toString(12);
                am_pm = "PM";
            } else if (hourOfDay == 0) {
                hourChosen = Integer.toString(12);
                am_pm = "AM";
            } else {
                am_pm = "AM";
                hourChosen = Integer.toString(hourOfDay);
                if(hourOfDay < 10) {
                    hourChosen = Integer.toString(hourOfDay);
                    hourChosen = "0" + hourChosen;
                }
            }
            if(minute < 10) {
                minuteChosen = Integer.toString(minute);
                minuteChosen = "0" + minuteChosen;
            } else {
                minuteChosen = Integer.toString(minute);
            }
            editText.setText(hourChosen + ":" + minuteChosen + am_pm);
        }
    }

}


