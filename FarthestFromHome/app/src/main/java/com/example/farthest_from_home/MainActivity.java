package com.example.farthest_from_home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView displayAddress;
    TextView farthestDistance;
    Button updateLocBtn;
    Button updateAddrBtn;
    String userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display farthest distance
        farthestDistance = (TextView) findViewById(R.id.main_distanceTextView);
        farthestDistance.setText("Distance from home: " + (int) displayDistance() + " miles.");

        // Display user address
        displayAddress = (TextView) findViewById(R.id.main_displayAddress);
        userAddress = getAddressFromStart();
        displayAddress.setText(userAddress);

        // Button to take current location and calculate distance
        updateLocBtn = (Button) findViewById(R.id.main_updateLocBtn);
        updateLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                mapIntent.putExtra("userAddress", userAddress);
                startActivity(mapIntent);
            }
        });

        // Button to change home address
        updateAddrBtn = (Button) findViewById(R.id.main_updateHomeBtn);
        updateAddrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeHome = new Intent(MainActivity.this, StartActivity.class);
                startActivity(changeHome);
            }
        });

        // Display address

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayAddress = (TextView) findViewById(R.id.main_displayAddress);
        displayAddress.setText(userAddress);
    }

    private double displayDistance()
    {
        Double distance = 0.0;
        try
        {
            Intent intent = getIntent();
            return intent.getDoubleExtra("Distance", distance);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Distance could not be retrieved", Toast.LENGTH_LONG).show();
            return -1.0;
        }
    }

    public String getAddressFromStart()
    {
        Intent intent = getIntent();
        return intent.getStringExtra("userAddress");
    }
}
