package zyz.com.meetroom.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

import zyz.com.meetroom.R;
import zyz.com.meetroom.util.DensityUtil;


/**
 * Created by zhuang_ge on 2017/8/4.
 * 侧滑菜单中空间卡片的房间标签适配器
 */

public class MemberNameAdapter {

    private List<String> memberNameList;

    private Context mContext;

    private FlowLayout mFlowLayout;

    /**
     * 将子view添加至流动布局以内
     */
    public void addLabels() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for(String s : memberNameList) {
            
            View v = inflater.inflate(R.layout.member_label,null);

            CardView labelBack = v.findViewById(R.id.label_back);
            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                  ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = DensityUtil.dip2px(mContext, 4);
            lp.setMargins(margin, margin, margin, margin);
            labelBack.setLayoutParams(lp);
            TextView roomName = (TextView) (v.findViewById(R.id.room_name));
            roomName.setText(s);
            mFlowLayout.addView(v);
        }
    }

    public void notifiDataSetChanged() {
        clear();
        addLabels();
    }

    public void clear() {
        mFlowLayout.removeAllViews();
    }

    public MemberNameAdapter(List<String> roomList, FlowLayout mFlowLayout, Context mContext) {
        this.memberNameList = roomList;
        this.mFlowLayout = mFlowLayout;
        this.mContext = mContext;
    }

    public void clearLabels() {
        mFlowLayout.removeAllViews();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public MemberNameAdapter() {
    }

    public List<String> getMemberNameList() {
        return memberNameList;
    }

    public void setMemberNameList(List<String> memberNameList) {
        this.memberNameList = memberNameList;
    }
}


