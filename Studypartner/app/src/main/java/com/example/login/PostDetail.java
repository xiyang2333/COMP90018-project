package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.service.HttpClient;
import com.example.service.entity.AnswerPart;
import com.example.service.entity.AnswerPostRequest;
import com.example.service.entity.AnswerPostResponse;
import com.example.service.entity.GetPostRequest;
import com.example.service.entity.GetPostResponse;
import com.example.service.entity.PostPart;
import com.example.service.entity.SearchPostRequest;
import com.example.service.entity.SearchPostResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.login.createQuestionFragment.base64ToBitmap;
import static com.example.service.InterfaceURL.ANSWER_POST_URL;
import static com.example.service.InterfaceURL.GET_POST_URL;
import static com.example.service.InterfaceURL.SEARCH_POST_URL;

public class PostDetail extends AppCompatActivity {
    int postId = 0;
    int userId = 0;
    TextView temp;
int count = 0;
    String postName;
    String postDescription;
    ArrayList<String> photoList = new ArrayList<>();
    List<AnswerPart> answerPart = new ArrayList<>();


    Button wrtiteAnswer;
    ImageView pic;
    TextView postname;
    TextView Posedes;
    MyListView answerList;
    ArrayList<String> temp_answer = new ArrayList<>();
    ArrayList<String> temp_answerUserName = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 0);
        userId = intent.getIntExtra("userId", 0);

        postname = findViewById(R.id.postName);
        answerList = findViewById(R.id.answerList);
        temp = findViewById(R.id.
                ansTtile);

        getDate();
        wrtiteAnswer = findViewById(R.id.writeAnswer);
        Posedes = findViewById(R.id.postDes);
        pic = findViewById(R.id.pic);

        wrtiteAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetail.this, writeAnswer.class);
                startActivityForResult(intent, 0);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String answerStr = data.getStringExtra("data");
        upDateData(answerStr);
        getDate();
    }


    public void upDateData(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AnswerPostRequest request = new AnswerPostRequest();


                request.setUserId(userId);
                request.setPostId(postId);
                request.setAnswer(s);
                //System.out.println(s + "????????????????");
                AnswerPostResponse response = HttpClient.httpPost(ANSWER_POST_URL, request, AnswerPostRequest.class, AnswerPostResponse.class);
                //System.out.println(" 用户答案上传状态" + response.getResponseStatus());

            }
        }).start();
    }

    public void getDate() {
count = count +1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetPostRequest request = new GetPostRequest();
                //System.out.println("postId is " + postId);
                //System.out.println("用户id 是" + userId);
                request.setUserId(userId);
                request.setPostId(postId);

                GetPostResponse response = HttpClient.httpPost(GET_POST_URL, request, GetPostRequest.class, GetPostResponse.class);

                postName = response.getPostName();
                postDescription = response.getPostDescription();
                photoList = (ArrayList) response.getPhotoList();
                answerPart = response.getAnswerList();


                if (answerPart != null) {
                    temp_answerUserName = new ArrayList<>();
                    temp_answer = new ArrayList<>();
                    for (int i = 0; i < answerPart.size(); i++) {
                        temp_answerUserName.add(answerPart.get(i).getUser().getUserName());
                        temp_answer.add(answerPart.get(i).getAnswer());
                    }
                }


                Message msg = new Message();
                msg.getData().putString("postName", postName);
                msg.getData().putString("postDescription", postDescription);
                msg.getData().putStringArrayList("photoList", photoList);
                msg.getData().putStringArrayList("temp_answerUserName", temp_answerUserName);
                msg.getData().putStringArrayList("answer", temp_answer);


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
            if (photoList != null) {
                String str = photoList.get(0);
                if(str != null && str.length() > 0) {
                    pic.setImageBitmap(base64ToBitmap(str));
                }
            }
            Posedes.setText(postDescription);
            postname.setText(postName);
            temp.setText("Answer From Others");

            ArrayList<String> answer = bundle.getStringArrayList("answer");

            ArrayList<String> answerUserName = bundle.getStringArrayList("temp_answerUserName");
            //System.out.println(answerUserId.size() + "传给adapter的长度是");

            AnswerFlashAdapter adapter = new AnswerFlashAdapter(answer, answerUserName);

            answerList.setAdapter(adapter);


        }
    };
}


