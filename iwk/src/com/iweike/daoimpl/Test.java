package com.iweike.daoimpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.iweike.po.Video;

@SuppressWarnings("unused")
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Iwk_videoDaoImpl videoDao = new Iwk_videoDaoImpl();
		Iwk_userDaoImpl userDao = new Iwk_userDaoImpl();
		Video video = null;
		//		
		video = new Video();
		// 获取时间戳
		System.out.println(Math.ceil(videoDao.queryRecordNum()/8));

	}

}
