package com.boyuanitsm.zhetengba.bean;

import java.util.Date;

/**
 * 所有活动标签
 * Created by xiaoke on 2016/6/1.
 */
public class ActivityLabel {
    private String	createPersonId;//'创建人',
    private String createTime;//'创建时间',
    private String	fatherLabelName;//别名父标签
    private String	icon;// '标签ICON',
    private String	id;// '主键ID',
    private Boolean	 isValid;//'是否有效.0:无效;1:有效',
    private String	labelId;//'父标签ID',
    private String	labelName;//'标签名称',
    private String	modifyPersonId;//'修改人',

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String	modifyTime;// '修改时间',

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }


    public String getFatherLabelName() {
        return fatherLabelName;
    }

    public void setFatherLabelName(String fatherLabelName) {
        this.fatherLabelName = fatherLabelName;
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

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getModifyPersonId() {
        return modifyPersonId;
    }

    public void setModifyPersonId(String modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

}
