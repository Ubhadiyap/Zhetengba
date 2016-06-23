package com.boyuanitsm.zhetengba.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class ImgBean<T> {
    List<T> smallPaths;

    public List<T> getBigImgPaths() {
        return smallPaths;
    }

    public void setBigImgPaths(List<T> smallPaths) {
        this.smallPaths = smallPaths;
    }
}
