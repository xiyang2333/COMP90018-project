package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class createQuestionFragment extends Fragment {
    Spinner choosetag;
    Button painting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_qustion, container, false);
        choosetag = rootView.findViewById(R.id.spinner1);
        painting = rootView.findViewById(R.id.painting);


        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        choosetag.setAdapter(adapter);
        choosetag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    Toast.makeText(getActivity().getBaseContext(), ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "viewa: " + view + ", parent: " + parent + ", position: " + position + ", id: " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "on nothing selected");
            }
        });

        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), painting.class);
                getActivity().startActivityForResult(intent, 1);

            }


        });


        rootView.findViewById(R.id.create_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //从栈中将当前fragment推出
                getFragmentManager().popBackStack();
            }
        });
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                break;

            default:
                break;
        }

    }
}

