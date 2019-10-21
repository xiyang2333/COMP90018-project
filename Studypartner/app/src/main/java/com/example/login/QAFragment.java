package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QAFragment extends Fragment {

    public QAFragment(){

    }


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_qa, container, false);

        rootView.findViewById(R.id.Q1).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,new createQuestionFragment()).commit();
                }
            }
        });

        return rootView;
    }
}
