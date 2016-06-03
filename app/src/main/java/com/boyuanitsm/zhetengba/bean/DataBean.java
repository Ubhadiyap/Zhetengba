package com.boyuanitsm.zhetengba.bean;

import java.util.List;

/**
 * Created by bitch-1 on 2016/6/2.
 */
public class DataBean<T> {
    private int tatal;
    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTatal() {
        return tatal;
    }

    public void setTatal(int tatal) {
        this.tatal = tatal;
    }
}
