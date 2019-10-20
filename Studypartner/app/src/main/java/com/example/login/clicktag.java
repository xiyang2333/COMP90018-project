package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

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

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Loginscreen = new Intent (clicktag.this, HomeActivity.class);
                startActivity(Loginscreen);
            }

    });





    }
}
