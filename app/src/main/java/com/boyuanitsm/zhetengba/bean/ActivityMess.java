package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * Created by xiaoke on 2016/6/23.
 */
@Table(name="activity_label")
public class ActivityMess implements Parcelable {
    @Column
    private String id;
    @Column
   private  String userId;
    @Column
    private  String  petName;
    @Column
     private String message;//":"加入到活动中
    @Column
    private String type;//0是活动
    @Column
     private String userIcon;//
    @Column
    private String createTime;
    @Column
    private String activityTheme;
    @Column
    private String messtype;
    @Column
    private String activityId;
    @Column
    private String scheduleId;
    @Column
    private int isAgree;//0默认，1同意，2拒绝
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
    private String msgType;
    @Column
    private String typeId;


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getRequestToJoin() {
        return requestToJoin;
    }

    public void setRequestToJoin(String requestToJoin) {
        this.requestToJoin = requestToJoin;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(int isAgree) {
        this.isAgree = isAgree;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getMesstype() {
        return messtype;
    }

    public void setMesstype(String messtype) {
        this.messtype = messtype;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ActivityMess{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", petName='" + petName + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", createTime='" + createTime + '\'' +
                ", activityTheme='" + activityTheme + '\'' +
                ", messtype='" + messtype + '\'' +
                ", activityId='" + activityId + '\'' +
                ", scheduleId='" + scheduleId + '\'' +
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

    public static Creator<ActivityMess> getCREATOR() {
        return CREATOR;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public ActivityMess() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.petName);
        dest.writeString(this.message);
        dest.writeString(this.type);
        dest.writeString(this.userIcon);
        dest.writeString(this.createTime);
        dest.writeString(this.activityTheme);
        dest.writeString(this.messtype);
        dest.writeString(this.activityId);
        dest.writeString(this.scheduleId);
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
    }

    protected ActivityMess(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.petName = in.readString();
        this.message = in.readString();
        this.type = in.readString();
        this.userIcon = in.readString();
        this.createTime = in.readString();
        this.activityTheme = in.readString();
        this.messtype = in.readString();
        this.activityId = in.readString();
        this.scheduleId = in.readString();
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
    }

    public static final Creator<ActivityMess> CREATOR = new Creator<ActivityMess>() {
        @Override
        public ActivityMess createFromParcel(Parcel source) {
            return new ActivityMess(source);
        }

        @Override
        public ActivityMess[] newArray(int size) {
            return new ActivityMess[size];
        }
    };
}
