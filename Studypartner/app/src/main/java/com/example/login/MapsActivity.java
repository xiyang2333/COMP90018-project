package com.example.login;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.service.HttpClient;
import com.example.service.entity.GetActivityRequest;
import com.example.service.entity.GetActivityResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

import static com.example.service.InterfaceURL.GET_ACTIVITY_URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;



    //vars
    private boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private int userId;
    LatLng myPosition;
    ArrayList<MyMarker> markersArray = new ArrayList<MyMarker>();


    protected void createMarker(LatLng lng, String title, String snippet) {

        mMap.addMarker(new MarkerOptions()
        .position(lng)
        .anchor(0.5f, 0.5f)
        .title(title)
        .snippet(snippet));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getLocationPermissions();
        updateMarkers();
        userId = getIntent().getIntExtra("userId",0);
        Log.d(TAG, "onCreate: userId" + userId);
    }

    /**
     * update marker array list from database when this activity created
     */
    private void updateMarkers() {
        markersArray = new ArrayList<MyMarker>();
        GetActivityRequest request = new GetActivityRequest();
        request.setUserId(userId);
        try {
            for(int i=0; i<10; i++){
                request.setActivityId(i);
                GetActivityResponse response = HttpClient.httpPost(GET_ACTIVITY_URL, request,GetActivityRequest.class, GetActivityResponse.class);
                if(response.getResponseStatus() != 0){
                    break;
                }else{
                    MyMarker marker = new MyMarker();
                    marker.setTitle(response.getActivityName());
                    marker.setSnippet(response.getActivityDescription());
                    marker.setLatLng(new LatLng(response.getLatitude().doubleValue(),response.getLatitude().doubleValue()));
                    markersArray.add(marker);
                }
            }
        }catch (Exception e){
            MyMarker marker = new MyMarker();
            marker.setLatLng(new LatLng(37.4200,-122.062));
            marker.setSnippet("testDes");
            marker.setTitle("test");
            markersArray.add(marker);
        }
    }

    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
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
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            // create markers according to Marker list
            Log.d(TAG, "onMapReady: create marker here");
            for(int i = 0 ; i < markersArray.size() ; i++) {
                createMarker(markersArray.get(i).getLatLng(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet());
            }


            getDeviceLocation();
            Log.d(TAG, "onMapReady: show my position");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            //initial search bar
        }
    }



    private void getDeviceLocation() {
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        try{
            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                // Getting latitude of the current location
                double latitude = location.getLatitude();
                // Getting longitude of the current location
                double longitude = location.getLongitude();
                // Creating a LatLng object for the current location
                LatLng latLng = new LatLng(latitude, longitude);
                myPosition = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(myPosition).title("my location"));
                Log.d(TAG, "getDeviceLocation: " + myPosition.latitude + myPosition.longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException:" + e.getMessage());
        }

    }
}
