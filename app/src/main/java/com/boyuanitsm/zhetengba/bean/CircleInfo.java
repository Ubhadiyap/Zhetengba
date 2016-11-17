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
    private String id;
    @Column
    private String userId;//用户id主键
    @Column
    private String userIcon;//用户头像
    @Column
    private String petName;//用户昵称
    @Column
    private String  createTime;//创建时间
    @Column
    private String messtype;//1评论了我，赞了我2.邀请，拒绝，接受3.同意拒绝提示，分享了，
    @Column
    private String messageState;//0评论我，1赞我// 0请求加入，1，邀请加入，//  0同意了请求，1，拒绝了请求，2，同意了邀请，3拒绝了邀请
    @Column
    private String circleName;//圈子名称
    @Column
    private String commentContent;//评论内容
    @Column
    private String commentTalk;//评论的说说
    @Column
    private String circleId;//圈子id
    @Column
    private String type;
   @Column
   private int isAgree;//0是默认值，1是已经同意，2是已经拒绝,3是已加入，4是已拒绝
    @Column
    private String msgContent;//消息内容
    @Column
    private String msgState;//消息状态：0:未读, 1:未处理, 2:已处理
    @Column
    private String sender;//发送者id；
    @Column
    private String receiver;//接收者id；
    @Column
    private String handleTime;//处理时间
    @Column
    private String handleResult;//处理结果，0是同意，1是拒绝
    @Column
    private boolean isValid;//是否有效
    @Column
    private String userSex;//用户性别
    @Column
    private String requestToJoin;//0.请求加入，1.邀请加入
    @Column
    private String msgType;//0档期，1，圈子
    @Column
    private String typeId;//活动时，档期id,圈子时，圈子id，
    @Column
    private String circleTalkId;

    public String getCircleTalkId() {
        return circleTalkId;
    }

    public void setCircleTalkId(String circleTalkId) {
        this.circleTalkId = circleTalkId;
    }

    @Override
    public String toString() {
        return "CircleInfo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", petName='" + petName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", messtype='" + messtype + '\'' +
                ", messageState='" + messageState + '\'' +
                ", circleName='" + circleName + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentTalk='" + commentTalk + '\'' +
                ", circleId='" + circleId + '\'' +
                ", type='" + type + '\'' +
                ", isAgree=" + isAgree +
                ", msgContent='" + msgContent + '\'' +
                ", msgState='" + msgState + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", handleResult='" + handleResult + '\'' +
                ", isValid=" + isValid +
                ", userSex='" + userSex + '\'' +
                ", requestToJoin='" + requestToJoin + '\'' +
                ", msgType='" + msgType + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgState() {
        return msgState;
    }

    public void setMsgState(String msgState) {
        this.msgState = msgState;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getRequestToJoin() {
        return requestToJoin;
    }

    public void setRequestToJoin(String requestToJoin) {
        this.requestToJoin = requestToJoin;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(int isAgree) {
        this.isAgree = isAgree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public static Creator<CircleInfo> getCREATOR() {
        return CREATOR;
    }

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

    public String getMesstype() {
        return messtype;
    }

    public void setMesstype(String messtype) {
        this.messtype = messtype;
    }

    public String getMessageState() {
        return messageState;
    }

    public void setMessageState(String messageState) {
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

    public CircleInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.userIcon);
        dest.writeString(this.petName);
        dest.writeString(this.createTime);
        dest.writeString(this.messtype);
        dest.writeString(this.messageState);
        dest.writeString(this.circleName);
        dest.writeString(this.commentContent);
        dest.writeString(this.commentTalk);
        dest.writeString(this.circleId);
        dest.writeString(this.type);
        dest.writeInt(this.isAgree);
        dest.writeString(this.msgContent);
        dest.writeString(this.msgState);
        dest.writeString(this.sender);
        dest.writeString(this.receiver);
        dest.writeString(this.handleTime);
        dest.writeString(this.handleResult);
        dest.writeByte(this.isValid ? (byte) 1 : (byte) 0);
        dest.writeString(this.userSex);
        dest.writeString(this.requestToJoin);
        dest.writeString(this.msgType);
        dest.writeString(this.typeId);
        dest.writeString(this.circleTalkId);
    }

    protected CircleInfo(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.userIcon = in.readString();
        this.petName = in.readString();
        this.createTime = in.readString();
        this.messtype = in.readString();
        this.messageState = in.readString();
        this.circleName = in.readString();
        this.commentContent = in.readString();
        this.commentTalk = in.readString();
        this.circleId = in.readString();
        this.type = in.readString();
        this.isAgree = in.readInt();
        this.msgContent = in.readString();
        this.msgState = in.readString();
        this.sender = in.readString();
        this.receiver = in.readString();
        this.handleTime = in.readString();
        this.handleResult = in.readString();
        this.isValid = in.readByte() != 0;
        this.userSex = in.readString();
        this.requestToJoin = in.readString();
        this.msgType = in.readString();
        this.typeId = in.readString();
        this.circleTalkId = in.readString();
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
