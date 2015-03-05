package com.beanu.arad.demo.support.event;

/**
 * 首页导航点击事件
 * Created by beanu on 15/2/13.
 */
public class NavEvent {

    String tag;

    public NavEvent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
