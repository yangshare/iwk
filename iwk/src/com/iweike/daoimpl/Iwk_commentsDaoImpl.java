package com.iweike.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.iweike.po.Comments;



public class Iwk_commentsDaoImpl {
	
	

	/**
	 * 评论数据类 
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
	Comments comments=new Comments();
	
	//1.根据id删除对象
	public boolean delectById(int id) {
		comments=new Comments();
		return iwkDao.delete(id, comments);
	}
	
	//2.根据id查询单个对象
	public Comments queryById(int id) {
		comments=new Comments();
		return (Comments)iwkDao.query(id, comments);
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
	public List<Comments> queryByWhere(String keys,Object values) {
		comments=new Comments();
		try {
			return Object2Other(iwkDao.query(comments, keys, values));
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的query(String keys,Object values)报错："+e.getMessage());
			return null;
		}
	}
	
	//6.获取所有记录，返回集合；	
	public List<Comments> queryAll() {
		comments=new Comments();
		try {
			return Object2Other(iwkDao.query(comments));
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的queryAll报错："+e.getMessage());
			return null;
		}
		
	}
	
	//7.集合内对象类别转换
	public List<Comments> Object2Other(List<Object> list) {
		Comments v=null;
		List<Comments> vlist=new ArrayList<Comments>();
		try {
			//遍历object集合，强转Comments对象
			for (int i = 0; i < list.size(); i++) {
				v = new Comments();
				v = (Comments) list.get(i);
				vlist.add(v);
			}
			return vlist;
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的Object2Other(List<Object> list)报错："+e.getMessage());
			return null;
		}
		
	}
	// 8.降序查询（对象类,数据库字段,取前面几条）

	public List<Comments> queryOrderDesc(String keys,int maxResults) {
		comments=new Comments();
		try {
			return Object2Other(iwkDao.queryOrderDesc(comments, keys,maxResults));
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的queryOrderDesc报错："+e.getMessage());
			return null;
		}

	}
	
	// 9.升序查询（对象类,数据库字段，取前面几条）

	public List<Comments> queryOrderAsc(String keys,int maxResults) {
		comments=new Comments();
		try {
			return Object2Other(iwkDao.queryOrderAsc(comments, keys,maxResults));
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的queryOrderAsc报错："+e.getMessage());
			return null;
		}

	}
	
	// 10.获取最后一条记录的id（对象类）
	public int queryLastRecordId() {
		comments=new Comments();
		try {
			comments=(Comments)iwkDao.queryLastRecordId(comments);
			return comments.getId();
		} catch (Exception e) {
			System.out.println("评论queryLastRecordId方法异常：" + e.getMessage());
			return 0;
		} 

	}
	
	// 11.分页查询
	public List<Comments> queryPageComments(int curPage,int max,String types,String values) {
		comments=new Comments();
		try {
			return Object2Other(iwkDao.query(comments, curPage, max,types,values));
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的queryOrderAsc报错："+e.getMessage());
			return null;
		}

	}
	
	// 11.获取该表记录总条数

	public double queryRecordNum(String types,String values) {
		comments=new Comments();
		try {
			return iwkDao.queryRecordNum(comments,types,values);
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的queryRecordNum报错："+e.getMessage());
			return Double.parseDouble(""+0);
		}
	}

	// 12.获取各类评论个数
	public double queryPageNumByTypes(String types, String values) {
		comments=new Comments();
		try {
			return iwkDao.queryRecordNum(comments,types,values);
		} catch (Exception e) {
			System.out.println("Iwk_CommentsDaoImpl的queryRecordNum报错："+e.getMessage());
			return Double.parseDouble(""+0);
		}
	}
	
	// 13.通过id删除对象
	public boolean delect(int id) {
		comments=new Comments();
		return iwkDao.delete(id, comments);
	}


	

}
