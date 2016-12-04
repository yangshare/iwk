package com.iweike.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.iweike.daoimpl.Iwk_userDaoImpl;
import com.iweike.daoimpl.Iwk_videoDaoImpl;
import com.iweike.po.User;
import com.iweike.po.Video;
import com.iweike.tool.FileUploadTool;
import com.opensymphony.xwork2.ActionSupport;

public class wUserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	/**
	 * 前台获取视频类 属性： 1.前后台通信对象request，response； 2.struts配置返回的json串，必须有get方法配套 方法：
	 * 1.获取所有视频信息===getAllVideo(); 2.通过id获取单个视频信息===getVideoById();
	 * 3.通过类型获取一类视频信息===getVideoByType(); 4.集合内对象类别转换;
	 */
	// 1.前后台通信对象request，response
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = null;
	
	PrintWriter out = null;
	Iwk_userDaoImpl userDao = new Iwk_userDaoImpl();
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

	// 4.用户信息
	// Fields
	private String user_id;//用户id
	private String icons;// 头像

	private String name;// 登陆名（昵称）
	private String pwd;// 登陆密码
	private String question;// 找回密码问题
	private String answer;// 找回密码答案
	private String school;// 学校
	private String college;// 学院
	private String professional;// 专业
	private String clazz;// 班级
	private String sno;// 学号
	private String sname;// 学生姓名
	private String ssex;// 学生性别
	private String email;// 邮箱
	private String introduce;// 自我介绍

	// Property accessors

	
	public void setName(String name) {
		this.name = name;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public void setSsex(String ssex) {
		this.ssex = ssex;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}


	// 实例化Dao包
	Iwk_videoDaoImpl videoDao = new Iwk_videoDaoImpl();
	User user = null;

	// 1.wUser_addUser(注册)
	public String addUser() {
		user = new User();
		try {
			icons=uploadImg();
			user.setIcons(icons.trim());// 头像

			user.setId(userDao.queryLastRecordId() + 1);// 编号
			user.setName(name.trim());// 登陆名（昵称）
			user.setPwd(pwd.trim());// 登陆密码
			user.setQuestion(question.trim());// 找回密码问题
			user.setAnswer(answer.trim());// 找回密码答案
			user.setSchool(school.trim());// 学校
			user.setCollege(college.trim());// 学院
			user.setProfessional(professional.trim());// 专业
			user.setClazz(clazz.trim());// 班级
			user.setSno(sno.trim());// 学号
			user.setSname(sname.trim());// 学生姓名
			user.setSsex(ssex.trim());// 学生性别
			user.setEmail(email.trim());// 邮箱
			user.setIntroduce(introduce.trim());// 自我介绍
			
			if(userDao.save(user))
				return "regSuccess";
			else{
				response=ServletActionContext.getResponse();
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				out=response.getWriter();
				out.print("alert('注册失败！请联系寻求帮助')");
				return "regError";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	// 2.wUser_queryUserIsExisted(注册时查询用户是否存在)
	public String queryUserIsExisted() {

		try {
			if (userDao.queryUserIsExisted(name) > 0)
				this.jsonStr = "用户名存在";
			else
				this.jsonStr = "用户名不存在";
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 3.wUser_queryUserBySno（通过学号获取用户）
	public String queryUserBySno() {
		user = new User();
		try {
			user = userDao.queryUserBySno(sno);
			this.jsonStr = JSONObject.fromObject(user).toString();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 4.wUser_queryUserByName（登陆）
	public String queryUserByName() {
		user = new User();
		try {
			System.out.println("前台密码："+pwd+",前台昵称："+name);
			user=userDao.queryUserByName(name);
			
			if(user!=null){
				System.out.println("对象密码："+user.getPwd()+",前台密码："+pwd);
				if(user.getPwd().equals(pwd))
					this.jsonStr = JSONObject.fromObject(user).toString();
				else
					this.jsonStr = null;
			}else
				this.jsonStr = null;
			
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	// 5.获取所有用户信息
	@SuppressWarnings("unchecked")
	public String queryAllUser() {
		try {
			JSONArray jsonList = JSONArray.fromObject(userDao.queryAll());
			this.json = jsonList;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	// 6.通过Id删除用户
	public String deleteUserById() {
		try {
			this.jsonStr = ""
					+ userDao.delect(Integer.parseInt(user_id));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/*
	 * ***********\\\\\\\\集合内对象类别转换\\\\\\\\***********
	 */
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

	public String uploadImg() {
		String filePath = "";
		if (image != null) {
			filePath = fileUploadTool.uploadImg(image, imageFileName, "img/head");
		}

		filePath = "upload/img/head/"
				+ filePath.substring(filePath.lastIndexOf("\\") + 1);
		return filePath;
	}

}
