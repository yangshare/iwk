package com.iweike.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import com.iweike.daoimpl.Iwk_videoDaoImpl;
import com.iweike.po.Video;
import com.iweike.tool.FileUploadTool;
import com.opensymphony.xwork2.ActionSupport;

public class wVideoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 前台获取视频类 属性： 1.前后台通信对象request，response； 2.struts配置返回的json串，必须有set方法配套 方法：
	 * 1.获取所有视频信息===getAllVideo(); 2.通过id获取单个视频信息===getVideoById();
	 * 3.通过类型获取一类视频信息===getVideoByType(); 4.集合内对象类别转换;
	 */
	// 1.前后台通信对象request，response
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	PrintWriter out = null;
	// 2.struts配置返回的json串，必须有set方法配套
	List<String> json = null;
	String jsonStr = "";

	public String getJsonStr() {
		return jsonStr;
	}

//	public void setJsonStr(String jsonStr) {
//		this.jsonStr = jsonStr;
//	}

	public List<String> getJson() {
		return json;
	}

//	public void setJson(List<String> json) {
//		this.json = json;
//	}

	// 3.文件的上传
	FileUploadTool fileUploadTool=new FileUploadTool();
	private File image;
	private String imageFileName;
	private File upload;
	private String uploadFileName;


	public void setImage(File image) {
		this.image = image;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	// 实例化Dao包
	Iwk_videoDaoImpl videoDao = new Iwk_videoDaoImpl();

	// 1.获取所有视频信息
	@SuppressWarnings("unchecked")
	public String queryAllVideo() {
		try {
			// org.hibernate.Session s = HibernateSessionFactory.getSession();
			// System.out.println("Session="+s);
			JSONArray jsonList = JSONArray.fromObject(videoDao.queryAll());
			// System.out.println("jsonList="+jsonList);
			// out=response.getWriter();
			// out.print(jsonList);
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 2.获取热播视频信息
	@SuppressWarnings("unchecked")
	public String queryPopVideo() {
		try {
			JSONArray jsonList = JSONArray.fromObject(videoDao.queryOrderDesc(
					"clicks", 8));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 3.获取最新视频信息
	@SuppressWarnings("unchecked")
	public String queryLastVideo() {
		try {
			JSONArray jsonList = JSONArray.fromObject(videoDao.queryOrderDesc(
					"time", 8));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 4.集合内对象类别转换
	public List<String> Video2String(List<Video> list) {
		String str = null;
		List<String> slist = new ArrayList<String>();
		try {
			// 遍历object集合，强转Video对象
			for (int i = 0; i < list.size(); i++) {
				str = new String();
				str = list.get(i).toString();
				slist.add(str);
			}
			return slist;
		} catch (Exception e) {
			System.out.println("wVideoAction的Video2String(List<Video> list)报错："
					+ e.getMessage());
			return null;
		}

	}

	// 5.上传文件
	public String uploadVideo() {
		String filePath="";
		if(upload!=null){
			filePath=fileUploadTool.uploadImg(upload, uploadFileName, "video");
		}
		this.jsonStr = "upload/video/"+filePath.substring(filePath.lastIndexOf("\\")+1);
		return SUCCESS;
	}

	// 6.上传图片
	public String uploadImg() {
		String filePath="";
		if(image!=null){
			filePath=fileUploadTool.uploadImg(image, imageFileName, "img");
		}
		System.out.println("返回文件路径=="+filePath);
		this.jsonStr = "upload/img/"+filePath.substring(filePath.lastIndexOf("\\")+1);
		return SUCCESS;
	}
}
