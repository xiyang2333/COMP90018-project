package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.service.HttpClient;
import com.example.service.entity.RegisterRequest;
import com.example.service.entity.RegisterResponse;

import static com.example.service.InterfaceURL.REGISTER_URL;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db=new DatabaseHelper(this);
        mTextUsername=(EditText)findViewById(R.id.edittext_username);
        mTextPassword=(EditText)findViewById(R.id.edittext_password);
        mTextCnfPassword=(EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister=(Button)findViewById(R.id.button_register);
        mTextViewLogin=(TextView) findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Loginintent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(Loginintent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user=mTextUsername.getText().toString().trim();
                final String pwd=mTextPassword.getText().toString().trim();
                String cnf_pwd=mTextCnfPassword.getText().toString().trim();
                if (pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd);
                    if(val>0){



                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RegisterRequest request=  new RegisterRequest();
                                request.setUserLoginName(user);
                                request.setUserPassword(pwd);
                                RegisterResponse response = HttpClient.httpPost(REGISTER_URL, request, RegisterRequest.class, RegisterResponse.class);
                                Log.d("register", "run: " + response.getUserId());
                            }
                        }).start();


                        Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToTag = new Intent(RegisterActivity.this,ShowTag.class);
                        startActivity(moveToTag);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();

                    }


                }
                else{
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
