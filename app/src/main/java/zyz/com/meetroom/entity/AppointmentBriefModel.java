package zyz.com.meetroom.entity;


import java.util.Date;

/**
 * Created by Charles Chen on 2017/11/30.
 */
public class AppointmentBriefModel {

    private Long id;

    private Date meetingTime;

    private Date endTime;

    private String path;

    private String meetingName;

    private String roomName;

    private String audited;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAudited() {
        return audited;
    }

    public void setAudited(String audited) {
        this.audited = audited;
    }
}
