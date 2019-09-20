package com.unimelb.studypartner.common;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by xiyang on 2019/9/20
 */
public class MqttPushCallback implements MqttCallback {
    private static Logger logger = Logger.getLogger(MqttPushCallback.class);

    @Override
    public void connectionLost(Throwable cause) {
        // maybe reconnect
        logger.info("start reconnect...");
        MqttPushClient.getInstance().connect();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //log.info(token.isComplete() + "");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("Topic: " + topic);
        logger.info("Message: " + new String(message.getPayload()));
    }
}
