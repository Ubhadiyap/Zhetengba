package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 频道说说实体
 * Created by gxy on 2016/6/2.
 */
public class ChannelTalkEntity implements Parcelable{
    private String address;//地址
    private int	commentCounts;//评论数
    private String	createPersonId;//创建人
    private String createTiem;//创建时间
    private String	id;//主键ID
    private boolean	isValid;//是否有效.0:无效;1:有效
    private String	labelId;//标签ID
    private int	likeCounts;//加赞数
    private int	sharedCounts;//分享数

    private String channelContent;//频道说说内容
    private String channelImage;//频道图片
    private String remark;//备注
    private String petName;//昵称
    /**
     * 用户姓名
     */
    private String userName;
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
    private String fatherCommentId;//父评论ID

    private String channelTalkId;//频道ID

    private String commentUserId;//评论用户ID

    private String commentTime;//评论时间

    private String commentContent;//评论内容

    private List<ChannelTalkEntity> commentsList;//评论列表

    @Override
    public String toString() {
        return "ChannelTalkEntity{" +
                "commentContent='" + commentContent + '\'' +
                ", address='" + address + '\'' +
                ", commentCounts=" + commentCounts +
                ", createPersonId='" + createPersonId + '\'' +
                ", createTiem='" + createTiem + '\'' +
                ", id='" + id + '\'' +
                ", isValid=" + isValid +
                ", labelId='" + labelId + '\'' +
                ", likeCounts=" + likeCounts +
                ", sharedCounts=" + sharedCounts +
                ", channelContent='" + channelContent + '\'' +
                ", channelImage='" + channelImage + '\'' +
                ", remark='" + remark + '\'' +
                ", petName='" + petName + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userSex='" + userSex + '\'' +
                ", liked=" + liked +
                ", fatherCommentId='" + fatherCommentId + '\'' +
                ", channelTalkId='" + channelTalkId + '\'' +
                ", commentUserId='" + commentUserId + '\'' +
                ", commentTime='" + commentTime + '\'' +
                '}';
    }

    public List<ChannelTalkEntity> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<ChannelTalkEntity> commentsList) {
        this.commentsList = commentsList;
    }

    public static Creator<ChannelTalkEntity> getCREATOR() {
        return CREATOR;
    }

    public ChannelTalkEntity() {
    }


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    public String getChannelTalkId() {
        return channelTalkId;
    }

    public void setChannelTalkId(String channelTalkId) {
        this.channelTalkId = channelTalkId;
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

    public String getChannelContent() {
        return channelContent;
    }

    public void setChannelContent(String channelContent) {
        this.channelContent = channelContent;
    }

    public String getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateTiem() {
        return createTiem;
    }

    public void setCreateTiem(String createTiem) {
        this.createTiem = createTiem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public int getSharedCounts() {
        return sharedCounts;
    }

    public void setSharedCounts(int sharedCounts) {
        this.sharedCounts = sharedCounts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeInt(this.commentCounts);
        dest.writeString(this.createPersonId);
        dest.writeString(this.createTiem);
        dest.writeString(this.id);
        dest.writeByte(this.isValid ? (byte) 1 : (byte) 0);
        dest.writeString(this.labelId);
        dest.writeInt(this.likeCounts);
        dest.writeInt(this.sharedCounts);
        dest.writeString(this.channelContent);
        dest.writeString(this.channelImage);
        dest.writeString(this.remark);
        dest.writeString(this.petName);
        dest.writeString(this.userName);
        dest.writeString(this.userIcon);
        dest.writeString(this.userSex);
        dest.writeInt(this.liked);
        dest.writeString(this.fatherCommentId);
        dest.writeString(this.channelTalkId);
        dest.writeString(this.commentUserId);
        dest.writeString(this.commentTime);
        dest.writeString(this.commentContent);
        dest.writeTypedList(this.commentsList);
    }

    protected ChannelTalkEntity(Parcel in) {
        this.address = in.readString();
        this.commentCounts = in.readInt();
        this.createPersonId = in.readString();
        this.createTiem = in.readString();
        this.id = in.readString();
        this.isValid = in.readByte() != 0;
        this.labelId = in.readString();
        this.likeCounts = in.readInt();
        this.sharedCounts = in.readInt();
        this.channelContent = in.readString();
        this.channelImage = in.readString();
        this.remark = in.readString();
        this.petName = in.readString();
        this.userName = in.readString();
        this.userIcon = in.readString();
        this.userSex = in.readString();
        this.liked = in.readInt();
        this.fatherCommentId = in.readString();
        this.channelTalkId = in.readString();
        this.commentUserId = in.readString();
        this.commentTime = in.readString();
        this.commentContent = in.readString();
        this.commentsList = in.createTypedArrayList(ChannelTalkEntity.CREATOR);
    }

    public static final Creator<ChannelTalkEntity> CREATOR = new Creator<ChannelTalkEntity>() {
        @Override
        public ChannelTalkEntity createFromParcel(Parcel source) {
            return new ChannelTalkEntity(source);
        }

        @Override
        public ChannelTalkEntity[] newArray(int size) {
            return new ChannelTalkEntity[size];
        }
    };
}
