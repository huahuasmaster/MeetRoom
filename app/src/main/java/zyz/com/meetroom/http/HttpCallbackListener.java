package zyz.com.meetroom.http;


import java.io.IOException;

/**
 * Created by Administrator on 2017/7/14.
 */

public abstract class HttpCallbackListener {

    public abstract void onFinish(String response);
    public  void onError(Exception e){
        if (e instanceof IOException){
            // io 异常
//            Logger.e(e.getMessage());
            return;
        }
    }
}
