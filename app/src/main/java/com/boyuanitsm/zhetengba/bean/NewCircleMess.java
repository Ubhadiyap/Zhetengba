package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * Created by xiaoke on 2016/9/19.
 */
@Table(name = "newmess_table")
public class NewCircleMess implements Parcelable {
    @Column
    private String id;
    @Column
    private boolean isMain;//主页面已读
    @Column
    private boolean isCircle;//圈子frg已读
    @Column
    private boolean isMess;//pp消息已读

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Creator<NewCircleMess> getCREATOR() {
        return CREATOR;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public boolean isMess() {
        return isMess;
    }

    public void setIsMess(boolean isMess) {
        this.isMess = isMess;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeByte(this.isMain ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCircle ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMess ? (byte) 1 : (byte) 0);
    }

    public NewCircleMess() {
    }

    protected NewCircleMess(Parcel in) {
        this.id = in.readString();
        this.isMain = in.readByte() != 0;
        this.isCircle = in.readByte() != 0;
        this.isMess = in.readByte() != 0;
    }

    public static final Creator<NewCircleMess> CREATOR = new Creator<NewCircleMess>() {
        @Override
        public NewCircleMess createFromParcel(Parcel source) {
            return new NewCircleMess(source);
        }

        @Override
        public NewCircleMess[] newArray(int size) {
            return new NewCircleMess[size];
        }
    };
}
