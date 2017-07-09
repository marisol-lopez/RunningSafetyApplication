package com.example.runningsafetyapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

    }

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

}
