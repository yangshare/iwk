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
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateSr = sdf.format(date);
		System.out.println("id*******************;"+videoDao.queryLastRecordId());
		video.setId(videoDao.queryLastRecordId() + 1);
		video.setTitle("题目");
		video.setTypes("类型");
		video.setIntroduce("简介");
		video.setAuthor("作者");

		video.setPic("封面");// 封面
		video.setClicks(0);// 点击率
		video.setTime(dateSr);// 视频上传时间

		video.setSrcs("视频路径");
		video.setObjId("1");
		video.setIsShow(0 + "");// 是否上架展示
		videoDao.save(video);

	}

}
