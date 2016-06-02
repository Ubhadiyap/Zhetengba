package com.boyuanitsm.zhetengba.bean;

/**
 * Created by bitch-1 on 2016/6/2.
 */
public class DataBean<T> {
    private int tatal;
    private T rows;

    public DataBean() {
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public int getTatal() {
        return tatal;
    }

    public void setTatal(int tatal) {
        this.tatal = tatal;
    }
}
