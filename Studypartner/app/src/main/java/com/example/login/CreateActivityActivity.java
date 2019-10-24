package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.HttpClient;
import com.example.service.entity.CreateActivityRequest;
import com.example.service.entity.CreateActivityResponse;

import java.math.BigDecimal;
import java.util.Date;

import static com.example.service.InterfaceURL.CREATE_ACTIVITY_URL;

public class CreateActivityActivity extends AppCompatActivity {
    private static final String TAG = "CreateActivityActivity";
    private Double latitude;
    private Double longitude;
    private Button mBtnCreate;
    private EditText mEtActTitle;
    private EditText mEtActDes;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity);
        // get intent information
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        userId = getIntent().getIntExtra("userId",0);

        // bind
        mBtnCreate = findViewById(R.id.btn_create);
        mEtActTitle = findViewById(R.id.et_activityTitle);
        mEtActDes = findViewById(R.id.et_activityDes);

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get text from edit text
                final String title = mEtActTitle.getText().toString().trim();
                final String des = mEtActDes.getText().toString().trim();
                if (title.length()>0 && des.length()>0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CreateActivityRequest request = new CreateActivityRequest();
                            request.setUserId(userId);
                            request.setActivityName(title);
                            request.setActivityDescription(des);
                            Date date = new Date(System.currentTimeMillis());
                            request.setTime(date);

                            // set coordinate for activity location
                            request.setLatitude(new BigDecimal(latitude));
                            request.setLongitude(new BigDecimal(longitude));

                            // send to backend
                            CreateActivityResponse response = HttpClient.httpPost(CREATE_ACTIVITY_URL, request, CreateActivityRequest.class, CreateActivityResponse.class);

                            Log.d(TAG, "onClick: tell me activity is created" + response);
                            Intent intent = new Intent(CreateActivityActivity.this, ShowCreateActivity.class);
                            intent.putExtra("userId", userId);
                            CreateActivityActivity.this.startActivityForResult(intent, 1);
                        }
                    }).start();
                    Toast.makeText(CreateActivityActivity.this,"activity created", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CreateActivityActivity.this, "fail to create activity, try again", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: activity is not created");
                }
            }
        });



    }

}
