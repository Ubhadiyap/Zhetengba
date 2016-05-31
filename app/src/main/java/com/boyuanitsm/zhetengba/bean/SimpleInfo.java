package com.boyuanitsm.zhetengba.bean;

/**
 * Created by xiaoke on 2016/5/6.
 */
public class SimpleInfo {

    //关注数量
    public int attentionNum;
    //是否关注
    public boolean isAttention;
    //头像地址
    public String headIconUlr;
    //昵称
    public String nicName;
    //性别
    public boolean male;
    //主题名称
    public String themeName;
    //地点
    public String location;
    //时间
    public String stime;
    //总人数
    public int totalNum;
    //已参加人数
    public int joinNum;
    //是否已关注
    public boolean isJoin;
    //

    public String getHeadIconUlr() {
        return headIconUlr;
    }

    public void setHeadIconUlr(String headIconUlr) {
        this.headIconUlr = headIconUlr;
    }

    public String getNicName() {
        return nicName;
    }

    public void setNicName(String nicName) {
        this.nicName = nicName;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(int joinNum) {
        this.joinNum = joinNum;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public void setIsJoin(boolean isJoin) {
        this.isJoin = isJoin;
    }

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setIsAttention(boolean isAttention) {
        this.isAttention = isAttention;
    }
}
