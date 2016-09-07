package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2016/9/7.
 */
public class PrizeBean implements Parcelable {
    private String createTime,id,imgAddr,prizeName;
    private int prizeType;

    protected PrizeBean(Parcel in) {
        createTime = in.readString();
        id = in.readString();
        imgAddr = in.readString();
        prizeName = in.readString();
        prizeType = in.readInt();
    }

    public static final Creator<PrizeBean> CREATOR = new Creator<PrizeBean>() {
        @Override
        public PrizeBean createFromParcel(Parcel in) {
            return new PrizeBean(in);
        }

        @Override
        public PrizeBean[] newArray(int size) {
            return new PrizeBean[size];
        }
    };

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public int getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(int prizeType) {
        this.prizeType = prizeType;
    }

    public PrizeBean() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createTime);
        dest.writeString(id);
        dest.writeString(imgAddr);
        dest.writeString(prizeName);
        dest.writeInt(prizeType);
    }
}
