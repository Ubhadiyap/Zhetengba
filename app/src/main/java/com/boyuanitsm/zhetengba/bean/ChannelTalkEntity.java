package com.boyuanitsm.zhetengba.bean;

/**
 * 频道说说实体
 * Created by gxy on 2016/6/2.
 */
public class ChannelTalkEntity {
    private String address;//地址
    private int	commentCounts;//评论数
    private String	createPersonId;//创建人
    private String createTiem;//创建时间
    private String	id;//主键ID
    private boolean	isValid;//是否有效.0:无效;1:有效
    private String	labelId;//标签ID
    private String	likeCounts;//加赞数
    private int	sharedCounts;//分享数

    private String channelContent;
    /**
     * varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '频道图片',
     */
    private String channelImage;


    /**
     * varchar(35) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
     */
    private String remark;

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

    public String getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(String likeCounts) {
        this.likeCounts = likeCounts;
    }

    public int getSharedCounts() {
        return sharedCounts;
    }

    public void setSharedCounts(int sharedCounts) {
        this.sharedCounts = sharedCounts;
    }
}
