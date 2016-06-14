package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 圈子实体,圈子说说实体
 * Created by gxy on 2016/6/1.
 */
public class CircleEntity implements Parcelable{
    private String address;//地址
    private String circleLogo;//圈子LOGO
    private String circleName;//圈子名称
    private String circleOwnerId;//圈主
    private String circleType;//圈子类型（0：公共圈，1：生活圈)
    private int	commentCounts;//评论数
    private String createTime;//创建时间
    private String id;//主键ID
    private int	likedCounts;//加赞数
    private int	memberCounts;//成员数
    private String modifyTime;//修改时间
    private String notice;//公告
    private int sharedCounts;//分享数
    private String userName;//圈主别名 (用户姓名)
    private String userId;//用户id
    private String talkContent;//说说内容
    private String talkImage;//说说图片
    /**
     * 用户姓名
     */
//    private String userName;
    /**
     * 圈子名称
     */
//    private String circleName;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 用户性别
     */
    private String userSex;
    /**
     * 是否已点赞
     */
    private int liked;
    //评论
    private String fatherCommentId;

    private String circleId;

    private String commentUserId;

    private String commentTime;

    private String commentContent;

    private String modifyCommentUserId;

    private String modifyCommentTime;

    private String deleteCommentUserId;

    private String deleteCommentTime;

    private String remark;

    private boolean isValid;
    public CircleEntity() {

    }

    protected CircleEntity(Parcel in) {
        address = in.readString();
        circleLogo = in.readString();
        circleName = in.readString();
        circleOwnerId = in.readString();
        circleType = in.readString();
        commentCounts = in.readInt();
        createTime = in.readString();
        id = in.readString();
        likedCounts = in.readInt();
        memberCounts = in.readInt();
        modifyTime = in.readString();
        notice = in.readString();
        sharedCounts = in.readInt();
        userName = in.readString();
        userId = in.readString();
        talkContent = in.readString();
        talkImage = in.readString();
        userIcon = in.readString();
        userSex = in.readString();
        liked = in.readInt();
        fatherCommentId = in.readString();
        circleId = in.readString();
        commentUserId = in.readString();
        commentTime = in.readString();
        commentContent = in.readString();
        modifyCommentUserId = in.readString();
        modifyCommentTime = in.readString();
        deleteCommentUserId = in.readString();
        deleteCommentTime = in.readString();
        remark = in.readString();
        isValid = in.readByte() != 0;
    }

    public static final Creator<CircleEntity> CREATOR = new Creator<CircleEntity>() {
        @Override
        public CircleEntity createFromParcel(Parcel in) {
            return new CircleEntity(in);
        }

        @Override
        public CircleEntity[] newArray(int size) {
            return new CircleEntity[size];
        }
    };

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public String getTalkImage() {
        return talkImage;
    }

    public void setTalkImage(String talkImage) {
        this.talkImage = talkImage;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getModifyCommentUserId() {
        return modifyCommentUserId;
    }

    public void setModifyCommentUserId(String modifyCommentUserId) {
        this.modifyCommentUserId = modifyCommentUserId;
    }

    public String getModifyCommentTime() {
        return modifyCommentTime;
    }

    public void setModifyCommentTime(String modifyCommentTime) {
        this.modifyCommentTime = modifyCommentTime;
    }

    public String getDeleteCommentUserId() {
        return deleteCommentUserId;
    }

    public void setDeleteCommentUserId(String deleteCommentUserId) {
        this.deleteCommentUserId = deleteCommentUserId;
    }

    public String getDeleteCommentTime() {
        return deleteCommentTime;
    }

    public void setDeleteCommentTime(String deleteCommentTime) {
        this.deleteCommentTime = deleteCommentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
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

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getLikedCounts() {
        return likedCounts;
    }

    public void setLikedCounts(int likedCounts) {
        this.likedCounts = likedCounts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(circleLogo);
        dest.writeString(circleName);
        dest.writeString(circleOwnerId);
        dest.writeString(circleType);
        dest.writeInt(commentCounts);
        dest.writeString(createTime);
        dest.writeString(id);
        dest.writeInt(likedCounts);
        dest.writeInt(memberCounts);
        dest.writeString(modifyTime);
        dest.writeString(notice);
        dest.writeInt(sharedCounts);
        dest.writeString(userName);
        dest.writeString(userId);
        dest.writeString(talkContent);
        dest.writeString(talkImage);
        dest.writeString(userIcon);
        dest.writeString(userSex);
        dest.writeInt(liked);
        dest.writeString(fatherCommentId);
        dest.writeString(circleId);
        dest.writeString(commentUserId);
        dest.writeString(commentTime);
        dest.writeString(commentContent);
        dest.writeString(modifyCommentUserId);
        dest.writeString(modifyCommentTime);
        dest.writeString(deleteCommentUserId);
        dest.writeString(deleteCommentTime);
        dest.writeString(remark);
        dest.writeByte((byte) (isValid ? 1 : 0));
    }
}
