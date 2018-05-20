package zyz.com.meetroom.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyz.com.meetroom.R;
import zyz.com.meetroom.adapter.MsgItemAdapter;
import zyz.com.meetroom.entity.MsgBrifeModel;
import zyz.com.meetroom.http.HttpCallbackListener;
import zyz.com.meetroom.http.HttpUtil;
import zyz.com.meetroom.http.UrlHandler;

public class MsgListActivity extends AppCompatActivity {

    @BindView(R.id.msg_list_rv)
    RecyclerView rv;

    private List<MsgBrifeModel> modelList = new ArrayList<>();

    private MsgItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);
        ButterKnife.bind(this);
        String url = UrlHandler.getMsgList();
        HttpUtil.getInstance().get(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) ->
                                new Date(json.getAsJsonPrimitive().getAsLong())).create();
                modelList = gson
                        .fromJson(response,new TypeToken<List<MsgBrifeModel>>(){}.getType());
                if(modelList == null) return;
                runOnUiThread(()->{
                    if(adapter == null) {
                        adapter = new MsgItemAdapter();
                        adapter.setListener((position,msgId)->{
                            Intent intent = new Intent(MsgListActivity.this,MsgDetailActivity.class);
                            intent.putExtra("msg_id",msgId);
                            startActivity(intent);
                        });
                        rv.setLayoutManager(new LinearLayoutManager(MsgListActivity.this));
                        adapter.setModels(modelList);
                        rv.setAdapter(adapter);
                    } else {
                        adapter.setModels(modelList);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}
