package com.example.lab04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveData extends AppCompatActivity {

    // Activity objects
    TextView mReceivedText;

    // Objects for sending data between devices
//    RequestQueue queue = Volley.newRequestQueue(this);
//    String url = "http:/www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_data);

        // Get intent data from other application
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type!= null)
        {
            if("text/plain".equals(type)){}
//                handleSentText(intent);
            else
                //TODO: Handle other types of data input
                ;

        }
    }
}
