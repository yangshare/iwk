package com.iwk.yang.bean;


import com.iwk.yang.entity.PersonInfoEntity;
import com.iwk.yang.volley.BaseVO;

/**
 * 身份证信息
 * @author 石岩
 */
public class PersonInfoBean extends BaseVO {
	private String success;
	private PersonInfoEntity result;
	//失败时出现
	private String msg;
	//失败时出现
	private String msgid;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public PersonInfoEntity getResult() {
		return result;
	}
	public void setResult(PersonInfoEntity result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	@Override
	public String toString() {
		return "PersonInfoBean{" +
				"success='" + success + '\'' +
				", result=" + result +
				", msg='" + msg + '\'' +
				", msgid='" + msgid + '\'' +
				'}';
	}
}
