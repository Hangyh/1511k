package com.example.dell.day0124;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void xiazai(View view) {

        HashMap<String,String> parasMap = new HashMap<>();
        parasMap.put("mobile", "17611200379");
        parasMap.put("password", "123456");

        OKHttpUtil.getmInstance().doPost("http://120.27.23.105/user/login",parasMap, new OKHttpUtil.OnResponseListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("t", "doPost onSuccess result=" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.i("t", "doPost onSuccess result=" + result);
            }
        });
    }

    public void login(View view) {

        HashMap<String,String> parasMap =  new HashMap<>();
        parasMap.put("key","22ecf6c32440e");
        OKHttpUtil.getmInstance().doGet("http://apicloud.mob.com/v1/weather/type", parasMap, new OKHttpUtil.OnResponseListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("t", "doGet onSuccess result=" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.i("t", "doGet onSuccess result=" + result);
            }
        });
    }
}
