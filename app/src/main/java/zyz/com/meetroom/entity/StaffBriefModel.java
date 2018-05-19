package zyz.com.meetroom.entity;


/**
 * Created by Charles Chen on 2017/11/27.
 */
public class StaffBriefModel {
    private Long id;

    private String staffName;

    private String picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public StaffBriefModel injectPicture(){
        return this;
    }
}
