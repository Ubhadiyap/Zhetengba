package com.boyuanitsm.zhetengba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xiaoke on 2016/10/20.
 */
public class GroupEnty {
    private String action;
    private List entities;
    private List<MyGroup> data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List getEntities() {
        return entities;
    }

    public void setEntities(List entities) {
        this.entities = entities;
    }

    public List<MyGroup> getData() {
        return data;
    }

    public void setData(List<MyGroup> data) {
        this.data = data;
    }
}
