package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QAFragment extends Fragment {
    public static final int MY_REQUEST_CODE = 2;
    public static final int MY_RESULT_CODE = 1;
    public QAFragment(){

    }


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final int userId = bundle.getInt("userId");

        View rootView = inflater.inflate(R.layout.fragment_qa, container, false);

        rootView.findViewById(R.id.Q1).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                if (getFragmentManager() != null) {
                    createQuestionFragment nextFra = new createQuestionFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,nextFra.newInstance(userId)).commit();
                }
            }
        });

        return rootView;
    }

}
