package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by bitch-1 on 2016/12/1.
 */

public class SuggestionInfoBean implements Parcelable {
    public String key;
    public String city;
    public String district;
    public LatLng pt;
    public String uid;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public LatLng getPt() {
        return pt;
    }

    public void setPt(LatLng pt) {
        this.pt = pt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SuggestionInfoBean() {
    }

    protected SuggestionInfoBean(Parcel in) {
        key = in.readString();
        city = in.readString();
        district = in.readString();
        pt = in.readParcelable(LatLng.class.getClassLoader());
        uid = in.readString();
    }

    public static final Creator<SuggestionInfoBean> CREATOR = new Creator<SuggestionInfoBean>() {
        @Override
        public SuggestionInfoBean createFromParcel(Parcel in) {
            return new SuggestionInfoBean(in);
        }

        @Override
        public SuggestionInfoBean[] newArray(int size) {
            return new SuggestionInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeParcelable(pt, flags);
        dest.writeString(uid);
    }
}
