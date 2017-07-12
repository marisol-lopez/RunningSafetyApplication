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

import java.util.ArrayList;
import java.util.Calendar;

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
//                Toast.makeText(MainActivity.this, "Number="+phoneNo, Toast.LENGTH_LONG).show();
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





    public void startRun(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        // need to create variable and store the view/widget we are trying to access
        EditText routeField = (EditText) findViewById(R.id.routeForm);

        // create a string variable and store what is in view.widget in it
        String routeFieldInput = routeField.getText().toString();

        EditText timeField = (EditText) findViewById(R.id.timeForm);
        String timeFieldInput = timeField.getText().toString();

        EditText contactField = (EditText) findViewById(R.id.contactForm);
        String contactFieldInput = contactField.getText().toString();

        ArrayList<String> inputValues = new ArrayList();

        inputValues.add(routeFieldInput);
        inputValues.add(timeFieldInput);
        inputValues.add(contactFieldInput);

        intent.putStringArrayListExtra("array", inputValues);
        startActivity(intent);

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
                minuteChosen = Integer.toString(hourOfDay);
            }
            editText.setText(hourChosen + ":" + minuteChosen + am_pm);
//            pickDate.setVisibility(View.GONE);

        }
    }

}


