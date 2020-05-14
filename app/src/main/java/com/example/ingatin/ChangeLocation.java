package com.example.ingatin;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.ManagerFactoryParameters;

public class ChangeLocation extends FragmentActivity implements OnMapReadyCallback{
    private static final String TAG = "ChangeLocation";
    private static final int REQUEST_LOCATION = 1707;
    public static final String LOCATION_LATITUDE = "loc_lat";
    public static final String LOCATION_LONGITUDE = "loc_lng";
    public static final String LOCATION_ADDRESS = "loc_addr";

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private Marker currentMarker;
    private LatLng currentLocationLatLng;
    private boolean mLocationPermissionsGranted = false;
    private String address;
    public TextView tv_title, tv_location;

    // Get current location code from Mitch : https://github.com/mitchtabian/Google-Maps-Google-Places
    // /tree/e8ad8f165c7df3f25b6a9128c70f4c0e3ed84f94

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_location);
        tv_title = this.findViewById(R.id.tv_location_name);
        tv_location = this.findViewById(R.id.tv_location_street);

        getLocationPermission();

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
//        java.lang.SecurityException: my location requires permission ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION
        mMap.setMyLocationEnabled(true);

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
//            mMap.setMyLocationEnabled(true);
//            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }

        // Add a marker in Sydney and move the camera
        if (currentLocationLatLng == null) currentLocationLatLng = new LatLng(0, 0);
        currentMarker = mMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("Everybody yeah").draggable(true));
        currentMarker.setAlpha(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocationLatLng));
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                currentMarker.setVisible(false);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng mapCenter = mMap.getCameraPosition().target;
                currentMarker.setPosition(mapCenter);
                currentMarker.setVisible(true);
                currentLocationLatLng = mapCenter;

                String theAddress = getLocationAddress(currentLocationLatLng);
                String locationName = getLocationName(currentLocationLatLng);
                address = theAddress;

                tv_title.setText(locationName);
                tv_location.setText(theAddress);
            }
        });
    }

    private String getLocationAddress(LatLng currentLocationLatLng) {
        String theAddress = "";
        Geocoder geocoder = new Geocoder(ChangeLocation.this, Locale.getDefault());
        try {
            List <Address> addresses = geocoder.getFromLocation(currentLocationLatLng.latitude,
                    currentLocationLatLng.longitude, 1);;
            theAddress = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return theAddress;
    }

    private String getLocationName(LatLng currentLocationLatLng) {
        String locationName = "";
        Geocoder geocoder = new Geocoder(ChangeLocation.this, Locale.getDefault());
        try {
            List <Address> addresses = geocoder.getFromLocation(currentLocationLatLng.latitude,
                    currentLocationLatLng.longitude, 1);;
            locationName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationName;
    }

    private void getDeviceLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if(mLocationPermissionsGranted) {
                Task location = mFusedLocationClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                    currentLocation.getLongitude()), 11f);
                            currentLocationLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }
                        else {
                            Toast.makeText(ChangeLocation.this,
                                    "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng coord, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, zoom));
    }

    private void getLocationPermission() {
        String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this, permissions, REQUEST_LOCATION);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, REQUEST_LOCATION);
        }

    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case REQUEST_LOCATION:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    public void backToAdd(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void sendResult(View view) {
        Intent locIntent = new Intent();
        locIntent.putExtra(LOCATION_LATITUDE, currentLocationLatLng.latitude);
        locIntent.putExtra(LOCATION_LONGITUDE, currentLocationLatLng.longitude);
        locIntent.putExtra(LOCATION_ADDRESS, address);
        setResult(RESULT_OK, locIntent);
        finish();
    }
}