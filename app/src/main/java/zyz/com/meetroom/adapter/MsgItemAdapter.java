package zyz.com.meetroom.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import zyz.com.meetroom.R;
import zyz.com.meetroom.entity.MsgBrifeModel;

public class MsgItemAdapter extends RecyclerView.Adapter {

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<MsgBrifeModel> models;

    private MsgItemClickListener listener;

    public MsgItemClickListener getListener() {
        return listener;
    }

    public void setListener(MsgItemClickListener listener) {
        this.listener = listener;
    }

    public List<MsgBrifeModel> getModels() {
        return models;
    }

    public void setModels(List<MsgBrifeModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_item,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MsgBrifeModel mModel = models.get(position);
        MyHolder myHolder = (MyHolder)holder;
        myHolder.redPoint.setVisibility(mModel.getRead() ? View.INVISIBLE : View.VISIBLE);
        myHolder.title.setText(mModel.getTitle());
        myHolder.time.setText(FORMAT.format(mModel.getSendTime()));
        if(listener != null) {
            myHolder.whole.setOnClickListener((view)->listener.onClick(position,mModel.getId()));
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        View whole;
        TextView title;
        TextView time;
        ImageView redPoint;

        MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.msg_title);
            time = itemView.findViewById(R.id.sent_time);
            redPoint = itemView.findViewById(R.id.red_point);
            whole = itemView.findViewById(R.id.whole_view_msg_list);
        }
    }

    public interface MsgItemClickListener {
         void onClick(int position, long msgId);
    }
}
