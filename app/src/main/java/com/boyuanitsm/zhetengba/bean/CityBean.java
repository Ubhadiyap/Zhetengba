package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2016/11/17.
 */
public class CityBean implements Parcelable {

    private String city_id;

    private String city_name;

    private String first_letter;

    private String longitude;//经度

    private String latitude;//纬度

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public CityBean() {
    }

    protected CityBean(Parcel in) {
        city_id = in.readString();
        city_name = in.readString();
        first_letter = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel in) {
            return new CityBean(in);
        }

        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city_id);
        dest.writeString(city_name);
        dest.writeString(first_letter);
        dest.writeString(longitude);
        dest.writeString(latitude);
    }
}
