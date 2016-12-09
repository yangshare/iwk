package com.iweike.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.iweike.po.User;
import com.iweike.tool.FileDeleteTool;

public class Iwk_userDaoImpl {

	IwkDaoImpl iwkDao = new IwkDaoImpl();// 通用增删改查方法
	User user = null;

	// 1.通过id删除对象
	public boolean delect(int id) {
		user = new User();
		user = query(id);
		//删除视频
		FileDeleteTool.deleteFile(user.getIcons());
		return iwkDao.delete(id, user);
	}

	// 2.通过id查询对象
	public User query(int id) {
		user = new User();
		return (User) iwkDao.query(id, user);
	}

	// 3.通过id保存对象
	public boolean save(Object object) {
		user = new User();
		return iwkDao.save(object);
	}

	// 4.通过对象修改记录
	public boolean update(Object object) {
		return iwkDao.update(object);
	}

	// 5.通过昵称查询该昵称是否被占用
	public int queryUserIsExisted(String name) {
		user = new User();
		return iwkDao.query(name, user);

	}

	// 6.通过学号查询该用户
	public User queryUserBySno(String sno) {
		user = new User();
		List<User> uLsit = Object2Other(iwkDao.query(user, "sno", sno));
		if (uLsit != null)
			return uLsit.get(0);
		else
			return null;

	}

	// 6.通过昵称查询该用户
	public User queryUserByName(String name) {
		user = new User();
		List<User> uList = Object2Other(iwkDao.query(user, "name", name));
		System.out.println(uList);
		if (uList != null && !uList.toString().equals("[]"))
			return uList.get(0);
		else
			return null;
	}

	// 10.获取最后一条记录的id（对象类）
	public int queryLastRecordId() {
		user = new User();
		try {
			user = (User) iwkDao.queryLastRecordId(user);
			return user.getId();
		} catch (Exception e) {
			System.out.println("用户queryLastRecordId方法异常：" + e.getMessage());
			return 0;
		}

	}
	// 11.获取所有记录，返回集合；
	public List<User> queryAll() {
		user = new User();
		try {

			return Object2Other(iwkDao.query(user));
		} catch (Exception e) {
			System.out.println("Iwk_userDaoImpl的queryAll报错：" + e.getMessage());
			return null;
		}

	}

	/*
	 * **********\\\\\\\\\\\\\\\\\\集合内对象类别转换\\\\\\\\\\\\\\\\\\\\\***********
	 */
	public List<User> Object2Other(List<Object> list) {
		User u = null;
		List<User> ulist = new ArrayList<User>();
		try {
			// 遍历object集合，强转Video对象
			for (int i = 0; i < list.size(); i++) {
				u = new User();
				u = (User) list.get(i);
				ulist.add(u);
			}
			return ulist;
		} catch (Exception e) {
			System.out
					.println("Iwk_UserDaoImpl的Object2Other(List<Object> list)报错："
							+ e.getMessage());
			return null;
		}

	}

}
