package zyz.com.meetroom.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyz.com.meetroom.R;
import zyz.com.meetroom.entity.MsgInfoModel;
import zyz.com.meetroom.http.HttpCallbackListener;
import zyz.com.meetroom.http.HttpUtil;
import zyz.com.meetroom.http.UrlHandler;

public class MsgDetailActivity extends AppCompatActivity {

    @BindView(R.id.msg_content_detail)
    TextView content;

    @BindView(R.id.msg_title_detail)
    TextView title;

    MsgInfoModel msgInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        long msgId = intent.getLongExtra("msg_id",-1L);
        if(msgId != -1L) {
            String url = UrlHandler.getMsgDetail(msgId);
            HttpUtil.getInstance().get(url, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) ->
                                    new Date(json.getAsJsonPrimitive().getAsLong())).create();
                    msgInfoModel = gson.fromJson(response,MsgInfoModel.class);
                    if(msgInfoModel != null) {
                        runOnUiThread(()->{
                            title.setText(msgInfoModel.getTitle());
                            content.setText(msgInfoModel.getContext());
                        });

                    }
                }
            });
        }
    }
}
