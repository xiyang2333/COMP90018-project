package com.example.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.service.HttpClient;
import com.example.service.entity.CreatePostRequest;
import com.example.service.entity.CreatePostResponse;
import com.example.service.entity.PostPart;
import com.example.service.entity.SearchPostRequest;
import com.example.service.entity.SearchPostResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.service.InterfaceURL.CREATE_POST_URL;
import static com.example.service.InterfaceURL.SEARCH_POST_URL;

public class answerQuestionFragment extends Fragment {

    int userId;
    int tagId;
    TextView emptyTV;
    Context mContext;
    String userInput;
    ArrayList<String> mData = null;
    ArrayList<Integer> mPostId;
    ListView postList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            //取出保存的值
            userId = getArguments().getInt("userId");
            tagId = getArguments().getInt("tagId");
            userInput = getArguments().getString("userInput");
        }

        View rootView = inflater.inflate(R.layout.activity_answerqa, container, false);
        postList = rootView.findViewById(R.id.mList);
        mContext = getContext();
        emptyTV = rootView.findViewById(R.id.emptyTV);


        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchPostRequest searchQA = new SearchPostRequest();
                //System.out.println(tagId + "tagId is");
                //System.out.println(userInput + "用户输入是什么");
                searchQA.setTagId(tagId);
                searchQA.setTitle(userInput);


                SearchPostResponse response = HttpClient.httpPost(SEARCH_POST_URL, searchQA, SearchPostRequest.class, SearchPostResponse.class);

                List<PostPart> resultList = response.getPostList();

                Message msg = new Message();


                ArrayList<String> postList = new ArrayList<>();
                ArrayList<Integer> postIdList = new ArrayList<>();


                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        postList.add(resultList.get(i).getPostName());
                        postIdList.add(resultList.get(i).getPostId());


                    }
                    msg.getData().putStringArrayList("postList", postList);
                    msg.getData().putIntegerArrayList("postIdList",postIdList);

                } else {
                    postList.add("There is not related question");
                    msg.getData().putStringArrayList("postList", postList);
                }


                handler.sendMessage(msg);
            }
        }).start();


        return rootView;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            mData = bundle.getStringArrayList("postList"); //mData 是一个存放 postName 的列表
            mPostId = bundle.getIntegerArrayList("postIdList");

            if (!mData.get(0).equals("There is not related question")) {
                PostAdapter adapter = new PostAdapter(mData, userId,mPostId);

                postList.setAdapter(adapter);
                super.handleMessage(msg);
            } else {

                emptyTV.setText("There is not related question");
            }
        }
    };

    public static answerQuestionFragment newInstance(int userId, int tagId, String userInput) {
        answerQuestionFragment fragmentOne = new answerQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        bundle.putInt("tagId", tagId);
        bundle.putString("userInput", userInput);
        //fragment保存参数，传入一个Bundle对象
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }


}

