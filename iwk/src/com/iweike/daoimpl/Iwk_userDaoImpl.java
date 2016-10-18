package com.iweike.daoimpl;

import com.iweike.po.User;



public class Iwk_userDaoImpl{

	IwkDaoImpl iwkDao=new IwkDaoImpl();//通用增删改查方法
	User user=new User();
	
	public boolean delect(int id) {
		
		return iwkDao.delete(id, user);
	}

	public User query(int id) {
		
		return (User)iwkDao.query(id, user);
	}

	public boolean save(Object object) {
		
		return iwkDao.save(object);
	}

	public boolean update(Object object) {
		
		return iwkDao.update(object);
	}

}
