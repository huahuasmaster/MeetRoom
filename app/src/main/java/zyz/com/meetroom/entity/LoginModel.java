package zyz.com.meetroom.entity;


/**
 * Created by Charles Chen on 2017/11/30.
 */
//给前端存在前端的
public class LoginModel {

    private Long id;

    private String staffName;

    private String department;

    private String picture;

    private Integer leftPoint;

    private String identity;


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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Integer leftPoint) {
        this.leftPoint = leftPoint;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public LoginModel injectPicture(){
//        this.picture = FileLoader.getPicture(this.picture);
        return this;
    }
}
