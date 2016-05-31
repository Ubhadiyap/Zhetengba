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
