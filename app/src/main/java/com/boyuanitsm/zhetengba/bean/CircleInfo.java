package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * 圈子消息
 * Created by xiaoke on 2016/5/26.
 */
@Table(name="circle_talbe")
public class CircleInfo implements Parcelable{
    @Column
    private String userId;//用户id主键
    @Column
    private String userIcon;//用户头像
    @Column
    private String petName;//用户昵称
    @Column
    private String  createTime;//创建时间
    @Column
    private int messtype;//1评论了我，赞了我2.邀请，拒绝，接受3.同意拒绝提示，分享了，
    @Column
    private int messageState;//1评论我，2，赞我// 1请求加入，2，邀请加入，//  1同意了请求，2，拒绝了请求，3，分享了圈子
    @Column
    private String circleName;//圈子名称
    @Column
    private String commentContent;//评论内容
    @Column
    private String commentTalk;//评论的说说

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMesstype() {
        return messtype;
    }

    public void setMesstype(int messtype) {
        this.messtype = messtype;
    }

    public int getMessageState() {
        return messageState;
    }

    public void setMessageState(int messageState) {
        this.messageState = messageState;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTalk() {
        return commentTalk;
    }

    public void setCommentTalk(String commentTalk) {
        this.commentTalk = commentTalk;
    }

    public static Creator<CircleInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "CircleInfo{" +
                "userId='" + userId + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", petName='" + petName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", messtype=" + messtype +
                ", messageState=" + messageState +
                ", circleName='" + circleName + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentTalk='" + commentTalk + '\'' +
                '}';
    }


    public CircleInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userIcon);
        dest.writeString(this.petName);
        dest.writeString(this.createTime);
        dest.writeInt(this.messtype);
        dest.writeInt(this.messageState);
        dest.writeString(this.circleName);
        dest.writeString(this.commentContent);
        dest.writeString(this.commentTalk);
    }

    protected CircleInfo(Parcel in) {
        this.userId = in.readString();
        this.userIcon = in.readString();
        this.petName = in.readString();
        this.createTime = in.readString();
        this.messtype = in.readInt();
        this.messageState = in.readInt();
        this.circleName = in.readString();
        this.commentContent = in.readString();
        this.commentTalk = in.readString();
    }

    public static final Creator<CircleInfo> CREATOR = new Creator<CircleInfo>() {
        @Override
        public CircleInfo createFromParcel(Parcel source) {
            return new CircleInfo(source);
        }

        @Override
        public CircleInfo[] newArray(int size) {
            return new CircleInfo[size];
        }
    };
}
