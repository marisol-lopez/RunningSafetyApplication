package com.example.runningsafetyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        ArrayList<String> inputValues = new ArrayList();
        inputValues = intent.getStringArrayListExtra("array");

        textView1.setText(inputValues.get(0));
        textView2.setText(inputValues.get(1));
        textView3.setText(inputValues.get(2));
    }


}