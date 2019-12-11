package com.example.test4.MVPModel.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUil {

    //和服务器进行交互
    //现在发起一条GHTTP请求只需要调用 sendOkHttpRequest()方法
    //然后传入请求地址，并注册一个回调来处理服务器响应就可以了

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
