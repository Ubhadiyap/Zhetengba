package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

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

    public ChannelTalkEntity() {
    }


    protected ChannelTalkEntity(Parcel in) {
        address = in.readString();
        commentCounts = in.readInt();
        createPersonId = in.readString();
        createTiem = in.readString();
        id = in.readString();
        isValid = in.readByte() != 0;
        labelId = in.readString();
        likeCounts = in.readInt();
        sharedCounts = in.readInt();
        channelContent = in.readString();
        channelImage = in.readString();
        remark = in.readString();
        userName = in.readString();
        userIcon = in.readString();
        userSex = in.readString();
        liked = in.readInt();
        fatherCommentId = in.readString();
        channelTalkId = in.readString();
        commentUserId = in.readString();
        commentTime = in.readString();
        commentContent = in.readString();
        petName=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeInt(commentCounts);
        dest.writeString(createPersonId);
        dest.writeString(createTiem);
        dest.writeString(id);
        dest.writeByte((byte) (isValid ? 1 : 0));
        dest.writeString(labelId);
        dest.writeInt(likeCounts);
        dest.writeInt(sharedCounts);
        dest.writeString(channelContent);
        dest.writeString(channelImage);
        dest.writeString(remark);
        dest.writeString(userName);
        dest.writeString(userIcon);
        dest.writeString(userSex);
        dest.writeInt(liked);
        dest.writeString(fatherCommentId);
        dest.writeString(channelTalkId);
        dest.writeString(commentUserId);
        dest.writeString(commentTime);
        dest.writeString(commentContent);
        dest.writeString(petName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChannelTalkEntity> CREATOR = new Creator<ChannelTalkEntity>() {
        @Override
        public ChannelTalkEntity createFromParcel(Parcel in) {
            return new ChannelTalkEntity(in);
        }

        @Override
        public ChannelTalkEntity[] newArray(int size) {
            return new ChannelTalkEntity[size];
        }
    };

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
}
