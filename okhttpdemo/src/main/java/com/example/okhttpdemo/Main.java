package com.example.okhttpdemo;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = (ImageView) findViewById(R.id.img);
        WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(img);
        ImageView imageView = imageViewWeakReference.get();
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506576450829&di=1e5713f042887d766b8381e22dc3fb00&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Fbmiddle%2F850113aegw1evdyskixj4g20b40694qp.jpg")
                .asGif()
                .dontAnimate()
                .thumbnail(0.1f)
                .fallback(R.mipmap.ic_launcher)
                .error(R.drawable.pd_shape)
                .skipMemoryCache(true)
                .into(imageView);

        memory();
    }

    private void memory() {
        ActivityManager manager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        long availMem = info.availMem;
        boolean lowMemory = info.lowMemory;
        long threshold = info.threshold;
        long totalMem = info.totalMem;
        String s = Formatter.formatFileSize(this, availMem);
        String s1 = Formatter.formatFileSize(this, threshold);
        String s2 = Formatter.formatFileSize(this, totalMem);

        Log.i("", "memory:");

    }

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                }
            }).connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    ComponentCallbacks2 componentCallbacks2 = new ComponentCallbacks2() {
        @Override
        public void onTrimMemory(int level) {

        }


        @Override
        public void onConfigurationChanged(Configuration newConfig) {

            }

        @Override
        public void onLowMemory() {

        }
    };

    //上传图片
    private void uploadImg(Map<String,Object> map,String url){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getValue() instanceof File){
                File f =(File)entry.getValue();
                builder.addFormDataPart(entry.getKey(),f.getName(), RequestBody.create(MediaType.parse("image/*"),f));
            }else {
                builder.addFormDataPart(entry.getKey(),entry.getValue().toString());
            }
            this.registerComponentCallbacks(componentCallbacks2);
        }


        RequestBody body = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }

            @Override
            public long contentLength() throws IOException {
                return super.contentLength();
            }
        };
        Request request=new Request.Builder()
                .post(builder.build())
                .url("eee")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                boolean successful = response.isSuccessful();
                if (successful){
                    Headers headers = response.headers();
                }
            }
        });
    }
    class MyGlideUrl extends GlideUrl{

        public MyGlideUrl(String url) {
            super(url);
        }

        @Override
        public String getCacheKey() {
            return super.getCacheKey();
        }
    }
}
