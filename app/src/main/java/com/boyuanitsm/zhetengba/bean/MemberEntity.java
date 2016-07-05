package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/** 圈子成员
 * Created by gxy on 2015/11/25.
 */
public class MemberEntity implements Parcelable {

    private String id;//用户id
    private String username;
    private String name;

    private String phone;

    private String icon;

    private String userType;
    private String petName;

    /**---------------------------------------------------------------------------------------------------------*/
    /**
     * 共同的标签
     */
    private String sameLabels;
    /**
     * 共同的圈子数
     */
    private int sameCircleCounts;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public static Creator<MemberEntity> getCREATOR() {
        return CREATOR;
    }

    public MemberEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSameLabels() {
        return sameLabels;
    }

    public void setSameLabels(String sameLabels) {
        this.sameLabels = sameLabels;
    }

    public int getSameCircleCounts() {
        return sameCircleCounts;
    }

    public void setSameCircleCounts(int sameCircleCounts) {
        this.sameCircleCounts = sameCircleCounts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.icon);
        dest.writeString(this.userType);
        dest.writeString(this.petName);
        dest.writeString(this.sameLabels);
        dest.writeInt(this.sameCircleCounts);
    }

    protected MemberEntity(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.icon = in.readString();
        this.userType = in.readString();
        this.petName = in.readString();
        this.sameLabels = in.readString();
        this.sameCircleCounts = in.readInt();
    }

    public static final Creator<MemberEntity> CREATOR = new Creator<MemberEntity>() {
        @Override
        public MemberEntity createFromParcel(Parcel source) {
            return new MemberEntity(source);
        }

        @Override
        public MemberEntity[] newArray(int size) {
            return new MemberEntity[size];
        }
    };
}
