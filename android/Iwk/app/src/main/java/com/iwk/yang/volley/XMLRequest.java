package com.iwk.yang.volley;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * 服务器以XML格式返回数据
 * @author Yan
 */
public class XMLRequest extends Request<XmlPullParser>{
	private Listener<XmlPullParser> mListener;
	//超时时间，默认10秒
	private int defaultHttpTimeOut=10*1000;
	//请求参数
	private Map<String,String> methodBody;

	public XMLRequest(String url, Map<String, String> methodBoby, Listener<XmlPullParser> listener,
					  ErrorListener errorListener){
		super(Method.POST, url, errorListener);
		//不启用缓存
		setShouldCache(false);
		mListener=listener;
		this.methodBody=methodBoby;
		// 设置重连策略
		this.setRetryPolicy(new DefaultRetryPolicy(defaultHttpTimeOut,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	public XMLRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
		super(Method.GET, url,  errorListener);
		mListener=listener;
		//不启用缓存
		setShouldCache(false);
		// 设置重连策略
		this.setRetryPolicy(new DefaultRetryPolicy(defaultHttpTimeOut,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}
	/**
	 * 设置请求参数
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if(methodBody==null){
			return super.getParams();
		}
		//创建一个集合，保存请求参数
		Map<String,String> map=new LinkedHashMap<>();
		//----此处可以添加多个通用参数
		//map.put(key,value);
		//------
		//------
		//遍历集合
		Iterator<Map.Entry<String,String>> iter=methodBody.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, String> entry=iter.next();
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * 解析服务器返回的数据
	 */
	@Override
	protected Response<XmlPullParser> parseNetworkResponse(
			NetworkResponse response) {
		try {  
			String xmlString = new String(response.data,  
					HttpHeaderParser.parseCharset(response.headers));  
			//创建解析工厂
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
			//获取解析器
			XmlPullParser xmlPullParser = factory.newPullParser();  
			//设置解析数据
			xmlPullParser.setInput(new StringReader(xmlString));  
			return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));  
		} catch (UnsupportedEncodingException e) {  
			return Response.error(new ParseError(e));  
		} catch (XmlPullParserException e) {  
			return Response.error(new ParseError(e));  
		}  
	}

	/**
	 * 分发结果
	 */
	@Override
	protected void deliverResponse(XmlPullParser response) {
		mListener.onResponse(response);
	}


}
