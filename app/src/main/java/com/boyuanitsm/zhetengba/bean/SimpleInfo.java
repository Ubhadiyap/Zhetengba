package com.boyuanitsm.zhetengba.bean;

import java.util.Date;

/**
 * Created by xiaoke on 2016/5/6.
 */
public class SimpleInfo {
    //id(主键）
    public String id;
    //用户id
    public String userId;
    //    活动标签
    public String labelId;
    //主题名称
    public String activityTheme;
    //地点
    public String activitySite;
    //活动详情
    public String activityDetial;
    //开始时间
    public Date startTime;
    //结束时间
    public Date endTime;
    //邀约人数
    public int inviteNumber;

    //可见类型：0全部可见；1好友可见
    public int activityVisibility;
    //关注数量
    public int followNum;
    //是否有效：0无效；1有效
    public boolean isVable;
    //详情icon
    public String icon;
    //类型：0活动；1群；
    public Boolean type;

    //已参加人数
    public int memberNum;
    //创建时间
    public Date creatTime;
    //创建人ID
    public String creatPersonId;
    //修改人
    public String modifyPersonId;
    //修改时间
    public Date modifyTime;
    //备注
    public String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme;
    }

    public String getActivitySite() {
        return activitySite;
    }

    public void setActivitySite(String activitySite) {
        this.activitySite = activitySite;
    }

    public String getActivityDetial() {
        return activityDetial;
    }

    public void setActivityDetial(String activityDetial) {
        this.activityDetial = activityDetial;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getInviteNumber() {
        return inviteNumber;
    }

    public void setInviteNumber(int inviteNumber) {
        this.inviteNumber = inviteNumber;
    }

    public int getActivityVisibility() {
        return activityVisibility;
    }

    public void setActivityVisibility(int activityVisibility) {
        this.activityVisibility = activityVisibility;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public boolean isVable() {
        return isVable;
    }

    public void setIsVable(boolean isVable) {
        this.isVable = isVable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getCreatPersonId() {
        return creatPersonId;
    }

    public void setCreatPersonId(String creatPersonId) {
        this.creatPersonId = creatPersonId;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
