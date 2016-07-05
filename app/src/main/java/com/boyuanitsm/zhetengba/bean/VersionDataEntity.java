package com.boyuanitsm.zhetengba.bean;

/**
 * Created by Administrator on 2016/1/26.
 */
public class VersionDataEntity {


    /**
     * isNeedUpdrage : true
     * message : 发现新版本!
     * version : {"id":null,"version":null,"versionName":null,"platform":"ANDROID","packagePath":null,"updrageLog":null,"createPersonId":null,"createTime":null,"modifyPersonId":null,"modifyTime":null,"remark":null}
     */

    private boolean isNeedUpdrage;
    private String message;
    /**
     * id : null
     * version : null
     * versionName : null
     * platform : ANDROID
     * packagePath : null
     * updrageLog : null
     * createPersonId : null
     * createTime : null
     * modifyPersonId : null
     * modifyTime : null
     * remark : null
     */

    private VersionEntity version;

    public void setIsNeedUpdrage(boolean isNeedUpdrage) {
        this.isNeedUpdrage = isNeedUpdrage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVersion(VersionEntity version) {
        this.version = version;
    }

    public boolean isIsNeedUpdrage() {
        return isNeedUpdrage;
    }

    public String getMessage() {
        return message;
    }

    public VersionEntity getVersion() {
        return version;
    }

    public static class VersionEntity {
        private String id;
        private String version;
        private String versionName;
        private String platform;
        private String packagePath;
        private String updrageLog;
        private String createPersonId;
        private Long createTime;
        private String modifyPersonId;
        private String modifyTime;
        private String remark;

        public void setId(String id) {
            this.id = id;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public void setUpdrageLog(String updrageLog) {
            this.updrageLog = updrageLog;
        }

        public void setCreatePersonId(String createPersonId) {
            this.createPersonId = createPersonId;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public void setModifyPersonId(String modifyPersonId) {
            this.modifyPersonId = modifyPersonId;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public String getVersion() {
            return version;
        }

        public String getVersionName() {
            return versionName;
        }

        public String getPlatform() {
            return platform;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public String getUpdrageLog() {
            return updrageLog;
        }

        public String getCreatePersonId() {
            return createPersonId;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public String getModifyPersonId() {
            return modifyPersonId;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public String getRemark() {
            return remark;
        }
    }
}
