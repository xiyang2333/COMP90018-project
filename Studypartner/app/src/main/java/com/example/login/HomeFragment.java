package com.example.login;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.service.HttpClient;
import com.example.service.entity.MainPageRequest;
import com.example.service.entity.MainPageResponse;

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

import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.service.InterfaceURL.MAIN_PAGE_URL;

public class HomeFragment extends Fragment {
    private String host = "tcp://13.250.45.8:61613";
    private String userName = "admin";
    private String passWord = "password";
    int userId = 0;
    private Date lastNotifyDate;

    private List<String> topicList = new ArrayList<>();

    private MqttClient client;
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler;

    private ListView postList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                    NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                    String id ="channel_1";//channel的id
                    String description = "123";//channel的描述信息
                    int importance = NotificationManager.IMPORTANCE_LOW;//channel的重要性
                    JSONObject jsonObject = JSON.parseObject(json);
                    Intent intent = null;
                    if(jsonObject.getString("type") == "ACTIVITY"){
                        // TODO

                    } else {
                        intent = new Intent(getContext(), PostDetail.class);
                        intent.putExtra("postId", jsonObject.getInteger("id"));
                        intent.putExtra("userId", userId);
                    }
                    PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationChannel channel = new NotificationChannel(id, description, importance);//生成channel
                    mNotificationManager.createNotificationChannel(channel);
                    Notification notification = new Notification.Builder(getActivity())
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

                ListViewAdapter homePageAdapter = new ListViewAdapter();
                postList.setAdapter(homePageAdapter);
                homePageAdapter.notifyDataSetChanged();
            }

            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        postList = home.findViewById(R.id.postList);

        Bundle bundle = getArguments();
        userId = bundle.getInt("userId");
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



        return inflater.inflate(R.layout.fragment_home,container,false);
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

    class ListViewAdapter extends BaseAdapter {
        int count = 2;
        public int getCount() {
            return count;
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
                mTextView = new TextView(getActivity());
            } else {
                mTextView = (TextView) view;
            }
            mTextView.setText("Item " + position);
            mTextView.setTextSize(20f);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setHeight(60);
            return mTextView;
        }
    }
}

