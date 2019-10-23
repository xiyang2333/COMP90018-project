package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.HttpClient;
import com.example.service.entity.LoginCheckRequest;
import com.example.service.entity.LoginCheckResponse;
import com.example.service.entity.UpdateTagRequest;
import com.example.service.entity.UpdateTagResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.service.InterfaceURL.LOGIN_URL;
import static com.example.service.InterfaceURL.UPDATE_TAG_URL;

public class MainActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;
    GoogleSignInClient mGoogleSignInClient;


    int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        mTextUsername = (EditText) findViewById(R.id.edittext_username);

        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = mTextUsername.getText().toString().trim();
                final String pwd = mTextPassword.getText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        LoginCheckRequest loginRequest = new LoginCheckRequest();
                        loginRequest.setUserLoginName(user);
                        loginRequest.setUserPassword(pwd);

                        LoginCheckResponse loginRes = HttpClient.httpPost(LOGIN_URL, loginRequest, LoginCheckRequest.class, LoginCheckResponse.class);

                        if (loginRes.getResponseStatus() == 1) {
                            Intent Loginscreen = new Intent(MainActivity.this, HomeActivity.class);
                            System.out.println(loginRes.getUserId()+"???????");
                            Loginscreen.putExtra("userId", loginRes.getUserId());
                            startActivity(Loginscreen);

                        } else {
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                            Looper.loop();

                        }

                    }
                }).start();

            }

        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //http get test
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                TagListResponse response = HttpClient.httpGet("http://10.0.2.2:8080/studypartner/alltag", TagListResponse.class);
//            }
//        }).start();

        //http post test
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LoginCheckRequest request = new LoginCheckRequest();
//                request.setUserLoginName("xiyang2333");
//                request.setUserPassword("E10ADC3949BA59ABBE56E057F20F883E");
//                LoginCheckResponse response = HttpClient.httpPost("http://10.0.2.2:8080/studypartner/logincheck", request, LoginCheckRequest.class, LoginCheckResponse.class);
//            }
//        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Intent Loginscreen = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(Loginscreen);
        }


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Toast.makeText(MainActivity.this, "You have logged in", Toast.LENGTH_SHORT).show();
            Intent moveToHome = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(moveToHome);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }


}
