package com.example.dell.day0124;


import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpUtil {

    private static volatile OKHttpUtil mInstance;
    private final OkHttpClient okHttpClient;
    private final Handler handler;


    private OKHttpUtil() {
        okHttpClient = new OkHttpClient();
        handler = new Handler();
    }

    public static OKHttpUtil getmInstance(){

        if (null==mInstance){
            synchronized (OKHttpUtil.class){
                if (null==mInstance){
                    mInstance = new OKHttpUtil();
                }
            }
        }

            return mInstance;
    }

    public  void doGet(String url, HashMap<String,String> paramsMap, final OnResponseListener listener){

            StringBuilder sb = new StringBuilder();

            sb.append(url);

            if (sb.lastIndexOf("?")==-1){
                sb.append("?");
            }
            for (Map.Entry<String,String> entry: paramsMap.entrySet()){

                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            sb.deleteCharAt(sb.length()-1);

            Request request = new Request.Builder().url(sb.toString()).build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (null!=listener){
                                listener.onFailure(e.getLocalizedMessage());
                            }

                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()){
                                try {
                                    String string = response.body().string();

                                    if (null != listener){

                                        listener.onFailure(string);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                }
            });

    }


    public  void doPost(String url, HashMap<String,String> paramsMap, final OnResponseListener listener){

        FormBody.Builder formBody = new FormBody.Builder();

        for (Map.Entry<String,String> entry:paramsMap.entrySet()){

            formBody.add(entry.getKey(),entry.getValue());
        }

        Request request = new Request.Builder().post(formBody.build()).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (null!=listener){
                            listener.onFailure(e.getLocalizedMessage());
                        }

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                            if  (response.isSuccessful()){

                                try {
                                    String string = response.body().string();
                                    if (null!=listener){
                                        listener.onSuccess(string);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                    }
                });

            }
        });
    }

    interface OnResponseListener {
        //成功
        public void onSuccess(String result);

        //失败
        public void onFailure(String result);
    }
}