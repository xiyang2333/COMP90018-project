package com.example.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.HttpClient;
import com.example.service.entity.ActivityPart;
import com.example.service.entity.SearchActivityRequest;
import com.example.service.entity.SearchActivityResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static com.example.service.InterfaceURL.SEARCH_ACTIVITY_URL;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";
    private ListView mLvActivities;
    private ArrayList<MyMarker> markersArray = new ArrayList<MyMarker>();
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // get userId
        userId = getIntent().getIntExtra("userId", 0);

        // bind
        mLvActivities = findViewById(R.id.lv_join_activity);

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
                    SearchActivityResponse response = HttpClient.httpPost(SEARCH_ACTIVITY_URL, request, SearchActivityRequest.class, SearchActivityResponse.class);
                    List<ActivityPart> activities = response.getActivityList();
                    for (int i = 1; i < activities.size(); i++) {
                        if (response.getResponseStatus() != 0) {
                            break;
                        } else {
                            if (activities.get(i).getLatitude() != null) {
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

                } catch (Exception e) {
                    // run for test
                    Log.d(TAG, "updateMarkers: exception and show me marker list" + e.getMessage());
                    MyMarker marker = new MyMarker();
                    marker.setLatLng(new LatLng(-37.7963, 144.9614));
                    marker.setSnippet("testDes");
                    marker.setTitle("test");
                    markersArray.add(marker);
                }
            }
        }).start();
    }

    /**
     *  display list view after get activities information
     */
    private Handler handler = new Handler(){
        public  void handleMessage(Message msg){
            ActivityAdapter adapter = new ActivityAdapter(markersArray);
            mLvActivities.setAdapter(adapter);
            // set list view items click listener
            mLvActivities.setOnItemClickListener(new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(JoinActivity.this,"我是item点击事件 i = " + i + "l = " + l,Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
