package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 收藏实体
 * Created by bitch-1 on 2016/6/2.
 */
public class CollectionBean implements Parcelable {

    //{"id":"b3151013339f44eb9b5b9405e21f7909",
    // "userId":"8db96198286811e69615eca86ba4ba05",
    // "labelId":"b16b7bcd23e211e69615eca86ba4ba05",
    // "activityTheme":"生日聚会",
    // "activitySite":"家里",
    // "activityParticulars":null,
    // "startTime":1465208112000,
    // "endTime":1465972080000,
    // "inviteNumber":10,
    // "activityVisibility":1,
    // "followNum":null,
    // "memberNum":null,
    // "isValid":true,
    // "icon":"img/test.jpg",
    // "createTime":1465194530000,
    // "createPersonId":"820106eb26fb11e69615eca86ba4ba05",
    // "modifyPersonId":null,
    // "modifyTime":1465194530000,
    // "remark":null,
    // "userNm":null,
    // "userIcon":null,
    // "userSex":null,
    // "follow":true,
    // "join":false,
    // "colleagues":false,
    // "friend":false,
    // "joinCount":null,
    // "noticeUserIds":null,
    // "invisibleUserIds":null}

    private String	id;//'主键ID',
    private String	userId ;//'用户ID',
    private String	labelId;// '活动标签ID',
    private String	activityTheme;//'活动主题',
    private String	activitySite ;//'活动地点',
    private String	activityParticulars ;//'活动详情',
    private String	startTime;// '活动开始时间',
    private String	endTime ;//'活动结束时间',
    private Integer inviteNumber;// '邀约人数',
    private Integer activityVisibility;//'可见类型（默认为1）0全部可见1好友可见',
    private Integer	 followNum;// '关注数',
    private Integer memberNum ;//'成员数量',
    private Boolean	 isValid ;//'是否有效.0:无效;1:有效',
    private String	icon;// '详情ICON',
    private String	createTime;// '创建时间',
    private String	createPersonId ;//'创建人',
    private String	modifyPersonId;// '修改人',
    private String  modifyTime;//修改时间
    private String	remark ;//'备注',
    private String userNm;//用户姓名
    private String userIcon;//用户头像
    private String userSex;//用户性别；
    private boolean follow;//是否关注；
    private boolean joining;//是否参加；
    private boolean colleagues;//是否同事
    private boolean friend;//false 是否好友
    private Integer joinCount;//一起活动次数
    private String noticeUserIds;//指定谁看；“aa,bb,cc”
    private String invisibleUserIds;//不让谁看


    private String	timeLength;// '时限(仅群有此字段,活动时为NULL)',
    private Boolean	 type ;//'类型(默认0:活动,1:群)',

    public boolean isJoining() {
        return joining;
    }

    public void setJoining(boolean joining) {
        this.joining = joining;
    }

    public static Creator<CollectionBean> getCREATOR() {
        return CREATOR;
    }

    public CollectionBean() {
    }

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

    public Integer getActivityVisibility() {
        return activityVisibility;
    }

    public void setActivityVisibility(Integer activityVisibility) {
        this.activityVisibility = activityVisibility;
    }

    public boolean isColleagues() {
        return colleagues;
    }

    public void setColleagues(boolean colleagues) {
        this.colleagues = colleagues;
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

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
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

    public String getInvisibleUserIds() {
        return invisibleUserIds;
    }

    public void setInvisibleUserIds(String invisibleUserIds) {
        this.invisibleUserIds = invisibleUserIds;
    }

    public Integer getInviteNumber() {
        return inviteNumber;
    }

    public void setInviteNumber(Integer inviteNumber) {
        this.inviteNumber = inviteNumber;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getNoticeUserIds() {
        return noticeUserIds;
    }

    public void setNoticeUserIds(String noticeUserIds) {
        this.noticeUserIds = noticeUserIds;
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
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

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.labelId);
        dest.writeString(this.activityTheme);
        dest.writeString(this.activitySite);
        dest.writeString(this.activityParticulars);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeValue(this.inviteNumber);
        dest.writeValue(this.activityVisibility);
        dest.writeValue(this.followNum);
        dest.writeValue(this.memberNum);
        dest.writeValue(this.isValid);
        dest.writeString(this.icon);
        dest.writeString(this.createTime);
        dest.writeString(this.createPersonId);
        dest.writeString(this.modifyPersonId);
        dest.writeString(this.modifyTime);
        dest.writeString(this.remark);
        dest.writeString(this.userNm);
        dest.writeString(this.userIcon);
        dest.writeString(this.userSex);
        dest.writeByte(this.follow ? (byte) 1 : (byte) 0);
        dest.writeByte(this.joining ? (byte) 1 : (byte) 0);
        dest.writeByte(this.colleagues ? (byte) 1 : (byte) 0);
        dest.writeByte(this.friend ? (byte) 1 : (byte) 0);
        dest.writeValue(this.joinCount);
        dest.writeString(this.noticeUserIds);
        dest.writeString(this.invisibleUserIds);
        dest.writeString(this.timeLength);
        dest.writeValue(this.type);
    }

    protected CollectionBean(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.labelId = in.readString();
        this.activityTheme = in.readString();
        this.activitySite = in.readString();
        this.activityParticulars = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.inviteNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.activityVisibility = (Integer) in.readValue(Integer.class.getClassLoader());
        this.followNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.memberNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isValid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.icon = in.readString();
        this.createTime = in.readString();
        this.createPersonId = in.readString();
        this.modifyPersonId = in.readString();
        this.modifyTime = in.readString();
        this.remark = in.readString();
        this.userNm = in.readString();
        this.userIcon = in.readString();
        this.userSex = in.readString();
        this.follow = in.readByte() != 0;
        this.joining = in.readByte() != 0;
        this.colleagues = in.readByte() != 0;
        this.friend = in.readByte() != 0;
        this.joinCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.noticeUserIds = in.readString();
        this.invisibleUserIds = in.readString();
        this.timeLength = in.readString();
        this.type = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<CollectionBean> CREATOR = new Creator<CollectionBean>() {
        @Override
        public CollectionBean createFromParcel(Parcel source) {
            return new CollectionBean(source);
        }

        @Override
        public CollectionBean[] newArray(int size) {
            return new CollectionBean[size];
        }
    };
}
