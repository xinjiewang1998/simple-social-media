package com.example.myapplication.paint;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class CustomWebSocket {
    // websocket to ccyy.xyz for predicting and pairing functionality
    private static OkHttpClient mClient;
    private static CustomWebSocket myInstance;
    private CustomWebSocket() {
        // websocket to ccyy.xyz for pairing functionality
        mClient = new OkHttpClient.Builder()
                .readTimeout(3600, TimeUnit.SECONDS)
                .writeTimeout(3600, TimeUnit.SECONDS)
                .connectTimeout(3600, TimeUnit.SECONDS)
                .build();
    }

    public static CustomWebSocket getInstance() {
        if (myInstance == null) {
            myInstance = new CustomWebSocket();
        }
        return myInstance;
    }

    public OkHttpClient getClient() {
        return mClient;
    }
}
