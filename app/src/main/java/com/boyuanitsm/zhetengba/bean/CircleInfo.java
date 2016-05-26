package com.boyuanitsm.zhetengba.bean;

/**
 * 圈子消息
 * Created by xiaoke on 2016/5/26.
 */
public class CircleInfo {
    private  int type;//布局类型1,回复，赞，2，接受，拒绝，3，同意，拒绝提示
    private int state;//1.回复，2，赞
    private int state2;//1,同意，2，拒绝提示


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState2(int state2) {
        this.state2 = state2;
    }

    public int getState2() {
        return state2;
    }
}
