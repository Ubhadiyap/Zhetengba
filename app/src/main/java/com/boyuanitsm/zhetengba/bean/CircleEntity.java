package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


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
    private int isInCircle;//是否在圈子里面，后面才加的字段
    private List<CircleEntity> commentsList;//评论列表
    private String circleTalkId;

    /**
     * 被回复者的ID
     */
    private String commentedUserId;
    /**
     * 被回复者的昵称
     */
    private String commentedUsername;
    /**
     * 子回复列表
     */
    private List<CircleEntity> childCommentsList;


    public String getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(String commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public String getCommentedUsername() {
        return commentedUsername;
    }

    public void setCommentedUsername(String commentedUsername) {
        this.commentedUsername = commentedUsername;
    }

    public List<CircleEntity> getChildCommentsList() {
        return childCommentsList;
    }

    public void setChildCommentsList(List<CircleEntity> childCommentsList) {
        this.childCommentsList = childCommentsList;
    }

    public String getCircleTalkId() {
        return circleTalkId;
    }


    public void setCircleTalkId(String circleTalkId) {
        this.circleTalkId = circleTalkId;
    }
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
    private String petName;
    private boolean isValid;

    public List<CircleEntity> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CircleEntity> commentsList) {
        this.commentsList = commentsList;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public static Creator<CircleEntity> getCREATOR() {
        return CREATOR;
    }

    public CircleEntity() {
    }

    public int getIsInCircle() {
        return isInCircle;
    }

    public void setIsInCircle(int isInCircle) {
        this.isInCircle = isInCircle;
    }

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
        dest.writeString(this.address);
        dest.writeString(this.circleLogo);
        dest.writeString(this.circleName);
        dest.writeString(this.circleOwnerId);
        dest.writeString(this.circleType);
        dest.writeInt(this.commentCounts);
        dest.writeString(this.createTime);
        dest.writeString(this.id);
        dest.writeInt(this.likedCounts);
        dest.writeInt(this.memberCounts);
        dest.writeString(this.modifyTime);
        dest.writeString(this.notice);
        dest.writeInt(this.sharedCounts);
        dest.writeString(this.userName);
        dest.writeString(this.userId);
        dest.writeString(this.talkContent);
        dest.writeString(this.talkImage);
        dest.writeInt(this.isInCircle);
        dest.writeTypedList(this.commentsList);
        dest.writeString(this.circleTalkId);
        dest.writeString(this.commentedUserId);
        dest.writeString(this.commentedUsername);
        dest.writeTypedList(this.childCommentsList);
        dest.writeString(this.userIcon);
        dest.writeString(this.userSex);
        dest.writeInt(this.liked);
        dest.writeString(this.fatherCommentId);
        dest.writeString(this.circleId);
        dest.writeString(this.commentUserId);
        dest.writeString(this.commentTime);
        dest.writeString(this.commentContent);
        dest.writeString(this.modifyCommentUserId);
        dest.writeString(this.modifyCommentTime);
        dest.writeString(this.deleteCommentUserId);
        dest.writeString(this.deleteCommentTime);
        dest.writeString(this.remark);
        dest.writeString(this.petName);
        dest.writeByte(this.isValid ? (byte) 1 : (byte) 0);
    }

    protected CircleEntity(Parcel in) {
        this.address = in.readString();
        this.circleLogo = in.readString();
        this.circleName = in.readString();
        this.circleOwnerId = in.readString();
        this.circleType = in.readString();
        this.commentCounts = in.readInt();
        this.createTime = in.readString();
        this.id = in.readString();
        this.likedCounts = in.readInt();
        this.memberCounts = in.readInt();
        this.modifyTime = in.readString();
        this.notice = in.readString();
        this.sharedCounts = in.readInt();
        this.userName = in.readString();
        this.userId = in.readString();
        this.talkContent = in.readString();
        this.talkImage = in.readString();
        this.isInCircle = in.readInt();
        this.commentsList = in.createTypedArrayList(CircleEntity.CREATOR);
        this.circleTalkId = in.readString();
        this.commentedUserId = in.readString();
        this.commentedUsername = in.readString();
        this.childCommentsList = in.createTypedArrayList(CircleEntity.CREATOR);
        this.userIcon = in.readString();
        this.userSex = in.readString();
        this.liked = in.readInt();
        this.fatherCommentId = in.readString();
        this.circleId = in.readString();
        this.commentUserId = in.readString();
        this.commentTime = in.readString();
        this.commentContent = in.readString();
        this.modifyCommentUserId = in.readString();
        this.modifyCommentTime = in.readString();
        this.deleteCommentUserId = in.readString();
        this.deleteCommentTime = in.readString();
        this.remark = in.readString();
        this.petName = in.readString();
        this.isValid = in.readByte() != 0;
    }

    public static final Creator<CircleEntity> CREATOR = new Creator<CircleEntity>() {
        @Override
        public CircleEntity createFromParcel(Parcel source) {
            return new CircleEntity(source);
        }

        @Override
        public CircleEntity[] newArray(int size) {
            return new CircleEntity[size];
        }
    };
}
