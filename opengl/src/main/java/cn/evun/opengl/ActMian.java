package cn.evun.opengl;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.ExifInterface;
import android.net.Proxy;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shuai.Li13 on 2017/9/14.
 */

public class ActMian extends Activity {

    private  String TAG = "----";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        requestData();
        try {
            postData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postData() throws IOException {

    }

    private void requestData() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://blog.csdn.net/andy_l1/article/details/77097884")
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                boolean successful = response.isSuccessful();
                if (successful){
                    InputStream string = response.body().byteStream();
                    String string1 = response.body().string();
                }
                Log.e(TAG, "onResponse: " );

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
