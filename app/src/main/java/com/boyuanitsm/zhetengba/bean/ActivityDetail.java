package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 活动列表实体
 * Created by xiaoke on 2016/9/6.
 */
public class ActivityDetail implements Parcelable{
    private String	activityName;//'活动名称',
    private String	createPerson;//'创建人',
    private String	createTime;//'创建时间',
    private String	endTime;// '结束时间',
    private String	hyperLink; //'活动H5链接',
    private String	id;//'主键ID',
    private boolean	isValid;//'是否有效',
    private String	modifyPerson;// '修改时间',
    private String	remark; //'备注',
    private String	startTime;// '开始时间',
    private boolean	withCounts;//'是否与次数相关',
    private boolean	withMoney;// '是否与钱相关',
    private int	withShelves;//'是否上架',

    @Override
    public String toString() {
        return "ActivityDetail{" +
                "activityName='" + activityName + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", createTime='" + createTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", hyperLink='" + hyperLink + '\'' +
                ", id='" + id + '\'' +
                ", isValid=" + isValid +
                ", modifyPerson='" + modifyPerson + '\'' +
                ", remark='" + remark + '\'' +
                ", startTime='" + startTime + '\'' +
                ", withCounts=" + withCounts +
                ", withMoney=" + withMoney +
                ", withShelves=" + withShelves +
                '}';
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public boolean isWithCounts() {
        return withCounts;
    }

    public void setWithCounts(boolean withCounts) {
        this.withCounts = withCounts;
    }

    public boolean isWithMoney() {
        return withMoney;
    }

    public void setWithMoney(boolean withMoney) {
        this.withMoney = withMoney;
    }

    public int getWithShelves() {
        return withShelves;
    }

    public void setWithShelves(int withShelves) {
        this.withShelves = withShelves;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityName);
        dest.writeString(this.createPerson);
        dest.writeString(this.createTime);
        dest.writeString(this.endTime);
        dest.writeString(this.hyperLink);
        dest.writeString(this.id);
        dest.writeByte(this.isValid ? (byte) 1 : (byte) 0);
        dest.writeString(this.modifyPerson);
        dest.writeString(this.remark);
        dest.writeString(this.startTime);
        dest.writeByte(this.withCounts ? (byte) 1 : (byte) 0);
        dest.writeByte(this.withMoney ? (byte) 1 : (byte) 0);
        dest.writeInt(this.withShelves);
    }

    public ActivityDetail() {
    }

    protected ActivityDetail(Parcel in) {
        this.activityName = in.readString();
        this.createPerson = in.readString();
        this.createTime = in.readString();
        this.endTime = in.readString();
        this.hyperLink = in.readString();
        this.id = in.readString();
        this.isValid = in.readByte() != 0;
        this.modifyPerson = in.readString();
        this.remark = in.readString();
        this.startTime = in.readString();
        this.withCounts = in.readByte() != 0;
        this.withMoney = in.readByte() != 0;
        this.withShelves = in.readInt();
    }

    public static final Creator<ActivityDetail> CREATOR = new Creator<ActivityDetail>() {
        @Override
        public ActivityDetail createFromParcel(Parcel source) {
            return new ActivityDetail(source);
        }

        @Override
        public ActivityDetail[] newArray(int size) {
            return new ActivityDetail[size];
        }
    };
}
