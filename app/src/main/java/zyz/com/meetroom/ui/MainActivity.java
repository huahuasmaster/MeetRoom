package zyz.com.meetroom.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import zyz.com.meetroom.R;
import zyz.com.meetroom.http.HttpUtil;
import zyz.com.meetroom.http.UrlHandler;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.name_txt_main)
    EditText nameMain;

    @BindView(R.id.name_header)
    EditText nameHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initName();
    }

    private void initName() {
//        String url = UrlHandler.getLoginUrl();
//        RequestBody body = new FormBody.Builder()
//                .
//        HttpUtil.getInstance().post(url,);
    }
}
