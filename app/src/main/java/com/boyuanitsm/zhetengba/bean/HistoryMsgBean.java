package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 时间轴返回实体类
 * Created by bitch-1 on 2016/6/2.
 */
public class HistoryMsgBean implements Parcelable{
    private String createTime;
    private String id;
    private String message;
    private String messageType;
    private String userId;

    protected HistoryMsgBean(Parcel in) {
        createTime = in.readString();
        id = in.readString();
        message = in.readString();
        messageType = in.readString();
        userId = in.readString();
    }

    public static final Creator<HistoryMsgBean> CREATOR = new Creator<HistoryMsgBean>() {
        @Override
        public HistoryMsgBean createFromParcel(Parcel in) {
            return new HistoryMsgBean(in);
        }

        @Override
        public HistoryMsgBean[] newArray(int size) {
            return new HistoryMsgBean[size];
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createTime);
        dest.writeString(id);
        dest.writeString(message);
        dest.writeString(messageType);
        dest.writeString(userId);
    }
}
