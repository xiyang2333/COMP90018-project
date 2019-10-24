package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowCreateActivity extends AppCompatActivity {

    Button mBtnBack;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_create);
        mBtnBack = findViewById(R.id.btn_backHome);
        userId = getIntent().getIntExtra("userId",0);


        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowCreateActivity.this, MapsActivity.class);
                intent.putExtra("userId", userId);
                ShowCreateActivity.this.startActivity(intent);
            }
        });
    }
}
