package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2016/9/2.
 */
public class ImagCatchBean implements Parcelable {
    private String zifu;
    private String imgpath;

    protected ImagCatchBean(Parcel in) {
        zifu = in.readString();
        imgpath = in.readString();
    }

    public static final Creator<ImagCatchBean> CREATOR = new Creator<ImagCatchBean>() {
        @Override
        public ImagCatchBean createFromParcel(Parcel in) {
            return new ImagCatchBean(in);
        }

        @Override
        public ImagCatchBean[] newArray(int size) {
            return new ImagCatchBean[size];
        }
    };

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getZifu() {
        return zifu;
    }

    public void setZifu(String zifu) {
        this.zifu = zifu;
    }

    public ImagCatchBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(zifu);
        dest.writeString(imgpath);
    }
}
