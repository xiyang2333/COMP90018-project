package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.service.HttpClient;
import com.example.service.entity.GetActivityRequest;
import com.example.service.entity.GetActivityResponse;
import com.example.service.entity.JoinActivityRequest;
import com.example.service.entity.JoinActivityResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.service.InterfaceURL.GET_ACTIVITY_URL;
import static com.example.service.InterfaceURL.JOIN_ACTIVITY_URL;

public class JoinSelectedActivity extends AppCompatActivity {

    private int userId;
    private int activityId;
    private TextView mTvActName;
    private TextView mTvActDes;
    private TextView mTvCreator;
    private TextView mTvActTime;
    private Button mBtnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_selected);

        // get userId
        userId = getIntent().getIntExtra("userId", 0);
        activityId = getIntent().getIntExtra("activityId",0);
        // bind
        mTvActName = findViewById(R.id.tv_activityName);
        mTvActDes = findViewById(R.id.tv_activityDes);
        mTvCreator = findViewById(R.id.tv_createUser);
        mTvActTime = findViewById(R.id.tv_time);
        mBtnJoin = findViewById(R.id.join_btn);

        getSelectedActivity();

    }

    private void getSelectedActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetActivityRequest request = new GetActivityRequest();
                request.setActivityId(activityId);
                request.setUserId(userId);

                GetActivityResponse response = HttpClient.httpPost(GET_ACTIVITY_URL, request, GetActivityRequest.class, GetActivityResponse.class);

                Message msg = new Message();
                msg.obj = response;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        public  void handleMessage(Message msg){

            GetActivityResponse response = (GetActivityResponse)msg.obj;
            mTvActName.setText("Name: " + response.getActivityName());
            mTvActDes.setText("Description: "+response.getActivityDescription());
            mTvCreator.setText("Starter: " + response.getCreateUser().getUserName());

            if (response.getTime() != null) {
                Date date = response.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy, MM, dd, HH:mm:ss");
                mTvActTime.setText("Time: " + simpleDateFormat.format(date));
            }else{
                mTvActTime.setText("Time: Uncertain");
            }
            mBtnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JoinActivityRequest request = new JoinActivityRequest();
                            request.setUserId(userId);
                            request.setActivityId(activityId);

                            JoinActivityResponse response = HttpClient.httpPost(JOIN_ACTIVITY_URL, request, JoinActivityRequest.class, JoinActivityResponse.class);

                            Intent intent = new Intent(JoinSelectedActivity.this,ShowJoinActivity.class);
                            intent.putExtra("userId", userId);
                            JoinSelectedActivity.this.startActivity(intent);
                        }
                    }).start();
                }


            });


        }





    };


}
