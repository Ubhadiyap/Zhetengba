package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * Created by xiaoke on 2016/6/23.
 */
@Table(name="activity_label")
public class ActivityMess implements Parcelable {
    @Column
   private  String userId;
    @Column
    private  String  petName;
    @Column
     private String message;//":"加入到活动中
    @Column
    private String type;//0是活动
    @Column
     private String userIcon;//
    @Column
    private String createTime;
    @Column
    private String activityTheme;
    @Column
    private String messtype;
    @Column
    private String activityId;
    @Column
    private String scheduleId;


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getMesstype() {
        return messtype;
    }

    public void setMesstype(String messtype) {
        this.messtype = messtype;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ActivityMess{" +
                "userId='" + userId + '\'' +
                ", petName='" + petName + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }

    public static Creator<ActivityMess> getCREATOR() {
        return CREATOR;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public ActivityMess() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.petName);
        dest.writeString(this.message);
        dest.writeString(this.type);
        dest.writeString(this.userIcon);
        dest.writeString(this.createTime);
        dest.writeString(this.activityTheme);
        dest.writeString(this.messtype);
        dest.writeString(this.activityId);
        dest.writeString(this.scheduleId);
    }

    protected ActivityMess(Parcel in) {
        this.userId = in.readString();
        this.petName = in.readString();
        this.message = in.readString();
        this.type = in.readString();
        this.userIcon = in.readString();
        this.createTime = in.readString();
        this.activityTheme = in.readString();
        this.messtype = in.readString();
        this.activityId = in.readString();
        this.scheduleId = in.readString();
    }

    public static final Creator<ActivityMess> CREATOR = new Creator<ActivityMess>() {
        @Override
        public ActivityMess createFromParcel(Parcel source) {
            return new ActivityMess(source);
        }

        @Override
        public ActivityMess[] newArray(int size) {
            return new ActivityMess[size];
        }
    };
}
