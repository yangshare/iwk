package com.iwk.yang.bean;

import java.io.Serializable;

public class Person implements Serializable{
    // Fields

    private Integer id;// 编号
    private String pic;// 封面
    private String title;// 视频标题
    private String introduce;// 视频简介
    private Integer clicks;// 点击率
    private String author;// 作者
    private String time;// 视频上传时间
    private String types;// 视频类别

    private String srcs;
    private String objId;
    private String isShow;//是否上架展示0否，1是





    // Property accessors

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getClicks() {
        return this.clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypes() {
        return this.types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getSrcs() {
        return this.srcs;
    }

    public void setSrcs(String srcs) {
        this.srcs = srcs;
    }

    public String getObjId() {
        return this.objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

}
