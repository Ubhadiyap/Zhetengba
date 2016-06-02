package com.boyuanitsm.zhetengba.bean;

/**
 * 兴趣/档期/活动标签
 * Created by xiaoke on 2016/6/1.
 */
public class LabelBannerInfo {

    private String dictCode;//编码方式
    private String dictDescribe;//描述
    private String dictName;//名称
    private String dictType;//类型
    private String icon;//图标
    private String id;//主键id
    private boolean isValid ;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictDescribe() {
        return dictDescribe;
    }

    public void setDictDescribe(String dictDescribe) {
        this.dictDescribe = dictDescribe;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
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

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "LabelBannerInfo{" +
                "dictCode='" + dictCode + '\'' +
                ", dictDescribe='" + dictDescribe + '\'' +
                ", dictName='" + dictName + '\'' +
                ", dictType='" + dictType + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
