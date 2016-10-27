package com.iweike.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.iweike.po.Video;

public class Iwk_videoDaoImpl{
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
	Video video=null;
	
	//1.根据id删除对象
	public boolean delectById(int id) {
		video=new Video();
		return iwkDao.delete(id, video);
	}
	
	//2.根据id查询单个对象
	public Video queryById(int id) {
		video=new Video();
		return (Video)iwkDao.query(id, video);
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
	public List<Video> queryByWhere(String keys,Object values) {
		video=new Video();
		try {
			return Object2Other(iwkDao.query(video, keys, values));
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的query(String keys,Object values)报错："+e.getMessage());
			return null;
		}
	}
	
	//6.获取所有记录，返回集合；	
	public List<Video> queryAll() {
		video=new Video();
		try {
			
			return Object2Other(iwkDao.query(video));
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的queryAll报错："+e.getMessage());
			return null;
		}
		
	}
	
	//7.集合内对象类别转换
	public List<Video> Object2Other(List<Object> list) {
		Video v=null;
		List<Video> vlist=new ArrayList<Video>();
		try {
			//遍历object集合，强转Video对象
			for (int i = 0; i < list.size(); i++) {
				v = new Video();
				v = (Video) list.get(i);
				vlist.add(v);
			}
			return vlist;
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的Object2Other(List<Object> list)报错："+e.getMessage());
			return null;
		}
		
	}
	// 8.降序查询（对象类,数据库字段,取前面几条）

	public List<Video> queryOrderDesc(String keys,int maxResults) {
		video=new Video();
		try {
			return Object2Other(iwkDao.queryOrderDesc(video, keys,maxResults));
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的queryOrderDesc报错："+e.getMessage());
			return null;
		}

	}
	
	// 9.升序查询（对象类,数据库字段，取前面几条）

	public List<Video> queryOrderAsc(String keys,int maxResults) {
		video=new Video();
		try {
			return Object2Other(iwkDao.queryOrderAsc(video, keys,maxResults));
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的queryOrderAsc报错："+e.getMessage());
			return null;
		}

	}
	
	// 10.获取最后一条记录的id（对象类）
	public int queryLastRecordId() {
		video=new Video();
		try {
			video=(Video)iwkDao.queryLastRecordId(video);
			return video.getId();
		} catch (Exception e) {
			System.out.println("视频queryLastRecordId方法异常：" + e.getMessage());
			return 0;
		} 

	}
	
	// 11.分页查询
	public List<Video> queryPageVideo(int curPage,int max,String types,String values) {
		video=new Video();
		try {
			return Object2Other(iwkDao.query(video, curPage, max,types,values));
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的queryOrderAsc报错："+e.getMessage());
			return null;
		}

	}
	
	// 11.获取该表记录总条数

	public double queryRecordNum(String types,String values) {
		video=new Video();
		try {
			return iwkDao.queryRecordNum(video,types,values);
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的queryRecordNum报错："+e.getMessage());
			return Double.parseDouble(""+0);
		}
	}

	// 12.获取各类视频个数
	public double queryPageNumByTypes(String types, String values) {
		video=new Video();
		try {
			return iwkDao.queryRecordNum(video,types,values);
		} catch (Exception e) {
			System.out.println("Iwk_VideoDaoImpl的queryRecordNum报错："+e.getMessage());
			return Double.parseDouble(""+0);
		}
	}

}
