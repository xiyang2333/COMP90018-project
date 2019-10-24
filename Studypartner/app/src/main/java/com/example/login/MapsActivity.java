package com.example.login;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.service.HttpClient;
import com.example.service.entity.ActivityPart;
import com.example.service.entity.SearchActivityRequest;
import com.example.service.entity.SearchActivityResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

import static com.example.service.InterfaceURL.SEARCH_ACTIVITY_URL;

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
    private LatLng myPosition;
    private LatLng selectPos;
    private ArrayList<MyMarker> markersArray = new ArrayList<MyMarker>();
    private Button mBtnCreate;
    private Button mBtnJoin;
    private Button mBtnGoHome;


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
        mBtnCreate = findViewById(R.id.create_btn);
        mBtnJoin = findViewById(R.id.join_btn);
        mBtnGoHome = findViewById(R.id.gohome_btn);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        userId = getIntent().getIntExtra("userId",0);

        Log.d(TAG, "onCreate: userId" + userId);
        getLocationPermissions();
        updateMarkers();
    }

    /**
     * update marker array list from database when this activity created
     */
    private void updateMarkers() {
        markersArray = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SearchActivityRequest request = new SearchActivityRequest();
                    request.setUserId(userId);
                    Log.d(TAG, "updateMarkers: show me user id" + userId);
                    SearchActivityResponse response = HttpClient.httpPost(SEARCH_ACTIVITY_URL, request,SearchActivityRequest.class, SearchActivityResponse.class);
                    List<ActivityPart> activities = response.getActivityList();
                    for(int i=1; i<activities.size(); i++){
                        if(response.getResponseStatus() != 0){
                            break;
                        }else{
                            if(activities.get(i).getLatitude() != null) {
                                MyMarker marker = new MyMarker();
                                marker.setTitle(activities.get(i).getActivityName());
                                marker.setSnippet(activities.get(i).getActivityDescription());
                                marker.setLatLng(new LatLng(activities.get(i).getLatitude().doubleValue(), activities.get(i).getLongitude().doubleValue()));
                                markersArray.add(marker);
                                //createMarker(marker.getLatLng(), marker.getTitle(), marker.getSnippet());
                                Log.d(TAG, "updateMarkers: no exception and show me marker list" + markersArray);

                            }
                        }
                    }
                    Message msg = new Message();
                    handler.sendMessage(msg);

                }catch (Exception e){
                    // run for test
                    Log.d(TAG, "updateMarkers: exception and show me marker list" + e.getMessage());
                    MyMarker marker = new MyMarker();
                    marker.setLatLng(new LatLng(-37.7963,144.9614));
                    marker.setSnippet("testDes");
                    marker.setTitle("test");
                    markersArray.add(marker);
                }
            }
        }).start();



    }
    private Handler handler = new Handler(){
        public  void handleMessage(Message msg){
            for(int i = 0 ; i < markersArray.size() ; i++) {
                createMarker(markersArray.get(i).getLatLng(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet());
            }
        }
    };

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

            Log.d(TAG, "onMapReady: show my position");
            getDeviceLocation();


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            //initial search bar

            // set map click listener
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.d(TAG, "onMapClick: "+ latLng.latitude + " / " + latLng.longitude + "]");
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet("your activity")
                    );
                    selectPos = latLng;
                }
            });


        }
        /**
         * create a new activity
         */
        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, CreateActivityActivity.class);
                Log.d(TAG, "onClick: create button is clicked");
                if(selectPos == null){
                    Toast.makeText(MapsActivity.this,"Please select a location",Toast.LENGTH_LONG).show();
                }else{
                    intent.putExtra("latitude",selectPos.latitude);
                    intent.putExtra("longitude",selectPos.longitude);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }

            }
        });

        /**
         * join an activity
         */
        mBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, JoinActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        /**
         * back to home activity
         */
        mBtnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

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
