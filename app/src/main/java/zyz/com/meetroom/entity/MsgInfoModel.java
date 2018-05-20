package zyz.com.meetroom.entity;

import java.util.Date;

/**
 * Created by dingxb on 2017/11/29.
 */
public class MsgInfoModel {

    private Long id;

    private Date sendTime;

    private String title;

    private String context;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
