package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.service.HttpClient;
import com.example.service.entity.AnswerPart;
import com.example.service.entity.GetPostRequest;
import com.example.service.entity.GetPostResponse;
import com.example.service.entity.PostPart;
import com.example.service.entity.SearchPostRequest;
import com.example.service.entity.SearchPostResponse;

import java.util.ArrayList;
import java.util.List;

import static com.example.login.createQuestionFragment.base64ToBitmap;
import static com.example.service.InterfaceURL.GET_POST_URL;
import static com.example.service.InterfaceURL.SEARCH_POST_URL;

public class PostDetail extends AppCompatActivity {
    int postId = 0;
    int userId = 0;
    String postName;
    String postDescription;
    ArrayList<String> photoList;
    List<AnswerPart> answerPart;
    ArrayList<Integer> answerUserId;
    ArrayList<String> answer;
    Button wrtiteAnswer;
    ImageView pic;
    TextView postname;
    TextView Posedes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 0);
        userId = intent.getIntExtra("userId", 0);
        postname = findViewById(R.id.postName);

        getDate();
        wrtiteAnswer = findViewById(R.id.writeAnswer);
        Posedes = findViewById(R.id.postDes);
        pic = findViewById(R.id.pic);



    }

    public void getDate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetPostRequest request = new GetPostRequest();
                System.out.println("postId is " + postId);
                System.out.println("用户id 是" + userId);
                request.setUserId(userId);
                request.setPostId(postId);

                GetPostResponse response = HttpClient.httpPost(GET_POST_URL, request, GetPostRequest.class, GetPostResponse.class);

                postName = response.getPostName();
                postDescription = response.getPostDescription();
                photoList = (ArrayList) response.getPhotoList();
                answerPart = response.getAnswerList();
                if(answerPart != null){
                for (int i = 0; i < answerPart.size(); i++) {
                    answerUserId.add(answerPart.get(i).getUserId());
                    answer.add(answerPart.get(i).getAnswer());
                }}


                Message msg = new Message();
                msg.getData().putString("postName",postName);
                msg.getData().putString("postDescription",postDescription);
                msg.getData().putStringArrayList("photoList", photoList);
                msg.getData().putIntegerArrayList("answerUserId", answerUserId);
                msg.getData().putStringArrayList("answer", answer);


                handler.sendMessage(msg);

            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            postName = bundle.getString("postName");
            postDescription = bundle.getString("postDescription");
            photoList = bundle.getStringArrayList("photoList");
            String str = photoList.get(0);
            pic.setImageBitmap(base64ToBitmap(str));
            Posedes.setText(postDescription);
            postname.setText(postName);



        }
    };
}
