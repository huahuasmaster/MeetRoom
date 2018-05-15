package zyz.com.meetroom.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyz.com.meetroom.R;
import zyz.com.meetroom.entity.AppointmentBriefModel;

public class MeetingItemAdapter extends RecyclerView.Adapter{
    private DateFormat format = new SimpleDateFormat("HH:mm:ss");
    private List<AppointmentBriefModel> models;

    public List<AppointmentBriefModel> getModels() {
        return models;
    }

    public void setModels(List<AppointmentBriefModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item_main,parent);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        AppointmentBriefModel model = models.get(position);
        myViewHolder.location.setText(model.getPath()+" "+model.getRoomName());
        myViewHolder.time.setText(format.format(model.getMeetingTime()) + "-"
                + format.format(model.getEndTime()));
        myViewHolder.meetName.setText(model.getMeetingName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.meet_location)
        TextView location;

        @BindView(R.id.meeting_name)
        TextView meetName;

        @BindView(R.id.start_time)
        TextView time;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
