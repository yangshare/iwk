package com.iweike.po;
// default package

import java.sql.Timestamp;


/**
 * Posts entity. @author MyEclipse Persistence Tools
 */

public class Posts{


    // Fields    

     private Integer id;
     private Timestamp time;
     private String contents;
     private String author;
     private String objId;
     private String types;



   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return this.time;
    }
    
    public void setTime(Timestamp time) {
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