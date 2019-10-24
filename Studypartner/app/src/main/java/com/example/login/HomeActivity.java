package com.example.login;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.service.HttpClient;
import com.example.service.entity.ActivityPart;
import com.example.service.entity.MainPageRequest;
import com.example.service.entity.MainPageResponse;
import com.example.service.entity.PostPart;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.service.InterfaceURL.MAIN_PAGE_URL;

public class HomeActivity extends FragmentActivity {
    private String host = "tcp://13.250.45.8:61613";
    private String userName = "admin";
    private String passWord = "password";
    int userId = 0;
    private Date lastNotifyDate;
    GoogleSignInClient mGoogleSignInClient;


    private List<String> topicList = new ArrayList<>();

    private MqttClient client;
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler;
    public FragmentTransaction mFragmentTransaction;
    public FragmentManager fragmentManager;
    public String curFragmentTag = "";
    ArrayList<Integer> ids;
    ArrayList<Integer> aIds;
    ListView postList;
    ListView activityList;
    String currentFrag;
    //private TextView mTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        postList = findViewById(R.id.postList);
        activityList = findViewById(R.id.activityList);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if(userId != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MainPageRequest request = new MainPageRequest();
                    request.setUserId(userId);
                    MainPageResponse response = HttpClient.httpPost(MAIN_PAGE_URL, request, MainPageRequest.class, MainPageResponse.class);
                    if(response != null && response.getResponseStatus() == 0){
                        if(response.getUserInterestedTag() != null && response.getUserInterestedTag().size() > 0) {
                            topicList = new ArrayList<>();
                            for(Integer tag : response.getUserInterestedTag()){
                                String topic = "topicID" + tag;
                                topicList.add(topic);
                            }
                            init();
                            connect();
                        }

                        Message msg = new Message();
                        msg.what = 4;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                }
            }).start();
        }
//        Fragment  selectedFragment = new HomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("userId",userId);
//        selectedFragment.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment,currentFrag).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            Bundle bundle = new Bundle();
            bundle.putInt("userId",userId);
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    selectedFragment = new HomeFragment();
//                    selectedFragment.setArguments(bundle);
//                    onCreate(null);
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    return true;
                //mTextMessage.setText(R.string.home);

                case R.id.navigation_activity:
                    selectedFragment = new ActivityFragment();
                    selectedFragment.setArguments(bundle);
                    break;

                //mTextMessage.setText(R.string.activity);

                case R.id.navigation_question:
                    selectedFragment = new QAFragment();
                    selectedFragment.setArguments(bundle);
                    break;
                //mTextMessage.setText(R.string.q_a);

                case R.id.navigation_me:
                    selectedFragment = new MeFragment();
                    selectedFragment.setArguments(bundle);
                    break;
                //mTextMessage.setText(R.string.account);

            }
            currentFrag = selectedFragment.toString();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment,currentFrag).commit();
            return true;
        }
    };


    /**@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }*/

    public void refresh() {
        onCreate(null);
    }

    class ListViewAdapter extends BaseAdapter {

        private ArrayList<String> mData;

        public ListViewAdapter(ArrayList<String> mData) {
            this.mData = mData;
        }

        public int getCount() {
            return mData == null ? 0 :mData.size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View view, ViewGroup parent) {
            TextView mTextView;
            if (view == null) {
                mTextView = new TextView(HomeActivity.this);
            } else {
                mTextView = (TextView) view;
            }
            mTextView.setText(mData.get(position));
            mTextView.setTextSize(20f);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setHeight(80);
            return mTextView;
        }
    }

    private void connect() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    client.connect(options);
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if (!client.isConnected()) {
                    connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }


    private void init() {
        try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, "test",
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("connectionLost----------");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }

                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    System.out.println("messageArrived----------");
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = message.toString();
                    handler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 1) {
//                Toast.makeText(MainActivity.this, (String) msg.obj,
//                        Toast.LENGTH_SHORT).show();
                Log.d("mqtt connect", (String) msg.obj);
                String json = (String) msg.obj;
                if(lastNotifyDate == null || lastNotifyDate.getTime() - new Date().getTime() > 1000 * 60 * 60 * 3){
//                    NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    String id ="channel_1";//channel的id
                    String description = "123";//channel的描述信息
                    int importance = NotificationManager.IMPORTANCE_LOW;//channel的重要性
                    JSONObject jsonObject = JSON.parseObject(json);
                    Intent intent = null;
                    if(jsonObject.getString("type") == "ACTIVITY"){
                        intent = new Intent(HomeActivity.this,JoinSelectedActivity.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("activityId", jsonObject.getInteger("id"));

                    } else {
                        intent = new Intent(HomeActivity.this, PostDetail.class);
                        intent.putExtra("postId", jsonObject.getInteger("id"));
                        intent.putExtra("userId", userId);
                    }
                    PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationChannel channel = new NotificationChannel(id, description, importance);//生成channel
                    mNotificationManager.createNotificationChannel(channel);
                    Notification notification = new Notification.Builder(HomeActivity.this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentIntent(pendingIntent)
                            .setContentTitle("Notification!")
                            .setContentText(jsonObject.getString("content"))
                            .setChannelId(id).build();
                    mNotificationManager.notify(1, notification);
                }

            } else if (msg.what == 2) {
                System.out.println("连接成功");
                try {
                    if(topicList != null && topicList.size() > 0){
                        for(String topic : topicList){
                            client.subscribe(topic, 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 3) {
//                System.out.println("连接失败，系统正在重连");
            } else if (msg.what == 4) {
                MainPageResponse response = (MainPageResponse) msg.obj;
                if(response.getPostList() != null && response.getPostList().size() > 0) {

                    ids = new ArrayList<>();
                    ArrayList<String> postStrs = new ArrayList<>();
                    for(PostPart postPart : response.getPostList()){
                        postStrs.add(postPart.getPostName());
                        ids.add(postPart.getPostId());
                    }
                    ListViewAdapter homePageAdapter = new ListViewAdapter(postStrs);
                    postList.setAdapter(homePageAdapter);

                    postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent intent = new Intent(HomeActivity.this, PostDetail.class);
                            intent.putExtra("postId", ids.get(position));
                            intent.putExtra("userId", userId);

                            startActivity(intent);

                        }
                    });
                }
                if(response.getActivityList() != null && response.getActivityList().size() > 0){

                    aIds = new ArrayList<>();
                    ArrayList<String> aStrs = new ArrayList<>();
                    for(ActivityPart activityPart : response.getActivityList()){
                        aStrs.add(activityPart.getActivityName());
                        aIds.add(activityPart.getActivityId());
                    }
                    ListViewAdapter homePageAdapter = new ListViewAdapter(aStrs);
                    activityList.setAdapter(homePageAdapter);

                    activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent intent = new Intent(HomeActivity.this,JoinSelectedActivity.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("activityId", aIds.get(position));

                            startActivity(intent);

                        }
                    });
                }
            }

            super.handleMessage(msg);
        }
    };

}
