package com.iweike.action;

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

import com.iweike.daoimpl.Iwk_commentsDaoImpl;
import com.iweike.po.Comments;
import com.iweike.tool.PropertyUtil;
import com.opensymphony.xwork2.ActionSupport;

public class wCommentsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 前台获取评论类 属性： 1.前后台通信对象request，response； 2.struts配置返回的json串，必须有set方法配套 方法：
	 * 1.获取所有评论信息===getAllVideo(); 2.通过id获取单个评论信息===getVideoById();
	 * 3.通过类型获取一类评论信息===getVideoByType(); 4.集合内对象类别转换;
	 */
	// 1.前后台通信对象request，response
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	PrintWriter out = null;
	Comments comments = null;
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


	// 4.文件入库的字段
	private Integer id;// 编号
	private String types;// 是視頻還是帖子
	private String contents;// 评论内容
	private String author;// 评论人

	private Integer objId;// 评论人id
	private Integer typesId;// 视频编号或帖子编号


	/*
	 * 字段方法
	 */
	public void setId(Integer id) {
		this.id = id;
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

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public void setTypesId(Integer typesId) {
		this.typesId = typesId;
	}

	
	// 实例化Dao包
	Iwk_commentsDaoImpl CommentsDao = new Iwk_commentsDaoImpl();

	// 1.获取所有评论信息
	@SuppressWarnings("unchecked")
	public String queryAllComments() {
		try {
			JSONArray jsonList = JSONArray.fromObject(CommentsDao.queryAll());
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 2.获取热播评论信息
	@SuppressWarnings("unchecked")
	public String queryPopComments() {
		try {
			JSONArray jsonList = JSONArray.fromObject(CommentsDao.queryOrderDesc(
					"clicks", 8));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 3.获取最新评论信息
	@SuppressWarnings("unchecked")
	public String queryLastComments() {
		try {
			JSONArray jsonList = JSONArray.fromObject(CommentsDao.queryOrderDesc(
					"time", 8));
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 4.集合内对象类别转换
	public List<String> Comments2String(List<Comments> list) {
		String str = null;
		List<String> slist = new ArrayList<String>();
		try {
			// 遍历object集合，强转Comments对象
			for (int i = 0; i < list.size(); i++) {
				str = new String();
				str = list.get(i).toString();
				slist.add(str);
			}
			return slist;
		} catch (Exception e) {
			System.out
					.println("wCommentsAction的Comments2String(List<Comments> list)报错："
							+ e.getMessage());
			return null;
		}

	}


	// 7.评论入库
	public String addComments() {
		comments = new Comments();
		// 获取时间戳
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateSr = sdf.format(date);

		comments.setId(CommentsDao.queryLastRecordId() + 1);
		comments.setTypes(types.trim());
		comments.setContents(contents.trim());
		comments.setAuthor(author.trim());

		comments.setTime(dateSr);// 上传时间

		comments.setIsShow("0");// 是否上架展示
		
		comments.setObjId(objId);
		comments.setTypesId(typesId);// 视频编号或帖子编号

		try {
			this.jsonStr = CommentsDao.save(comments) ? "评论添加成功" : "评论添加失败";
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 9.获取最新评论信息
	// public String queryPageComments() {
	// try {
	// JSONArray jsonList =
	// JSONArray.fromObject(CommentsDao.queryPageComments(curPage,
	// HOME_PerPageRow,"types",types));
	// this.json = jsonList;
	// return SUCCESS;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ERROR;
	// }
	// }

	// 10.获取总页面
	public String queryPageNum() {
		try {
			this.jsonStr = ""
					+ (int) Math.ceil(CommentsDao.queryRecordNum("types", types)
							/ HOME_PerPageRow);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 10.获取各类评论个数
	public String queryPageNumByTypes() {
		try {

			this.jsonStr = ""
					+ (int) CommentsDao.queryPageNumByTypes("types", types);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 10.获取各类评论个数
	public String queryCommentById() {
		try {
			JSONObject jsonObject = JSONObject.fromObject(CommentsDao
					.queryById(id));
			this.jsonStr = "" + jsonObject;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

}
