package zyz.com.meetroom.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.OnClick;
import okhttp3.MediaType;
import zyz.com.meetroom.R;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import zyz.com.meetroom.R;
import zyz.com.meetroom.http.HttpCallbackListener;
import zyz.com.meetroom.http.HttpUtil;
import zyz.com.meetroom.http.UrlHandler;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.register_btn)
    Button loginBtn;

    @BindView(R.id.go_register_btn)
    Button goRegisterBtn;

    @BindView(R.id.account_edit)
    EditText phoneEdit;

    @BindView(R.id.password_edit)
    EditText pwdEdit;
    private final String TAG = this.getClass().getName();
    @OnClick(R.id.register_btn)
    void login() {
        final String phone = phoneEdit.getText().toString().trim();
        final String password = pwdEdit.getText().toString().trim();
        waitDialog.show();

        final MediaType type = MediaType.parse("application/json;charset=utf-8");
        JsonObject o = new JsonObject();
        o.addProperty("phone",phone);
        o.addProperty("password",password);
        Log.i(TAG, "login: json:"+o.toString());
        RequestBody body = RequestBody.create(type,o.toString());
        String url = UrlHandler.getLoginUrl();
        HttpUtil.getInstance().post(url, body, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(phoneEdit,"登录成功",Snackbar.LENGTH_SHORT).show();
                        if (waitDialog.isShowing()) {
                            waitDialog.cancel();
                        }
                        loginSp.edit().putString("password",password)
                                .putString("phone",phone).apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (waitDialog.isShowing()) {
                            waitDialog.cancel();
                        }
                        Snackbar.make(phoneEdit, "登录失败，请检查。", Snackbar.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private SharedPreferences loginSp;
    private MaterialDialog waitDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginSp = getSharedPreferences("login_data", Context.MODE_PRIVATE);

        waitDialog = new MaterialDialog.Builder(this)
                .title("登录中……")
                .content("请稍后")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .build();
        phoneEdit.setFocusable(true);
        phoneEdit.requestFocus();
        phoneEdit.setText("" + loginSp.getString("phone", ""));
        pwdEdit.setText("" + loginSp.getString("password", ""));

        loginBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                return false;
            }
        });
        //检查权限，动态申请未给予的权限
//        List<String> permissionList = new ArrayList<>();
//        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.READ_SMS);
//        }
////        if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.RECEIVE_SMS) !=PackageManager.PERMISSION_GRANTED) {
////            permissionList.add(Manifest.permission.RECEIVE_SMS);
////        }
//        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.READ_CONTACTS);
//        }
//        if (!permissionList.isEmpty()) {
//            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(LoginActivity.this, permissions, 1);
//        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0) {
//                    for (int result : grantResults) {
//                        if (result != PackageManager.PERMISSION_GRANTED) {
//                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
//                        }
//                    }
//                } else {
//                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            default:
//        }
//    }
}
