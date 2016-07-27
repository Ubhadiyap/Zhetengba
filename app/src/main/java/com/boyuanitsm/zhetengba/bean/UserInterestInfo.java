package com.boyuanitsm.zhetengba.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * 个人兴趣标签
 * Created by xiaoke on 2016/6/2.
 */
@Table(name = "interest_table")
public class UserInterestInfo implements Parcelable{
    @Column
    private String createPersonId;//'创建人',
    @Column
    private String createTiem;//'创建时间',
    @Column
    private String dictName;// 标签名
    @Column
    private String id;//'主键ID',
    @Column
    private String interestId;// '兴趣标签ID',
    @Column
    private Boolean isValid;// '是否有效.0:无效;1:有效',
    @Column
    private String modifyPersonId;// '修改人',
    @Column
    private String modifyTime;// COMMENT '修改时间',
    @Column
    private String userId;// '用户ID',

    @Override
    public String toString() {
        return "UserInterestInfo{" +
                "createPersonId='" + createPersonId + '\'' +
                ", createTiem='" + createTiem + '\'' +
                ", dictName='" + dictName + '\'' +
                ", id='" + id + '\'' +
                ", interestId='" + interestId + '\'' +
                ", isValid=" + isValid +
                ", modifyPersonId='" + modifyPersonId + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateTiem() {
        return createTiem;
    }

    public void setCreateTiem(String createTiem) {
        this.createTiem = createTiem;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTiem);
        dest.writeString(this.dictName);
        dest.writeString(this.id);
        dest.writeString(this.interestId);
        dest.writeValue(this.isValid);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.userId);
    }

    public UserInterestInfo() {
    }

    protected UserInterestInfo(Parcel in) {
        this.createPersonId = in.readString();
        this.createTiem = in.readString();
        this.dictName = in.readString();
        this.id = in.readString();
        this.interestId = in.readString();
        this.isValid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.modifyPersonId = in.readString();
        this.modifyTime = in.readString();
        this.userId = in.readString();
    }

    public static final Creator<UserInterestInfo> CREATOR = new Creator<UserInterestInfo>() {
        @Override
        public UserInterestInfo createFromParcel(Parcel source) {
            return new UserInterestInfo(source);
        }

        @Override
        public UserInterestInfo[] newArray(int size) {
            return new UserInterestInfo[size];
        }
    };

}
