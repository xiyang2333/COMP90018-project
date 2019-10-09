package com.example.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

    public static <T> T httpGet(String url, Class<T> T) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = null;
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("http success", response.toString());
                String body = response.body().string();
                return JSON.parseObject(body, T);
            } else {
                Log.d("http fail", response.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <K, T> T httpPost(String url, K data, Class<K> K, Class<T> T) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            String dataJson = JSON.toJSONString(data);
            RequestBody body = RequestBody.create(json, dataJson);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("http success", response.toString());
                String responseBody = response.body().string();
                return JSON.parseObject(responseBody, T);
            } else {
                Log.d("http fail", response.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
