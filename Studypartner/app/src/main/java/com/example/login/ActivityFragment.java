package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ActivityFragment extends Fragment {
    Button mapButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);
        mapButton = rootView.findViewById(R.id.map);
        Bundle bundle = getArguments();
        final int userId = bundle.getInt("userId");

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
