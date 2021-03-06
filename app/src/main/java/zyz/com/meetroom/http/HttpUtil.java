package zyz.com.meetroom.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zyz.com.meetroom.application.ContextApplication;

/**
 * Created by zhuang_ge on 2017/11/13.
 */

public class HttpUtil {
    private OkHttpClient client;
    private  String token;
    private static final String IDENTITY = "user";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    private HttpUtil(){
        client = new OkHttpClient();
        sp = ContextApplication.getContext()
                .getSharedPreferences("login_data", Context.MODE_PRIVATE);
        editor = sp.edit();
        token = sp.getString("authorization","");
    }
    private static HttpUtil httpUtil = null;
    public static HttpUtil getInstance() {
        if(httpUtil == null) {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    public void sendRequestWithCallback(final RequestTypeEnum method, final String address, final RequestBody body, final HttpCallbackListener listener,
                                        HashMap<String,String> headers) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder()
                        .url(address).header("authorization",token)
                        .header("identity",IDENTITY);
                if(headers != null) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        builder.header(entry.getKey(), entry.getValue());
                    }
                }
                Log.i("url", "使用了token进行访问："+token);
                switch (method) {
                    case POST:
                        builder.post(body);
                        break;
                    case PUT:
                        builder.put(body);
                        break;
                    case DELETE:
                        builder.delete(body);
                        break;
                    default:
                        builder.get();
                        break;
                }

                Request request = builder.build();
                try {
                    //实际进行请求的代码
                    Log.i("url",address);
                    Response response = client.newCall(request).execute();
                    String temp = response.header("authorization");
                    Log.i("url", "run: 获取到的token："+token);
                    if(temp == null) {
                          Log.i("","没有token使用默认token");
                    } else {
                        token = temp;
                        Log.i("","收到token："+token);
                    }

                    editor.putString("authorization",token).apply();
                    String result = response.body().string();
                    if (result != null && listener != null) {
                        //当response的code大于200，小于300时，视作请求成功
                        Log.i("", "run: "+result);
                        if (response.isSuccessful()) {
                            listener.onFinish(result);
                        } else {
                            listener.onError(new ServerException(result));
                        }
                    }

                } catch (IOException e) {
                    if(listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();

    }

    public void post(final String address, RequestBody body, final HttpCallbackListener listener) {
        sendRequestWithCallback(RequestTypeEnum.POST,address,body,listener,null);
    }

    public void get(String address,HttpCallbackListener listener) {
        sendRequestWithCallback(RequestTypeEnum.GET,address,null,listener,null);
    }

    public void get(String address,HttpCallbackListener listener,HashMap<String,String> map) {
        sendRequestWithCallback(RequestTypeEnum.GET,address,null,listener,map);
    }

    public void delete(String address,RequestBody body,HttpCallbackListener listener) {
        sendRequestWithCallback(RequestTypeEnum.DELETE,address,body,listener,null);
    }

    public void put(String address,RequestBody body,HttpCallbackListener listener) {
        sendRequestWithCallback(RequestTypeEnum.PUT,address,body,listener,null);
    }



}
