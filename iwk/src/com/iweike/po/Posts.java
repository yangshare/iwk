package com.iweike.po;

/**
 * Posts entity. @author MyEclipse Persistence Tools
 */

public class Posts {

	// Fields

	private Integer id;
	private String title;//标题
	private String time;
	private String contents;
	private String author;
	private String objId;
	private String types;

	private String isShow;// 审核通过
	private String pic;//封面
	private String picHight;//封面高度

	// Property accessors
	
	

	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPicHight() {
		return picHight;
	}

	public void setPicHight(String picHight) {
		this.picHight = picHight;
	}

	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getObjId() {
		return this.objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getTypes() {
		return this.types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

}