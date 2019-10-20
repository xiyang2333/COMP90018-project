package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.os.Handler;
import android.os.Message;
>>>>>>> 89351f93207210ac3988570eb279654c182fb737
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
<<<<<<< HEAD
import android.widget.Toast;
=======
import android.widget.LinearLayout;

import com.example.service.HttpClient;
import com.example.service.entity.Tag;
import com.example.service.entity.TagListResponse;
>>>>>>> 89351f93207210ac3988570eb279654c182fb737

import com.example.service.HttpClient;
import com.example.service.entity.RegisterRequest;
import com.example.service.entity.RegisterResponse;
import com.example.service.entity.Tag;
import com.example.service.entity.UpdateTagRequest;
import com.example.service.entity.UpdateTagResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Arrays;
import java.util.List;

import static com.example.service.InterfaceURL.REGISTER_URL;
import static com.example.service.InterfaceURL.UPDATE_TAG_URL;
=======
import java.util.List;

import static com.example.service.InterfaceURL.ALL_TAG_URL;
>>>>>>> 89351f93207210ac3988570eb279654c182fb737

public class clicktag extends AppCompatActivity {

    Button mEnter;
<<<<<<< HEAD
    CheckBox mChemistry;
    CheckBox mIT;
    CheckBox mBusiness;
    CheckBox mMath;
    CheckBox mPhysics;
    CheckBox mBiology;
    CheckBox mMechanics;
    CheckBox mArt;
    List<Integer> tagList = new ArrayList<Integer>();
=======
    List<Tag> tagList = null;
    private List<CheckBox> checkBoxs = new ArrayList<CheckBox>();
//    CheckBox mChemistry;
//    CheckBox mIT;
//    CheckBox mBusiness;
//    CheckBox mMath;
//    CheckBox mPhysics;
//    CheckBox mBiology;
//    CheckBox mMechanics;
//    CheckBox mArt;
>>>>>>> 89351f93207210ac3988570eb279654c182fb737

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(tagList != null) {
                LinearLayout linearLayout = findViewById(R.id.lLayout);
                for (Tag tag : tagList) {
                    CheckBox checkBox = (CheckBox) getLayoutInflater().inflate(
                            R.layout.checkbox, null);
                    checkBox.setText(tagList.indexOf(tag) + ". " + tag.getTagName());
                    checkBoxs.add(checkBox);
                    try {
                        linearLayout.addView(checkBox);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicktag);

        mEnter = (Button)findViewById(R.id.tag_enter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                TagListResponse tagListResponse = HttpClient.httpGet(ALL_TAG_URL, TagListResponse.class);
                if(tagListResponse.getResponseStatus() == 0 && tagListResponse.getTagList() != null){
                    tagList = tagListResponse.getTagList();
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                Log.d("register", "run: " + tagListResponse);
            }
        }).start();
//        mChemistry = findViewById(R.id.checkbox_1);
//        mIT = findViewById(R.id.checkbox_2);
//        mBusiness = findViewById(R.id.checkbox_3);
//        mMath = findViewById(R.id.checkbox_4);
//        mPhysics = findViewById(R.id.checkbox_5);
//        mBiology = findViewById(R.id.checkbox_6);
//        mMechanics = findViewById(R.id.checkbox_7);
//        mArt = findViewById(R.id.checkbox_8);


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
