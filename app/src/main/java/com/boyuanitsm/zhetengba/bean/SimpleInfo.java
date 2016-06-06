package com.boyuanitsm.zhetengba.bean;

import java.util.Date;

/**
 * Created by xiaoke on 2016/5/6.
 */
public class SimpleInfo {
    private String	activityParticulars ;//'活动详情',
    private String	activitySite ;//'活动地点',
    private String	activityTheme;//'活动主题',
    private Integer activityVisibility;//'可见类型（默认为1）0全部可见1好友可见',
    private String	createPersonId ;//'创建人',
    private String	createTime;// '创建时间',
    private String	endTime ;//'活动结束时间',
    private Integer	 followNum;// '关注数',
    private String	icon;// '详情ICON',
    private String	id;//'主键ID',
    private Integer inviteNumber;// '邀约人数',
    private Boolean	 isValid ;//'是否有效.0:无效;1:有效',
    private String	labelId;// '活动标签ID',
    private Integer memberNum ;//'成员数量',
    private String	modifyPersonId;// '修改人',
    private String	modifyTimeCOMMENT;// '修改时间',
    private String	remark ;//'备注',
    private String	startTime;// '活动开始时间',
    private String	timeLength;// '时限(仅群有此字段,活动时为NULL)',
    private Boolean	 type ;//'类型(默认0:活动,1:群)',
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

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
//    //id(主键）
//    public String id;
//    //用户id
//    public String userId;
//    //    活动标签
//    public String labelId;
//    //主题名称
//    public String activityTheme;
//    //地点
//    public String activitySite;
//    //活动详情
//    public String activityDetial;
//    //开始时间
//    public Date startTime;
//    //结束时间
//    public Date endTime;
//    //邀约人数
//    public int inviteNumber;
//
//    //可见类型：0全部可见；1好友可见
//    public int activityVisibility;
//    //关注数量
//    public int followNum;
//    //是否有效：0无效；1有效
//    public boolean isVable;
//    //详情icon
//    public String icon;
//    //类型：0活动；1群；
//    public Boolean type;
//
//    //已参加人数
//    public int memberNum;
//    //创建时间
//    public Date creatTime;
//    //创建人ID
//    public String creatPersonId;
//    //修改人
//    public String modifyPersonId;
//    //修改时间
//    public Date modifyTime;
//    //备注
//    public String remark;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getLabelId() {
//        return labelId;
//    }
//
//    public void setLabelId(String labelId) {
//        this.labelId = labelId;
//    }
//
//    public String getActivityTheme() {
//        return activityTheme;
//    }
//
//    public void setActivityTheme(String activityTheme) {
//        this.activityTheme = activityTheme;
//    }
//
//    public String getActivitySite() {
//        return activitySite;
//    }
//
//    public void setActivitySite(String activitySite) {
//        this.activitySite = activitySite;
//    }
//
//    public String getActivityDetial() {
//        return activityDetial;
//    }
//
//    public void setActivityDetial(String activityDetial) {
//        this.activityDetial = activityDetial;
//    }
//
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }
//
//    public int getInviteNumber() {
//        return inviteNumber;
//    }
//
//    public void setInviteNumber(int inviteNumber) {
//        this.inviteNumber = inviteNumber;
//    }
//
//    public int getActivityVisibility() {
//        return activityVisibility;
//    }
//
//    public void setActivityVisibility(int activityVisibility) {
//        this.activityVisibility = activityVisibility;
//    }
//
//    public int getFollowNum() {
//        return followNum;
//    }
//
//    public void setFollowNum(int followNum) {
//        this.followNum = followNum;
//    }
//
//    public boolean isVable() {
//        return isVable;
//    }
//
//    public void setIsVable(boolean isVable) {
//        this.isVable = isVable;
//    }
//
//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }
//
//    public Boolean getType() {
//        return type;
//    }
//
//    public void setType(Boolean type) {
//        this.type = type;
//    }
//
//    public int getMemberNum() {
//        return memberNum;
//    }
//
//    public void setMemberNum(int memberNum) {
//        this.memberNum = memberNum;
//    }
//
//    public Date getCreatTime() {
//        return creatTime;
//    }
//
//    public void setCreatTime(Date creatTime) {
//        this.creatTime = creatTime;
//    }
//
//    public String getCreatPersonId() {
//        return creatPersonId;
//    }
//
//    public void setCreatPersonId(String creatPersonId) {
//        this.creatPersonId = creatPersonId;
//    }
//
//    public String getModifyPersonId() {
//        return modifyPersonId;
//    }
//
//    public void setModifyPersonId(String modifyPersonId) {
//        this.modifyPersonId = modifyPersonId;
//    }
//
//    public Date getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(Date modifyTime) {
//        this.modifyTime = modifyTime;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
}
