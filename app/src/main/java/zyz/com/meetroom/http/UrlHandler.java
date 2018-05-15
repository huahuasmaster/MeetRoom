package zyz.com.meetroom.http;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.annotation.Retention;
import java.util.Date;

import zyz.com.meetroom.application.ContextApplication;


/**
 * 记录、生成请求的辅助类
 * 所有网络请求地址在此处维护
 * Created by zhuang_ge on 2017/8/10.
 */

public class UrlHandler {



    private static String port = "8088";


    private static SharedPreferences loginSp = ContextApplication
            .getContext()
            .getSharedPreferences("login_data", Context.MODE_PRIVATE);

    //获取登录url
    public static String getLoginUrl() {
        return "http://"+getIp()+":"+port+"/login";
    }

    //假登录URL
    public static String getFakeLoginUrl(){return  getHead()+"/meeting-room/login";}

    //待进行会议列表
    public static String getMeetingList() {return getHead()+"/meeting-room/appointment/"
            +getUserId()+"/list";
            }


    /**
     * 获取(除了登录接口)请求的开头ip与端口
     *
     * @return
     */
    public static String getHead() {
        return "http://" + getIp() + ":" + port;
    }

    //获取服务器ip
    public static String getIp() {
        return loginSp.getString("ip", "192.168.1.196");
    }

    //设置服务器ip
    public static void setIp(String ip) {
        loginSp.edit().putString("ip", ip).apply();
    }


    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        UrlHandler.port = port;
    }


    public static String getUserId() {
        return loginSp.getString("id", "-1");
    }

    public static void setUserId(String userId) {
        loginSp.edit().putString("id", userId).apply();
    }

}
