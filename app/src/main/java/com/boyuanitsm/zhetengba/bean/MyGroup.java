package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiaoke on 2016/10/20.
 */
public class MyGroup implements Parcelable {
    private String groupid;
    private String groupname;


    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupid);
        dest.writeString(this.groupname);
    }

    public MyGroup() {
    }

    protected MyGroup(Parcel in) {
        this.groupid = in.readString();
        this.groupname = in.readString();
    }

    public static final Creator<MyGroup> CREATOR = new Creator<MyGroup>() {
        @Override
        public MyGroup createFromParcel(Parcel source) {
            return new MyGroup(source);
        }

        @Override
        public MyGroup[] newArray(int size) {
            return new MyGroup[size];
        }
    };
}
