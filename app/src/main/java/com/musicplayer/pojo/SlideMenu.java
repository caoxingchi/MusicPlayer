package com.musicplayer.pojo;

/**
 * @author: xingchi --->> itcolors <<---
 * @date: 2020/6/12 10:12
 * @version: 1.0
 * 侧边导航栏的item
 */
public class SlideMenu {
    private int resource;
    private String title;

    public SlideMenu() {
        super();
    }

    public SlideMenu(int resource, String title) {
        this.resource = resource;
        this.title = title;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
