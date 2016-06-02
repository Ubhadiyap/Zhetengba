package com.boyuanitsm.zhetengba.bean;

import java.util.Date;

/**
 * 圈子
 * Created by gxy on 2016/6/1.
 */
public class CircleEntity {
    private String address;//地址
    private String circleLogo;//圈子LOGO
    private String circleName;//圈子名称
    private String circleOwnerId;//圈主
    private String circleType;//圈子类型（0：公共圈，1：生活圈)
    private int	commentCounts;//评论数
    private String createTime;//创建时间
    private String id;//主键ID
    private int	likeCounts;//加赞数
    private int	memberCounts;//成员数
    private String modifyTime;//修改时间
    private String notice;//公告
    private int sharedCounts;//分享数

    public String getCircleOwnerId() {
        return circleOwnerId;
    }

    public void setCircleOwnerId(String circleOwnerId) {
        this.circleOwnerId = circleOwnerId;
    }

    public String getCircleType() {
        return circleType;
    }

    public void setCircleType(String circleType) {
        this.circleType = circleType;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMemberCounts() {
        return memberCounts;
    }

    public void setMemberCounts(int memberCounts) {
        this.memberCounts = memberCounts;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getSharedCounts() {
        return sharedCounts;
    }

    public void setSharedCounts(int sharedCounts) {
        this.sharedCounts = sharedCounts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCircleLogo() {
        return circleLogo;
    }

    public void setCircleLogo(String circleLogo) {
        this.circleLogo = circleLogo;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
}
