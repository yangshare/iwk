package com.iweike.daoimpl;

import com.iweike.po.Comments;



public class Iwk_commentsDaoImpl {
	IwkDaoImpl iwkDao=new IwkDaoImpl();//通用增删改查方法
	Comments comments=new Comments();
	
	public boolean delect(int id) {
		
		return iwkDao.delete(id, comments);
	}

	public Comments query(int id) {
		
		return (Comments)iwkDao.query(id, comments);
	}

	public boolean save(Object object) {
		
		return iwkDao.save(object);
	}

	public boolean update(Object object) {
		
		return iwkDao.update(object);
	}
	

}
