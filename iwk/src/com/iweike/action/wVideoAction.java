package com.iweike.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;

import com.iweike.daoimpl.Iwk_videoDaoImpl;
import com.iweike.po.Video;
import com.iweike.tool.FileUploadTool;
import com.iweike.tool.PropertyUtil;
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
	Video video = null;
	// 初始化参数配置文件
	private static PropertyUtil propertyUtil = new PropertyUtil(
			"cons.properties");
	private static int HOME_PerPageRow;// 分页每次加载数量
	static {
		HOME_PerPageRow = Integer.parseInt(propertyUtil
				.getPropertyValue("HOME_PerPageRow"));
	}
	// 2.struts配置返回的json串，必须有set方法配套
	List<String> json = null;
	String jsonStr = "";

	public String getJsonStr() {
		return jsonStr;
	}

	public List<String> getJson() {
		return json;
	}

	// 3.文件的上传
	FileUploadTool fileUploadTool = new FileUploadTool();
	private File image;
	private String imageFileName;
	private File upload;
	private String uploadFileName;

	private String terminalImage;// 终端（Android）图片
	private String terminalUpload;// 终端（Android）视频

	public void setTerminalImage(String terminalImage) {
		this.terminalImage = terminalImage;
	}

	public void setTerminalUpload(String terminalUpload) {
		this.terminalUpload = terminalUpload;
	}

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

	// 4.文件入库的字段
	private String video_Id;
	private String title;
	private String types;
	private String introduce;
	private String author;
	private String obj_id;
	private String backImgPath;
	private String backVideoPath;
	// 当前页面
	private int curPage;

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public void setVideo_Id(String videoId) {
		video_Id = videoId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setObj_id(String objId) {
		obj_id = objId;
	}

	public void setBackImgPath(String backImgPath) {
		this.backImgPath = backImgPath;
	}

	public void setBackVideoPath(String backVideoPath) {
		this.backVideoPath = backVideoPath;
	}

	// 实例化Dao包
	Iwk_videoDaoImpl videoDao = new Iwk_videoDaoImpl();

	// 1.获取所有视频信息
	@SuppressWarnings("unchecked")
	public String queryAllVideo() {
		try {
			JSONArray jsonList = JSONArray.fromObject(videoDao.queryAll());
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

	/* *************************************
	 * 文件上传////////////////////////////***
	 */
	// 5.上传文件
	public String uploadVideo() {
//		System.out.println("terminalUpload*************************=" + terminalUpload);
		System.out.println("uploadFileName=" + uploadFileName);
		String filePath = "";
		if (terminalUpload != null) {
			// 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密
			try {
				byte[] videoBytes = new BASE64Decoder()
						.decodeBuffer(terminalUpload);
				filePath = fileUploadTool.uploadImg(videoBytes, uploadFileName,
						"video");
			} catch (IOException e) {
				System.out.println("uploadImg的IOException：" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			if (upload != null) {
				filePath = fileUploadTool.uploadImg(upload, uploadFileName,
						"video");
			}
		}
		this.jsonStr = "upload/video/"
				+ filePath.substring(filePath.lastIndexOf("\\") + 1);
		return SUCCESS;
	}

	// 6.上传图片
	public String uploadImg() {
//		System.out.println("terminal************************************=" + terminalImage);
		System.out.println("imageFileName=" + imageFileName);
		String filePath = "";
		if (terminalImage != null) {
			// 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密
			try {
				byte[] photoimg = new BASE64Decoder()
						.decodeBuffer(terminalImage);
				filePath = fileUploadTool.uploadImg(photoimg, imageFileName,
						"img");
			} catch (IOException e) {
				System.out.println("uploadImg的IOException：" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			if (image != null) {
				filePath = fileUploadTool
						.uploadImg(image, imageFileName, "img");
			}
		}

		this.jsonStr = "upload/img/"
				+ filePath.substring(filePath.lastIndexOf("\\") + 1);
		return SUCCESS;
	}

	// 7.视频入库
	public String addVideo() {
		video = new Video();
		// 获取时间戳
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateSr = sdf.format(date);

		video.setId(videoDao.queryLastRecordId() + 1);
		video.setTitle(title.trim());
		video.setTypes(types.trim());
		video.setIntroduce(introduce.trim());
		video.setAuthor(author.trim());

		video.setPic(backImgPath.trim());// 封面
		video.setClicks(0);// 点击率
		video.setTime(dateSr);// 视频上传时间

		video.setSrcs(backVideoPath.trim());
		video.setObjId(obj_id.trim());
		video.setIsShow("0");// 是否上架展示
		try {
			this.jsonStr = videoDao.save(video) ? "视频添加成功" : "视频添加失败";
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 8.增加视频点击次数
	public String addClicks() {
		video = new Video();
		video = videoDao.queryById(Integer.parseInt(video_Id));
		video.setClicks(video.getClicks() + 1);// 点击率
		try {
			this.jsonStr = videoDao.update(video) ? "添加点击次数成功" : "添加点击次数成功";
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 9.获取最新视频信息
	@SuppressWarnings("unchecked")
	public String queryPageVideo() {
		try {
			JSONArray jsonList = JSONArray.fromObject(videoDao.queryPageVideo(
					curPage, HOME_PerPageRow, "types", types));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	// 9.查询单人的视频
	@SuppressWarnings("unchecked")
	public String queryPageVideoByObjid() {
		try {
			JSONArray jsonList = JSONArray.fromObject(videoDao.queryPageVideoByObjid(
					"objId", obj_id));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}


	// 10.获取总页面
	public String queryPageNum() {
		System.out.println("types"+types);
		try {
			this.jsonStr = ""
					+ (int) Math.ceil(videoDao.queryRecordNum("types", types)
							/ HOME_PerPageRow);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 10.获取各类视频个数
	public String queryPageNumByTypes() {
		try {
			this.jsonStr = ""
					+ (int) videoDao.queryPageNumByTypes("types", types);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	// 10.通过Id删除视频
	public String deleteVideoById() {
		try {
			this.jsonStr = ""
					+ videoDao.delectById(Integer.parseInt(video_Id));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}


}
