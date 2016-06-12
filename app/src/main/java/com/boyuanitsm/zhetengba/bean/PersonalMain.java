package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 个人主页对应的实体对象；
 * Created by xiaoke on 2016/6/8.
 */
public class PersonalMain implements Parcelable {

    private List<CircleEntity> circleEntity;
    private  List<ScheduleInfo> scheduleEntity;
    private List<CircleEntity> circleTalkEntity;
    private List<UserInfo> userEntity ;
    private List<UserInterestInfo> userInterestEntity;

    public List<CircleEntity> getCircleEntity() {
        return circleEntity;
    }

    public void setCircleEntity(List<CircleEntity> circleEntity) {
        this.circleEntity = circleEntity;
    }

    public List<ScheduleInfo> getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(List<ScheduleInfo> scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public List<CircleEntity> getCircleTalkEntity() {
        return circleTalkEntity;
    }

    public void setCircleTalkEntity(List<CircleEntity> circleTalkEntity) {
        this.circleTalkEntity = circleTalkEntity;
    }

    public List<UserInfo> getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(List<UserInfo> userEntity) {
        this.userEntity = userEntity;
    }

    public List<UserInterestInfo> getUserInterestEntity() {
        return userInterestEntity;
    }

    public void setUserInterestEntity(List<UserInterestInfo> userInterestEntity) {
        this.userInterestEntity = userInterestEntity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.circleEntity);
        dest.writeTypedList(this.scheduleEntity);
        dest.writeTypedList(this.circleTalkEntity);
        dest.writeTypedList(this.userEntity);
        dest.writeList(this.userInterestEntity);
    }

    public PersonalMain() {
    }

    protected PersonalMain(Parcel in) {
        this.circleEntity = in.createTypedArrayList(CircleEntity.CREATOR);
        this.scheduleEntity = in.createTypedArrayList(ScheduleInfo.CREATOR);
        this.circleTalkEntity = in.createTypedArrayList(CircleEntity.CREATOR);
        this.userEntity = in.createTypedArrayList(UserInfo.CREATOR);
        this.userInterestEntity = new ArrayList<UserInterestInfo>();
        in.readList(this.userInterestEntity, UserInterestInfo.class.getClassLoader());
    }

    public static final Creator<PersonalMain> CREATOR = new Creator<PersonalMain>() {
        @Override
        public PersonalMain createFromParcel(Parcel source) {
            return new PersonalMain(source);
        }

        @Override
        public PersonalMain[] newArray(int size) {
            return new PersonalMain[size];
        }
    };
}
