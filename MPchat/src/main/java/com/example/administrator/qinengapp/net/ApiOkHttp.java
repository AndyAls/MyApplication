package com.example.administrator.qinengapp.net;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ApiOkHttp {

    static OkHttpClient okHttpClient = new OkHttpClient();
    static Gson gson = new Gson();
    private static String result;

    public static void getStringDate(final Context context, String url, final Class clazz){

        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context,"网络连接错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if (string==null) {
                    Object object = gson.fromJson(string, clazz);
                }else {
                    Toast.makeText(context,"网络连接错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String postDate(Context context, String url, String key, String value) throws IOException {

        RequestBody body = new FormBody.Builder().add(key,value).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()){
            Toast.makeText(context,"网络连接错误",Toast.LENGTH_SHORT).show();
        }else {
            result = response.body().string();
        }

        return result;
    }

}
