package com.iweike.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.iweike.po.Posts;

public class Iwk_postsDaoImpl{
	/**
	 * 视频数据类 
	 * 方法： 
	 * 1.删除（id,对象），返回boolean； 
	 * 2.查询（id,对象），返回object；
	 * 3.保存（对象），返回boolean；
	 * 4.修改（对象），返回boolean；
	 * 5.取等条件查询（对象类，字段，值），返回集合；
	 * 6.获取所有记录，返回集合；
	 * 7.集合内对象类别转换;
	 */
	IwkDaoImpl iwkDao=new IwkDaoImpl();//通用增删改查方法
	Posts posts=null;
	
	//1.根据id删除对象
	public boolean delectById(int id) {
		posts=new Posts();
		return iwkDao.delete(id, posts);
	}
	
	//2.根据id查询单个对象
	public Posts queryById(int id) {
		posts=new Posts();
		return (Posts)iwkDao.query(id, posts);
	}
	
	//3.保存对象
	public boolean save(Object object) {
		return iwkDao.save(object);
	}
	
	//4.更新对象
	public boolean update(Object object) {
		
		return iwkDao.update(object);
	}
	
	//5.取等条件查询（对象类，字段，值），返回集合；
	public List<Posts> queryByWhere(String keys,Object values) {
		posts=new Posts();
		try {
			return Object2Other(iwkDao.query(posts, keys, values));
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的query(String keys,Object values)报错："+e.getMessage());
			return null;
		}
	}
	
	//6.获取所有记录，返回集合；	
	public List<Posts> queryAll() {
		posts=new Posts();
		try {
			return Object2Other(iwkDao.query(posts));
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的queryAll报错："+e.getMessage());
			return null;
		}
		
	}
	
	//7.集合内对象类别转换
	public List<Posts> Object2Other(List<Object> list) {
		Posts v=null;
		List<Posts> vlist=new ArrayList<Posts>();
		try {
			//遍历object集合，强转Posts对象
			for (int i = 0; i < list.size(); i++) {
				v = new Posts();
				v = (Posts) list.get(i);
				vlist.add(v);
			}
			return vlist;
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的Object2Other(List<Object> list)报错："+e.getMessage());
			return null;
		}
		
	}
	// 8.降序查询（对象类,数据库字段,取前面几条）

	public List<Posts> queryOrderDesc(String keys,int maxResults) {
		posts=new Posts();
		try {
			return Object2Other(iwkDao.queryOrderDesc(posts, keys,maxResults));
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的queryOrderDesc报错："+e.getMessage());
			return null;
		}

	}
	
	// 9.升序查询（对象类,数据库字段，取前面几条）

	public List<Posts> queryOrderAsc(String keys,int maxResults) {
		posts=new Posts();
		try {
			return Object2Other(iwkDao.queryOrderAsc(posts, keys,maxResults));
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的queryOrderAsc报错："+e.getMessage());
			return null;
		}

	}
	
	// 10.获取最后一条记录的id（对象类）
	public int queryLastRecordId() {
		posts=new Posts();
		try {
			posts=(Posts)iwkDao.queryLastRecordId(posts);
			return posts.getId();
		} catch (Exception e) {
			System.out.println("视频queryLastRecordId方法异常：" + e.getMessage());
			return 0;
		} 

	}
	
	// 11.分页查询
	public List<Posts> queryPagePosts(int curPage,int max,String types,String values) {
		posts=new Posts();
		try {
			return Object2Other(iwkDao.query(posts, curPage, max,types,values));
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的queryOrderAsc报错："+e.getMessage());
			return null;
		}

	}
	
	// 11.获取该表记录总条数

	public double queryRecordNum(String types,String values) {
		posts=new Posts();
		try {
			return iwkDao.queryRecordNum(posts,types,values);
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的queryRecordNum报错："+e.getMessage());
			return Double.parseDouble(""+0);
		}
	}

	// 12.获取各类视频个数
	public double queryPageNumByTypes(String types, String values) {
		posts=new Posts();
		try {
			return iwkDao.queryRecordNum(posts,types,values);
		} catch (Exception e) {
			System.out.println("Iwk_PostsDaoImpl的queryRecordNum报错："+e.getMessage());
			return Double.parseDouble(""+0);
		}
	}

}
