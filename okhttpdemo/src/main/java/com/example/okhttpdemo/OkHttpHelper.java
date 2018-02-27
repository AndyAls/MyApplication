package com.example.okhttpdemo;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shuai.Li13 on 2017/9/30.
 */

public class OkHttpHelper {

    private  OkHttpClient client;
    private static OkHttpHelper helper;
    private OkHttpHelper() {
        client= new OkHttpClient();
    }

    public static synchronized OkHttpHelper getInstance() {
        if (helper==null){
            helper=new OkHttpHelper();
        }
        return helper;
    }

    public void doGet(String url,BaseCallBack callBack){
        Request reqest=buildRequest(url,null, MethodType.GET);
        doRequest(reqest,callBack);
    }

    private Request buildRequest(String url,Map<String,String> parms,MethodType type) {

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type==MethodType.GET){
           builder.get();
        }else if (type==MethodType.POST){
            RequestBody body = buildRequestBody(parms);
            builder.post(body);
        }
        return builder.build();
    }

    private RequestBody buildRequestBody(Map<String, String> parms) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parms!=null){
            for (Map.Entry<String, String> entry : parms.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }

    private enum MethodType{
        GET,
        POST;
    }
    public void doPost(String url, Map<String,String> parms,BaseCallBack callBack){

        Request reqest=buildRequest(url,parms, MethodType.GET);
        doRequest(reqest,callBack);
    }

    public void doRequest(Request request,BaseCallBack call){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int code = response.code();
            }

        });
    }

    private interface BaseCallBack{
        void onStart();
        void onSuccess();
        void onError();
        void onFailure();
    }
}
