package com.resmed.liangj.ocr.bean.httpbean;

import java.util.List;

public class ReportBean {

    /**
     * _id : 5b20a86b421aa97934d42ffd
     * createdAt : 2018-06-14T12:22:32.573Z
     * desc : android图片涂鸦，具有设置画笔，撤销，缩放移动等功能。
     * images : ["http://img.gank.io/e666e3c0-3606-4107-a515-4e3a96a6cfdd","http://img.gank.io/648431b0-10f3-40da-bfc8-dacf2eb34ace","http://img.gank.io/2086cfd0-39f9-4ae3-b32e-5c90e3fefa2a"]
     * publishedAt : 2018-06-14T00:00:00.0Z
     * source : web
     * type : Android
     * url : https://github.com/1993hzw/Graffiti
     * used : true
     * who : joker
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
