package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

public class writeAnswer extends AppCompatActivity {
    Button saveAns;
    EditText wrtiteAns;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_answer);
        wrtiteAns = findViewById(R.id.writeAnswer);


        saveAns = findViewById(R.id.saveAns);
        saveAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userAns = wrtiteAns.getText().toString();
                //System.out.println(userAns + "用户在写答案界面");


                Intent i = new Intent();
                i.putExtra("data", userAns);
                setResult(1, i);
                finish();

            }
        });


    }
}
