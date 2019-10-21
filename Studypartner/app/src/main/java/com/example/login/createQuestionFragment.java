package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.example.service.HttpClient;
import com.example.service.entity.CreatePostRequest;
import com.example.service.entity.UpdateTagRequest;
import com.example.service.entity.UpdateTagResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.service.InterfaceURL.UPDATE_TAG_URL;
import static org.greenrobot.eventbus.EventBus.*;

public class createQuestionFragment extends Fragment {
    Spinner choosetag;
    Button painting;
    public static final int MY_REQUEST_CODE = 2;
    public static final int MY_RESULT_CODE = 1;
    ImageView picture;
    int userId;
    EditText mtitle;
    EditText mdecirbtion;
    String finaltag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            //取出保存的值
            userId = getArguments().getInt("userId");
        }

        View rootView = inflater.inflate(R.layout.fragment_create_qustion, container, false);
        choosetag = rootView.findViewById(R.id.spinner1);
        painting = rootView.findViewById(R.id.painting);
        picture = rootView.findViewById(R.id.picuture);
        mtitle = rootView.findViewById(R.id.title_QA);
        mdecirbtion = rootView.findViewById(R.id.edit_QA);


        EventBus.getDefault().register(this);

        /**
         * 事件响应方法
         * 接收消息
         * @param event
         */


        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        choosetag.setAdapter(adapter);
        choosetag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    finaltag = ((TextView) view).getText().toString();
                    System.out.println(finaltag+"?????????????");
                    Toast.makeText(getActivity().getBaseContext(), finaltag, Toast.LENGTH_SHORT).show();
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
                getActivity().startActivityForResult(intent, MY_REQUEST_CODE);

            }


        });


        rootView.findViewById(R.id.create_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //从栈中将当前fragment推出
                final String title=mtitle.getText().toString().trim();
                final String pwd=mdecirbtion.getText().toString().trim();
                final int tagId = tagint(finaltag);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CreatePostRequest request=  new CreatePostRequest();

                        request.setUserTags(tagList);
                        request.setUserId(userId);

                        UpdateTagResponse response = HttpClient.httpPost(UPDATE_TAG_URL, request, UpdateTagRequest.class, UpdateTagResponse.class);



                        getFragmentManager().popBackStack();


                    }
                }).start();

            }
        });
        return rootView;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {

        String str = event.getMessgae();
        Bitmap pic = base64ToBitmap(str);
        picture.setImageBitmap(pic);
    }


    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static createQuestionFragment newInstance(int userId) {
        createQuestionFragment fragmentOne = new createQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        //fragment保存参数，传入一个Bundle对象
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }
    public static int tagint(String finaltag) {
        if (finaltag.equals("Chemistry")) {
            return 1;
        }
        else if (finaltag.equals("IT")) {
            return 2;
        }
        else if (finaltag.equals("Business")) {
            return 3;
        }
        else if (finaltag.equals("Math")) {
            return 4;
        }else if (finaltag.equals("Physics")) {
            return 5;
        }else if (finaltag.equals("Biology")) {
            return 6;
        }else if (finaltag.equals("Mechanics")) {
            return 7;
        }
        else return 8;

    }

}

