package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.service.HttpClient;
import com.example.service.entity.Tag;
import com.example.service.entity.TagListResponse;

import java.util.ArrayList;
import java.util.List;

import static com.example.service.InterfaceURL.ALL_TAG_URL;

public class clicktag extends AppCompatActivity {
    Button mEnter;
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

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Loginscreen = new Intent (clicktag.this, HomeActivity.class);
                startActivity(Loginscreen);
            }

    });





    }
}
