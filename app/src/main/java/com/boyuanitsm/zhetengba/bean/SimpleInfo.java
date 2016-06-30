package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by xiaoke on 2016/5/6.
 */
public class SimpleInfo implements Parcelable{
    private String	activityParticulars ;//'活动详情',
    private String	activitySite ;//'活动地点',
    private String	activityTheme;//'活动主题',
    private int activityVisibility;//'可见类型（默认为1）0全部可见1好友可见',
    private String	createPersonId ;//'创建人',
    private String	createTime;// '创建时间',
    private String	endTime ;//'活动结束时间',
    private int	 followNum;// '关注数',
    private String	icon;// '详情ICON',
    private String	id;//'主键ID',
    private int inviteNumber;// '邀约人数',
    private boolean	 isValid ;//'是否有效.0:无效;1:有效',
    private String	labelId;// '活动标签ID',
    private int memberNum ;//'成员数量',
    private String	modifyPersonId;// '修改人',
    private String	modifyTimeCOMMENT;// '修改时间',
    private String	remark ;//'备注',
    private String	startTime;// '活动开始时间',
    private String	timeLength;// '时限(仅群有此字段,活动时为NULL)',
    private boolean	 type ;//'类型(默认0:活动,1:群)',
    private String	userId ;//'用户ID',
    private String userNm;//用户姓名
    private String userIcon;//用户头像
    private String userSex;//用户性别；
    private boolean follow;//是否关注；
    private boolean join;//是否参加；
    private String noticeUserIds;//指定谁看；“aa,bb,cc”
    private String invisibleUserIds;//不让谁看
    private boolean colleagues;//是否同事
    private boolean friend;//false 是否好友
    private int joinCount;//一起参加次数

    public String getActivityParticulars() {
        return activityParticulars;
    }

    public void setActivityParticulars(String activityParticulars) {
        this.activityParticulars = activityParticulars;
    }

    public String getActivitySite() {
        return activitySite;
    }

    public void setActivitySite(String activitySite) {
        this.activitySite = activitySite;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme;
    }

    public int getActivityVisibility() {
        return activityVisibility;
    }

    public void setActivityVisibility(int activityVisibility) {
        this.activityVisibility = activityVisibility;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
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

    public int getInviteNumber() {
        return inviteNumber;
    }

    public void setInviteNumber(int inviteNumber) {
        this.inviteNumber = inviteNumber;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getModifyTimeCOMMENT() {
        return modifyTimeCOMMENT;
    }

    public void setModifyTimeCOMMENT(String modifyTimeCOMMENT) {
        this.modifyTimeCOMMENT = modifyTimeCOMMENT;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public String getNoticeUserIds() {
        return noticeUserIds;
    }

    public void setNoticeUserIds(String noticeUserIds) {
        this.noticeUserIds = noticeUserIds;
    }

    public String getInvisibleUserIds() {
        return invisibleUserIds;
    }

    public void setInvisibleUserIds(String invisibleUserIds) {
        this.invisibleUserIds = invisibleUserIds;
    }

    public boolean isColleagues() {
        return colleagues;
    }

    public void setColleagues(boolean colleagues) {
        this.colleagues = colleagues;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityParticulars);
        dest.writeString(this.activitySite);
        dest.writeString(this.activityTheme);
        dest.writeInt(this.activityVisibility);
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTime);
        dest.writeString(this.endTime);
        dest.writeInt(this.followNum);
        dest.writeString(this.icon);
        dest.writeString(this.id);
        dest.writeInt(this.inviteNumber);
        dest.writeByte(this.isValid ? (byte) 1 : (byte) 0);
        dest.writeString(this.labelId);
        dest.writeInt(this.memberNum);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTimeCOMMENT);
        dest.writeString(this.remark);
        dest.writeString(this.startTime);
        dest.writeString(this.timeLength);
        dest.writeByte(this.type ? (byte) 1 : (byte) 0);
        dest.writeString(this.userId);
        dest.writeString(this.userNm);
        dest.writeString(this.userIcon);
        dest.writeString(this.userSex);
        dest.writeByte(this.follow ? (byte) 1 : (byte) 0);
        dest.writeByte(this.join ? (byte) 1 : (byte) 0);
        dest.writeString(this.noticeUserIds);
        dest.writeString(this.invisibleUserIds);
        dest.writeByte(this.colleagues ? (byte) 1 : (byte) 0);
        dest.writeByte(this.friend ? (byte) 1 : (byte) 0);
        dest.writeInt(this.joinCount);
    }

    public SimpleInfo() {
    }

    protected SimpleInfo(Parcel in) {
        this.activityParticulars = in.readString();
        this.activitySite = in.readString();
        this.activityTheme = in.readString();
        this.activityVisibility = in.readInt();
        this.createPersonId = in.readString();
        this.createTime = in.readString();
        this.endTime = in.readString();
        this.followNum = in.readInt();
        this.icon = in.readString();
        this.id = in.readString();
        this.inviteNumber = in.readInt();
        this.isValid = in.readByte() != 0;
        this.labelId = in.readString();
        this.memberNum = in.readInt();
        this.modifyPersonId = in.readString();
        this.modifyTimeCOMMENT = in.readString();
        this.remark = in.readString();
        this.startTime = in.readString();
        this.timeLength = in.readString();
        this.type = in.readByte() != 0;
        this.userId = in.readString();
        this.userNm = in.readString();
        this.userIcon = in.readString();
        this.userSex = in.readString();
        this.follow = in.readByte() != 0;
        this.join = in.readByte() != 0;
        this.noticeUserIds = in.readString();
        this.invisibleUserIds = in.readString();
        this.colleagues = in.readByte() != 0;
        this.friend = in.readByte() != 0;
        this.joinCount = in.readInt();
    }

    public static final Creator<SimpleInfo> CREATOR = new Creator<SimpleInfo>() {
        @Override
        public SimpleInfo createFromParcel(Parcel source) {
            return new SimpleInfo(source);
        }

        @Override
        public SimpleInfo[] newArray(int size) {
            return new SimpleInfo[size];
        }
    };
}
