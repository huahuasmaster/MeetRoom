package zyz.com.meetroom.entity;


import java.util.Date;

/**
 * Created by dingxb on 2017/11/29.
 */
public class AppointmentInsertModel {
    private String meetingName;

    private Long leaderId;

    private Date meetingTime;

    private Date endTime;

    private Long pathId;

    private Long roomId;

    private Long[] memberIds;

    private String remark;

    private Boolean audited;

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
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

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long[] getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Long[] memberIds) {
        this.memberIds = memberIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getAudited() {
        return audited;
    }

    public void setAudited(Boolean audited) {
        this.audited = audited;
    }

    public AppointmentInsertModel(String meetingName, Long leaderId, Date meetingTime, Date endTime, Long pathId, Long roomId, Long[] memberIds, String remark, Boolean audited) {
        this.meetingName = meetingName;
        this.leaderId = leaderId;
        this.meetingTime = meetingTime;
        this.endTime = endTime;
        this.pathId = pathId;
        this.roomId = roomId;
        this.memberIds = memberIds;
        this.remark = remark;
        this.audited = audited;
    }
}
