package com.boyuanitsm.zhetengba.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 兴趣/档期/活动标签
 * Created by xiaoke on 2016/6/1.
 */
public class LabelBannerInfo implements Parcelable{

    private String dictCode;//编码方式
    private String dictDescribe;//描述
    private String dictName;//名称
    private String dictType;//类型
    private String icon;//图标
    private String id;//主键id
    private boolean isValid ;

//    public LabelBannerInfo(String labelid, String dictName, String icon, String dictType, String dictDescribe, String dictCode) {
//        this.id=labelid;
//        this.dictName=dictName;
//        this.icon=icon;
//        this.dictType=dictType;
//        this.dictDescribe=dictDescribe;
//        this.dictCode=dictCode;
//    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictDescribe() {
        return dictDescribe;
    }

    public void setDictDescribe(String dictDescribe) {
        this.dictDescribe = dictDescribe;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "LabelBannerInfo{" +
                "dictCode='" + dictCode + '\'' +
                ", dictDescribe='" + dictDescribe + '\'' +
                ", dictName='" + dictName + '\'' +
                ", dictType='" + dictType + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", isValid=" + isValid +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dictCode);
        dest.writeString(this.dictDescribe);
        dest.writeString(this.dictName);
        dest.writeString(this.dictType);
        dest.writeString(this.icon);
        dest.writeString(this.id);
        dest.writeByte(this.isValid ? (byte) 1 : (byte) 0);
    }

    public LabelBannerInfo() {
    }

    protected LabelBannerInfo(Parcel in) {
        this.dictCode = in.readString();
        this.dictDescribe = in.readString();
        this.dictName = in.readString();
        this.dictType = in.readString();
        this.icon = in.readString();
        this.id = in.readString();
        this.isValid = in.readByte() != 0;
    }

    public static final Creator<LabelBannerInfo> CREATOR = new Creator<LabelBannerInfo>() {
        @Override
        public LabelBannerInfo createFromParcel(Parcel source) {
            return new LabelBannerInfo(source);
        }

        @Override
        public LabelBannerInfo[] newArray(int size) {
            return new LabelBannerInfo[size];
        }
    };
}
