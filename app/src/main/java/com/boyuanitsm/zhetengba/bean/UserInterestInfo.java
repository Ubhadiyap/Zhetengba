package com.boyuanitsm.zhetengba.bean;

/**
 * 个人兴趣标签
 * Created by xiaoke on 2016/6/2.
 */
public class UserInterestInfo {

    private String	createPersonId;//'创建人',
    private String	createTiem;//'创建时间',
    private String	dictName;// 标签名
    private String	id;//'主键ID',
    private String	interestId;// '兴趣标签ID',
    private Boolean	isValid;// '是否有效.0:无效;1:有效',
    private String	modifyPersonId;// '修改人',
    private String modifyTime;// COMMENT '修改时间',
    private String	userId;// '用户ID',

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

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
