package zyz.com.meetroom.http;


import android.util.Log;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/14.
 */

public abstract class HttpCallbackListener {

    public abstract void onFinish(String response);
    public  void onError(Exception e){
        if (e instanceof IOException){
            Log.i("发生了IO异常",e.getMessage());
            // io 异常
//            Logger.e(e.getMessage());
            return;
        }
    }
}
