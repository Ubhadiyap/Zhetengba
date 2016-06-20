package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户好友实体
 * Created by wangbin on 16/6/16.
 */
public class FriendsBean implements Parcelable {

    private String id;
    private String username;
    private String name;
    private String phone;
    private String birthday;
    private String sex;
    private String petName;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public FriendsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.birthday);
        dest.writeString(this.sex);
        dest.writeString(this.petName);
        dest.writeString(this.icon);
    }

    protected FriendsBean(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.birthday = in.readString();
        this.sex = in.readString();
        this.petName = in.readString();
        this.icon = in.readString();
    }

    public static final Creator<FriendsBean> CREATOR = new Creator<FriendsBean>() {
        public FriendsBean createFromParcel(Parcel source) {
            return new FriendsBean(source);
        }

        public FriendsBean[] newArray(int size) {
            return new FriendsBean[size];
        }
    };
}
