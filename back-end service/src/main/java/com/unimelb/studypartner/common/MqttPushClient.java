package com.unimelb.studypartner.common;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

/**
 * Created by xiyang on 2019/9/20
 */
@Service
public class MqttPushClient {

    private static Logger logger = Logger.getLogger(MqttPushClient.class);
    private static final String MQTT_HOST = "tcp://13.250.45.8:61613";
    private static final String MQTT_CLIENTID = "StudyPartnerPublisher";
    private static final String MQTT_USERNAME = "admin";
    private static final String MQTT_PASSWORD = "password";
    private static int MQTT_TIMEOUT = 10;
    private static int MQTT_KEEPALIVE = 10;

    private MqttClient client;
    private static volatile MqttPushClient mqttClient = null;
    public static MqttPushClient getInstance() {
        if(mqttClient == null) {
            synchronized (MqttPushClient.class) {
                if(mqttClient == null) {
                    mqttClient = new MqttPushClient();
                }
            }
        }
        return mqttClient;
    }

    private MqttPushClient() {
        logger.info("Connect MQTT: " + this);
        connect();
    }

    public void connect() {
        try {
            client = new MqttClient(MQTT_HOST, MQTT_CLIENTID, new MemoryPersistence());
            MqttConnectOptions option = new MqttConnectOptions();
            option.setCleanSession(true);
            option.setUserName(MQTT_USERNAME);
            option.setPassword(MQTT_PASSWORD.toCharArray());
            option.setConnectionTimeout(MQTT_TIMEOUT);
            option.setKeepAliveInterval(MQTT_KEEPALIVE);
            try {
                client.setCallback(new MqttPushCallback());
                client.connect(option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发布主题，用于通知<br>
     * 默认qos为1 非持久化
     * @param topic
     * @param data
     */
    public void publish(String topic, String data) {
        publish(topic, data, 1, false);
    }
    /**
     * 发布
     * @param topic
     * @param data
     * @param qos
     * @param retained
     */
    public void publish(String topic, String data, int qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(data.getBytes());
        MqttTopic mqttTopic = client.getTopic(topic);
        if(null == mqttTopic) {
            logger.error("Topic Not Exist");
        }
        MqttDeliveryToken token;
        try {
            token = mqttTopic.publish(message);
            token.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
