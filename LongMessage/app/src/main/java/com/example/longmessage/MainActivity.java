package com.example.longmessage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText mGetText;
    private EditText mGetNumber;
    private Button mSendBtn;

    private String phoneNumber;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sms things
        getSmsPermission();

        // Activity init
        mGetNumber = (EditText) findViewById(R.id.Main_getPhoneNumber);
        mGetText = (EditText) findViewById(R.id.Main_getMessage);
        mSendBtn = (Button) findViewById(R.id.Main_sendButton);

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                phoneNumber = mGetNumber.getText().toString();
                message = mGetText.getText().toString();

                if(!validNumber(phoneNumber))
                {
                    Toast.makeText(MainActivity.this, "Invalid phone number", Toast.LENGTH_LONG).show();
                    return;
                } else if (message == null)
                {
                    Toast.makeText(MainActivity.this, "Message cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                sendMessage(phoneNumber, message);


            }
        });

    }

    private void sendMessage(String phoneNumber, String message)
    {
        final SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        // Ensure message is not too long
        while(message.length() > 160)
        {
            String subMessage = message.substring(0, 160);
            smsManager.sendTextMessage(phoneNumber, null, subMessage, null, null);
            sendIntent.putExtra("sms_body", "default content");
            sendIntent.setType("vnd.android-dir/mms-sms");

            message = message.substring(160, message.length());
        }

        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        sendIntent.putExtra("sms_body", "default content");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    private boolean validNumber(String phoneNumber)
    {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(phoneNumber);

        if(!m.find())
            return false;
        return phoneNumber.length() == 10;
    }

    private void getSmsPermission()
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);
            }
        }
    }
}
