package com.boyuanitsm.zhetengba.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class ImgBean<T> {
    List<T> bigImgPaths;

    public List<T> getBigImgPaths() {
        return bigImgPaths;
    }

    public void setBigImgPaths(List<T> bigImgPaths) {
        this.bigImgPaths = bigImgPaths;
    }
}
