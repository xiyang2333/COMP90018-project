package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.service.HttpClient;
import com.example.service.entity.RegisterRequest;
import com.example.service.entity.RegisterResponse;
import com.example.service.entity.Tag;
import com.example.service.entity.UpdateTagRequest;
import com.example.service.entity.UpdateTagResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.service.InterfaceURL.REGISTER_URL;
import static com.example.service.InterfaceURL.UPDATE_TAG_URL;

public class clicktag extends AppCompatActivity {

    Button mEnter;
    CheckBox mChemistry;
    CheckBox mIT;
    CheckBox mBusiness;
    CheckBox mMath;
    CheckBox mPhysics;
    CheckBox mBiology;
    CheckBox mMechanics;
    CheckBox mArt;
    List<Integer> tagList = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicktag);

        mEnter = (Button)findViewById(R.id.tag_enter);
        mChemistry = findViewById(R.id.checkbox_1);
        mIT = findViewById(R.id.checkbox_2);
        mBusiness = findViewById(R.id.checkbox_3);
        mMath = findViewById(R.id.checkbox_4);
        mPhysics = findViewById(R.id.checkbox_5);
        mBiology = findViewById(R.id.checkbox_6);
        mMechanics = findViewById(R.id.checkbox_7);
        mArt = findViewById(R.id.checkbox_8);


        Intent intent = getIntent();

        final int userId = intent.getIntExtra("user",0);


        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mChemistry.isChecked()){
                    tagList.add(1);
                }
                if(mIT.isChecked()){
                    tagList.add(2);
                }
                if(mBusiness.isChecked()){
                    tagList.add(3);
                }
                if(mMath.isChecked()){
                    tagList.add(4);
                }
                if(mPhysics.isChecked()){
                    tagList.add(5);
                }
                if(mBiology.isChecked()){
                    tagList.add(6);
                }
                if(mMechanics.isChecked()){
                    tagList.add(7);
                }
                if(mArt.isChecked()){
                    tagList.add(8);
                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UpdateTagRequest request=  new UpdateTagRequest();

                        request.setUserTags(tagList);
                        request.setUserId(userId);

                        UpdateTagResponse response = HttpClient.httpPost(UPDATE_TAG_URL, request, UpdateTagRequest.class, UpdateTagResponse.class);




                    }
                }).start();
            Intent movetoHome = new Intent (clicktag.this, HomeActivity.class);
            movetoHome.putExtra("user",userId);
            startActivity(movetoHome);
            }

    });





    }
}
