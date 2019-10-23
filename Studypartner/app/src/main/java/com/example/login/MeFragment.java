
package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.service.HttpClient;
import com.example.service.entity.UserPart;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.service.entity.UserRequest;

import java.util.ArrayList;

import static com.example.service.InterfaceURL.USER_INFO_URL;


public class MeFragment extends Fragment {
    GoogleSignInClient mGoogleSignInClient;

    TextView name, id;
    int userId, a;
    String name_str;
    Button sign_out_button;

    public MeFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        //System.out.println("sssssssssssssssssssss");
        userId = bundle.getInt("userId");
        //System.out.println("sssssssssssssssssssss" + userId);

        View view = inflater.inflate(R.layout.fragment_me, container, false);


        name = (TextView) view.findViewById(R.id.textName);
        sign_out_button = view.findViewById(R.id.sign_out_button);


        //name = getActivity().findViewById(R.id.textName);

        new Thread(new Runnable() {

            @Override
            public void run() {

                UserRequest request = new UserRequest();

                request.setUserId(userId);

                UserPart response = HttpClient.httpPost(USER_INFO_URL, request, UserRequest.class, UserPart.class);

                a = response.getUserId();
                name_str = response.getUserName();


                Message msg = new Message();

                msg.getData().putString("Name",name_str);
                msg.getData().putInt("userId",a);

                handler.sendMessage(msg);
                //System.out.println("bbbbbbbbb" + msg);



            }
        }).start();



        return view;

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            userId = bundle.getInt("userId");
            name_str = bundle.getString("Name");
            //System.out.println("ccccccccccccccccccc"+ userId+name_str);
            name.setText(name_str);
            super.handleMessage(msg);
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        Button sign_out_button = (Button) getActivity().findViewById(R.id.sign_out_button);
        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.sign_out_button:
                        signOut();
                        break;
                    // ...
                }
            }
        });


        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent (getActivity(), MainActivity.class);

                getActivity().startActivity(logout);
            }
        });



        // name = getActivity().findViewById(R.id.textName);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            //  name.setText(personName);
           // email.setText(personEmail);


        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "You have signed out", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

