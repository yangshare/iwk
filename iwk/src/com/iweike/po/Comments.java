package com.iweike.po;


// default package



/**
 * Comments entity. @author MyEclipse Persistence Tools
 */

public class Comments {


    // Fields    

	private Integer id;// 编号
	private String types;//是視頻還是帖子
	private String time;// 评论时间
	private String contents;// 评论内容
	private String author;// 评论人

	private Integer objId;//评论人id
	private Integer typesId;// 视频编号或帖子编号
	
	private String isShow;//审核通过


    // Property accessors
	
	

    public Integer getId() {
        return this.id;
    }
    
    public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}


    public Integer getObjId() {
        return this.objId;
    }
    
    public void setObjId(Integer objId) {
        this.objId = objId;
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

	public Integer getTypesId() {
		return typesId;
	}

	public void setTypesId(Integer typesId) {
		this.typesId = typesId;
	}

   








}