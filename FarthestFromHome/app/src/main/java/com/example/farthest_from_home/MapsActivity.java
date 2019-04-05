package com.example.farthest_from_home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;

    private GoogleMap mMap;
    private LocationManager mManager;
    private ImageButton addBtn;
    private LinearLayout addLayout;

    private LatLng home;
    private LatLng currentLocation;

    private Boolean currentLocFound = false;
    private double longitude = 151;
    private double latitude = -34;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addLayout = (LinearLayout) findViewById(R.id.add_layout);
        addLayout.bringToFront();
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation(v);

                Intent sendBack = new Intent(MapsActivity.this, MainActivity.class);
                sendBack.putExtra("Distance", CalculationByDistance(home, currentLocation));
                startActivity(sendBack);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getLocationPermission();
    }

    public void updateLocation(View view)
    {
        Intent intent = getIntent();
        String location = intent.getStringExtra("userAddress");
        List<Address> addressList;

        if (location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try
            {
                addressList = geocoder.getFromLocationName(location, 1);
                Address address = addressList.get(0);
                home = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(home).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(home));

                Thread.sleep(2000);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Invalid address", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocationPermission()
    {
        // Get coarse location
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            mManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, this);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        try
        {
            mManager.removeUpdates(this);
        }
        catch (SecurityException e)
        {
            Log.e("ERR", "No location update permission remover");
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void onLocationChanged(Location location)
    {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        currentLocation = new LatLng(latitude, longitude);
        if(!currentLocFound)
        {
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in at your location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f));
            currentLocFound = true;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        // Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        try
        {
            int Radius = 6371;// radius of earth in Km
            double lat1 = StartP.latitude;
            double lat2 = EndP.latitude;
            double lon1 = StartP.longitude;
            double lon2 = EndP.longitude;
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                    * Math.sin(dLon / 2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double valueResult = Radius * c;
            double km = valueResult / 1;
            DecimalFormat newFormat = new DecimalFormat("####");
            int kmInDec = Integer.valueOf(newFormat.format(km));
            double meter = valueResult % 1000;
            int meterInDec = Integer.valueOf(newFormat.format(meter));
            Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec);

            return Radius * c;
//            return 25.0;
        }
        catch (Exception e)
        {
            return -1.0;
        }
    }
}