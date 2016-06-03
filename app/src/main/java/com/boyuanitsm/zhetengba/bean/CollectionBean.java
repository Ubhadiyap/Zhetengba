package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by bitch-1 on 2016/6/2.
 */
public class CollectionBean implements Parcelable {
    private  String CollectionId;
    private  Date CollectionTime;
    private  String CollectionType;
    private  String getId;
    private  String UserId;

    public CollectionBean() {
    }

    protected CollectionBean(Parcel in) {
        CollectionId = in.readString();
        CollectionType = in.readString();
        getId = in.readString();
        UserId = in.readString();
    }

    public static final Creator<CollectionBean> CREATOR = new Creator<CollectionBean>() {
        @Override
        public CollectionBean createFromParcel(Parcel in) {
            return new CollectionBean(in);
        }

        @Override
        public CollectionBean[] newArray(int size) {
            return new CollectionBean[size];
        }
    };

    public String getCollectionId() {
        return CollectionId;
    }

    public void setCollectionId(String collectionId) {
        CollectionId = collectionId;
    }

    public Date getCollectionTime() {
        return CollectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        CollectionTime = collectionTime;
    }

    public String getCollectionType() {
        return CollectionType;
    }

    public void setCollectionType(String collectionType) {
        CollectionType = collectionType;
    }

    public String getGetId() {
        return getId;
    }

    public void setGetId(String getId) {
        this.getId = getId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CollectionId);
        dest.writeString(CollectionType);
        dest.writeString(getId);
        dest.writeString(UserId);
    }
}
