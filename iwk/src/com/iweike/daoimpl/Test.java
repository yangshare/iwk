package com.iweike.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.iweike.po.Video;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Iwk_videoDaoImpl VideoDao=new Iwk_videoDaoImpl();
		
//		WkVideo WkVideo=new WkVideo();
		//测试id查询视频（通过）
//		WkVideo=WkVideoDao.query(1);
//		System.out.println("电影的名称="+WkVideo.getTitle());
		//测试类别查询视频(通过)
		List<Video> vlist=new ArrayList<Video>();
//		vlist=WkVideoDao.queryByWhere("types", "文艺艺术");
		//测试查询所有视频(通过)
		vlist=VideoDao.queryAll();
		System.out.println("电影的类别="+vlist.get(0).getTitle());
		

	}

}
