package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2016/11/17.
 */
public class CityBean implements Parcelable {
    private String cityid;
    public String name;
    public String pinyi;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }

    public CityBean() {
    }

    public CityBean(String cityid, String name, String pinyi) {
        this.cityid = cityid;
        this.name = name;
        this.pinyi = pinyi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityid);
        dest.writeString(this.name);
        dest.writeString(this.pinyi);
    }

    protected CityBean(Parcel in) {
        this.cityid = in.readString();
        this.name = in.readString();
        this.pinyi = in.readString();
    }

    public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel source) {
            return new CityBean(source);
        }

        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };
}
