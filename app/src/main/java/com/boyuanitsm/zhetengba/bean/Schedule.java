package com.boyuanitsm.zhetengba.bean;

import java.util.Date;

/**
 * 档期
 * Created by xiaoke on 2016/6/1.
 */
public class Schedule {
    private String	ersonId;//创建人
    private Date time;//创建时间
    private Date	endTime ;//档期结束时间',
    private Integer	followNum ;//关注数',
    private Boolean	isValid ;//是否有效.0:无效;1:有效',
    private String	labelId;//标签ID',
    private String	modifyPersonId;//修改人',
    private Date	modifyTime;//修改时间',
    private String	scheduleId;//档期ID',
    private Integer	scheduleVisibility;//可见性（默认为1）1全部可见2好友可见',
    private Date	startTime;//档期开始时间',
    private String	userId;//用户ID',

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getErsonId() {
        return ersonId;
    }

    public void setErsonId(String ersonId) {
        this.ersonId = ersonId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
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

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getScheduleVisibility() {
        return scheduleVisibility;
    }

    public void setScheduleVisibility(Integer scheduleVisibility) {
        this.scheduleVisibility = scheduleVisibility;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
