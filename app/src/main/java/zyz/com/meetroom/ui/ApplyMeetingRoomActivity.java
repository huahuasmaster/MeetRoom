package zyz.com.meetroom.ui;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nex3z.flowlayout.FlowLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import zyz.com.meetroom.R;
import zyz.com.meetroom.adapter.MemberNameAdapter;
import zyz.com.meetroom.entity.AppointmentInsertModel;
import zyz.com.meetroom.entity.StaffBriefModel;
import zyz.com.meetroom.http.HttpCallbackListener;
import zyz.com.meetroom.http.HttpUtil;
import zyz.com.meetroom.http.RequestJsonBodyBuilder;
import zyz.com.meetroom.http.UrlHandler;

public class ApplyMeetingRoomActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @BindView(R.id.btn_apply)
    View applyBtn;

    @BindView(R.id.flow_apply)
    FlowLayout flowLayout;

    @BindView(R.id.choose_member_apply_btn)
    Button chooseMemberBtn;

    @BindView(R.id.meeting_end_time_apply_edit)
    TextView meetEndTimeEdit;

    @BindView(R.id.meeting_name_apply_edit)
    EditText meetNameEdit;

    @BindView(R.id.meeting_time_apply_edit)
    TextView meetStartTimeEdit;

    @BindView(R.id.meeting_remark_apply_edit)
    EditText meetRemarkEdit;

    Calendar now = Calendar.getInstance();
    DatePickerDialog startDateDialog;
    TimePickerDialog startTimeDialog;
    DatePickerDialog endDateDialog;
    TimePickerDialog endTimeDialog;
    MaterialDialog chooseStaffDialog;

    private Calendar startDateTime = Calendar.getInstance();
    private List<StaffBriefModel> staffs = new ArrayList<>();
    String[] staffNames;
    //记录选中的员工的下标
    private Integer[] choosedStaffIndex;
    //记录选中的员工的名称
    private CharSequence[] choosedStaffNames;

    private Calendar endDateTime = Calendar.getInstance();
    private MemberNameAdapter adapter;

    private boolean startDateTimePicked;
    private boolean endDateTimePicked;

    @OnClick(R.id.meeting_time_apply_edit)
    void pickStartTime() {
        startDateDialog.show(getFragmentManager(), "会议开始日期");
    }

    @OnClick(R.id.meeting_end_time_apply_edit)
    void pickEndTime() {
        endDateDialog.show(getFragmentManager(), "会议结束日期");
    }

    @OnClick(R.id.choose_member_apply_btn)
    void choosedMembers() {
        chooseStaffDialog.show();
    }

    private static final String TAG = "apply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_meeting_room);
        ButterKnife.bind(this);
        adapter = new MemberNameAdapter(new ArrayList<String>(), flowLayout, this);
        startDateDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        startTimeDialog = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                true);
        endDateDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        endTimeDialog = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                true);
        chooseStaffDialog = new MaterialDialog.Builder(this)
                .title("选择参与会议人员")
                .positiveText("确定")
                .negativeText("取消")
                .items(staffs)
                .itemsCallbackMultiChoice(null, (dialog, which, text) -> {
                    Log.i(TAG, "onCreate: " + which.length);
                    Log.i(TAG, "onCreate: " + text.length);
                    choosedStaffIndex = which;
                    choosedStaffNames = text;
                    return true;
                })
                .alwaysCallMultiChoiceCallback()
                .onPositive((dialog, which) -> initNames())
                .build();
        //查询一下员工列表
        String url = UrlHandler.getStaffList(-1);
        HttpUtil.getInstance().get(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
//               response = response.substring(1,response.length()-2);
                Log.i(TAG, "onFinish: " + response);
                staffs = new Gson().fromJson(response,
                        new TypeToken<List<StaffBriefModel>>() {
                        }.getType());
                if (staffs != null) {
                    staffNames = new String[staffs.size()];
                    for (int i = 0; i < staffs.size(); i++) {
                        staffNames[i] = staffs.get(i).getStaffName();
                    }
                }
                chooseStaffDialog.setItems(staffNames);

            }
        });
    }

    //向flowlayout中填装
    private void initNames() {
        List<String> names = new ArrayList<>();
        for (CharSequence s : choosedStaffNames) {
            names.add((String) s);
        }
        initNames(names);
    }

    private void initNames(List<String> names) {
        adapter.setMemberNameList(names);
        adapter.notifiDataSetChanged();
    }

    @OnClick(R.id.btn_apply)
    void apply() {
        String name = meetNameEdit.getText().toString().trim();
        String remark = meetRemarkEdit.getText().toString().trim();
        //1.检查信息完整
        if (startDateTimePicked && endDateTimePicked &&
                TextUtils.isEmpty(name) &&
                TextUtils.isEmpty(remark)) {
            Snackbar.make(chooseMemberBtn, "请检查信息完整", Snackbar.LENGTH_SHORT).show();
            return;
        }
        //2.提交信息
//        AppointmentInsertModel model = new AppointmentInsertModel(name,
//                Long.parseLong(UrlHandler.getUserId()),
//                meetStartTimeEdit.getText().toString(),
//                meetEndTimeEdit.getText().toString(),
//                -1L,
//                null,
//                toArray(memberIds),
//                remark,false);

        AppointmentInsertModel model = new AppointmentInsertModel(name,
                Long.parseLong(UrlHandler.getUserId()),
                startDateTime.getTime(),
                endDateTime.getTime(),
                -1L,
                null,
                toArray(choosedStaffIndex,staffs),
                remark, false);

        RequestBody body = new RequestJsonBodyBuilder<AppointmentInsertModel>()
                .fromPojo(model);

        String url = UrlHandler.getApplyUrl();

        HttpUtil.getInstance().post(url, body, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                runOnUiThread(() -> Toast.makeText(ApplyMeetingRoomActivity.this,
                        "提交成功", Toast.LENGTH_SHORT).show());
                finish();
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: ", e);
                runOnUiThread(()->Toast.makeText(ApplyMeetingRoomActivity.this,
                        e.getMessage(), Toast.LENGTH_SHORT).show());


            }
        });

    }

    @OnClick(R.id.choose_member_apply_btn)
    void chooseMember() {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view == startDateDialog) {
            startDateTime.set(year, monthOfYear, dayOfMonth);
            startTimeDialog.show(getFragmentManager(), "会议开始时间");
        }
        if (view == endDateDialog) {
            endDateTime.set(year, monthOfYear, dayOfMonth);
            endTimeDialog.show(getFragmentManager(), "会议结束时间");
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (view == startTimeDialog) {
            startDateTime.set(startDateTime.get(Calendar.YEAR),
                    startDateTime.get(Calendar.MONTH),
                    startDateTime.get(Calendar.DATE),
                    hourOfDay,
                    minute, second);
            meetStartTimeEdit.setText(FORMAT.format(startDateTime.getTime()));
            startDateTimePicked = true;
        }
        if (view == endTimeDialog) {
            endDateTime.set(endDateTime.get(Calendar.YEAR),
                    endDateTime.get(Calendar.MONTH),
                    endDateTime.get(Calendar.DATE),
                    hourOfDay,
                    minute, second);
            meetEndTimeEdit.setText(FORMAT.format(endDateTime.getTime()));
            endDateTimePicked = false;
        }
    }

    private Long[] toArray(Integer[] index,List<StaffBriefModel> staffBriefModels) {
        if(index == null) {
            return null;
        }
        Long[] result = new Long[index.length];
        for(int i = 0; i < index.length; i++) {
            result[i] = staffBriefModels.get(i).getId();
        }
        return result;
    }

}
