package com.iweike.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.iweike.daoimpl.Iwk_postsDaoImpl;
import com.iweike.po.Posts;
import com.iweike.tool.FileUploadTool;
import com.iweike.tool.PropertyUtil;
import com.opensymphony.xwork2.ActionSupport;

public class wPostsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 前台获取帖子类 属性： 1.前后台通信对象request，response； 2.struts配置返回的json串，必须有set方法配套 方法：
	 * 1.获取所有帖子信息===getAllVideo(); 2.通过id获取单个帖子信息===getVideoById();
	 * 3.通过类型获取一类帖子信息===getVideoByType(); 4.集合内对象类别转换;
	 */
	// 1.前后台通信对象request，response
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	PrintWriter out = null;
	Posts posts = null;
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

	public void setImage(File image) {
		this.image = image;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	// 4.文件入库的字段
	private String post_id;
	
	private String title;
	private String types;
	private String contents;
	private String author;
	private String obj_id;
	

	private String pic;//封面
	private String picHight;//封面高度
	// 当前页面
	private int curPage;

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	
	public void setPost_id(String postId) {
		post_id = postId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setObj_id(String objId) {
		obj_id = objId;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setPicHight(String picHight) {
		this.picHight = picHight;
	}

	// 实例化Dao包
	Iwk_postsDaoImpl PostsDao = new Iwk_postsDaoImpl();

	// 1.获取所有帖子信息
	@SuppressWarnings("unchecked")
	public String queryAllPosts() {
		try {
			JSONArray jsonList = JSONArray.fromObject(PostsDao.queryAll());
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 2.获取热播帖子信息
	@SuppressWarnings("unchecked")
	public String queryPopPosts() {
		try {
			JSONArray jsonList = JSONArray.fromObject(PostsDao.queryOrderDesc(
					"clicks", 8));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 3.获取最新帖子信息
	@SuppressWarnings("unchecked")
	public String queryLastPosts() {
		try {
			JSONArray jsonList = JSONArray.fromObject(PostsDao.queryOrderDesc(
					"time", 8));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 4.集合内对象类别转换
	public List<String> Posts2String(List<Posts> list) {
		String str = null;
		List<String> slist = new ArrayList<String>();
		try {
			// 遍历object集合，强转Posts对象
			for (int i = 0; i < list.size(); i++) {
				str = new String();
				str = list.get(i).toString();
				slist.add(str);
			}
			return slist;
		} catch (Exception e) {
			System.out.println("wPostsAction的Posts2String(List<Posts> list)报错："
					+ e.getMessage());
			return null;
		}

	}

	/* *************************************
	 * 文件上传////////////////////////////***
	 */

	// 6.上传图片
	public String uploadImg() {
		String filePath = "";
		if (image != null) {
			filePath = fileUploadTool.uploadImg(image, imageFileName, "posts");
		}

		this.jsonStr = "upload/posts/"
				+ filePath.substring(filePath.lastIndexOf("\\") + 1);
		return SUCCESS;
	}

	// 7.帖子入库
	public String addPosts() {
		posts = new Posts();
		// 获取时间戳
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateSr = sdf.format(date);

		posts.setId(PostsDao.queryLastRecordId() + 1);
		posts.setTitle(title.trim());
		posts.setTypes(types.trim());
		posts.setContents(contents.trim());
		posts.setAuthor(author.trim());

		posts.setPic(pic.trim());// 封面
		posts.setPicHight(picHight.trim());//封面高度
		posts.setTime(dateSr);// 上传时间

		posts.setObjId(obj_id.trim());
		posts.setIsShow("0");// 是否上架展示
		try {
			this.jsonStr = PostsDao.save(posts) ? "帖子添加成功" : "帖子添加失败";
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}


	// 9.获取最新帖子信息
	@SuppressWarnings("unchecked")
	public String queryPagePosts() {
		try {
			JSONArray jsonList = JSONArray.fromObject(PostsDao.queryPagePosts(curPage, HOME_PerPageRow,"types",types));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	// 10.获取总页面
	public String queryPageNum() {
		try {
			this.jsonStr =""+(int)Math.ceil(PostsDao.queryRecordNum("types",types)/HOME_PerPageRow);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	// 10.获取各类帖子个数
	public String queryPageNumByTypes() {
		try {
			
			this.jsonStr =""+(int)PostsDao.queryPageNumByTypes("types",types);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	// 10.获取各类帖子个数
	public String queryPostById() {
		try {
			JSONObject jsonObject=JSONObject.fromObject(PostsDao.queryById(Integer.parseInt(post_id.trim())));
			this.jsonStr =""+jsonObject;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

}
