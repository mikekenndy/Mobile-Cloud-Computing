package com.example.farthest_from_home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    EditText userAddress;
    Button submitBtn;
    String homeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        userAddress = (EditText) findViewById(R.id.start_homeAddrEditText);
        submitBtn = (Button) findViewById(R.id.start_submitBtn);

        // Get and store user address
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                homeAddress = userAddress.getText().toString();

                Intent sendHomeAddress = new Intent(StartActivity.this, MainActivity.class);
                sendHomeAddress.putExtra("userAddress", homeAddress);
                startActivity(sendHomeAddress);
            }
        });
    }
}
