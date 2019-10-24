package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.service.HttpClient;
import com.example.service.entity.CreateActivityRequest;
import com.example.service.entity.CreateActivityResponse;

import java.math.BigDecimal;
import java.util.Date;

import static com.example.service.InterfaceURL.CREATE_ACTIVITY_URL;

public class ActivityFragment extends Fragment {
    private static final String TAG = "ActivityFragment";
    Button mapButton;
    private EditText mEtActTitle;
    private EditText mEtActDes;
    private Button mBtnCreate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);

        mapButton = rootView.findViewById(R.id.map);
        Bundle bundle = getArguments();
        final int userId = bundle.getInt("userId");

        // bind
        mBtnCreate = rootView.findViewById(R.id.button_create);
        mEtActTitle = rootView.findViewById(R.id.edittext_activityTitle);
        mEtActDes = rootView.findViewById(R.id.edittext_activityDes);

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get text from edit text
                final String title = mEtActTitle.getText().toString().trim();
                final String des = mEtActDes.getText().toString().trim();
                if (title.length()>0 && des.length()>0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CreateActivityRequest request = new CreateActivityRequest();
                            request.setUserId(userId);
                            request.setActivityName(title);
                            request.setActivityDescription(des);
                            Date date = new Date(System.currentTimeMillis());
                            request.setTime(date);

                            // set coordinate for activity location
                            request.setLatitude(new BigDecimal(-37.7963));
                            request.setLongitude(new BigDecimal(144.9614));

                            // send to backend
                            CreateActivityResponse response = HttpClient.httpPost(CREATE_ACTIVITY_URL, request, CreateActivityRequest.class, CreateActivityResponse.class);

                            Log.d(TAG, "onClick: tell me activity is created" + response);
                            Intent intent = new Intent(getActivity(), ShowCreateActivity.class);
                            intent.putExtra("userId", userId);
                            getActivity().startActivityForResult(intent, 1);
                        }
                    }).start();
                    Toast.makeText(getContext(),"activity created", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "fail to create activity, try again", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: activity is not created");
                }
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("userId", userId);
                getActivity().startActivityForResult(intent, 1);

            }


        });


        return rootView;
    }
}
