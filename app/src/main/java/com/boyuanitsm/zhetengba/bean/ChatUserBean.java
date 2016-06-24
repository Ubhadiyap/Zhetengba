package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * Created by wangbin on 16/6/24.
 */
@Table(name = "chat_user")
public class ChatUserBean implements Parcelable {
    @Column
    private String userId;
    @Column
    private String nick;
    @Column
    private String icon;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.nick);
        dest.writeString(this.icon);
    }

    public ChatUserBean() {
    }

    protected ChatUserBean(Parcel in) {
        this.userId = in.readString();
        this.nick = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<ChatUserBean> CREATOR = new Parcelable.Creator<ChatUserBean>() {
        public ChatUserBean createFromParcel(Parcel source) {
            return new ChatUserBean(source);
        }

        public ChatUserBean[] newArray(int size) {
            return new ChatUserBean[size];
        }
    };
}
