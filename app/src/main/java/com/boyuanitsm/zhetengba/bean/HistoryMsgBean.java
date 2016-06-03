package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 时间轴返回实体类
 * Created by bitch-1 on 2016/6/2.
 */
public class HistoryMsgBean implements Parcelable{
    private String createtime;
    private String Id;
    private String Message;
    private String MessageType;
    private String UserId;

    public HistoryMsgBean() {
    }

    protected HistoryMsgBean(Parcel in) {
        createtime = in.readString();
        Id = in.readString();
        Message = in.readString();
        MessageType = in.readString();
        UserId = in.readString();
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
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
        dest.writeString(createtime);
        dest.writeString(Id);
        dest.writeString(Message);
        dest.writeString(MessageType);
        dest.writeString(UserId);
    }
}
