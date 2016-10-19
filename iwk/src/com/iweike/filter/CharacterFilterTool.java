package com.iweike.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.iweike.filter.ParameterRequestWrapper;

public class CharacterFilterTool implements Filter {
	/*
	 * 过滤POST和GET方法乱码
	 */
	

	private String characterEncoding;

	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {

		final HttpServletRequest newRequest = (HttpServletRequest) request;
		if (newRequest.getMethod().equalsIgnoreCase("POST")) {// post提交
			// 将参数设置为utf-8，解决post乱码
			try {
				request.setCharacterEncoding(characterEncoding);
			} catch (UnsupportedEncodingException e) {
				System.err
						.println("不支持的编码/ncause by:line33 in ShowParamFilter.java");
			}
			response.setCharacterEncoding(characterEncoding);
		}
		if (newRequest.getMethod().equalsIgnoreCase("GET")) {// get提交,用if而不用else，避免处理服务器反馈的response
			HashMap paramsMap = new HashMap(request.getParameterMap());
			for (Object key : paramsMap.keySet()) {
				// 注意，此次，通过key来获取value应该使用request.getParameter(key.toString())，
				// 而不是paramsMap.get(key.toString()),因为paramsMap.get获取的将是LString类型而不是String
				try {
					paramsMap.put(
							key.toString(),
							new String[] { new String((request.getParameter(key
									.toString())).getBytes("ISO8859-1"),
									"UTF-8") });
				} catch (UnsupportedEncodingException e) {
					System.err
							.println("不支持的编码/ncause by:line47 in ShowParamFilter.java");
				}
			}
			request = new ParameterRequestWrapper(newRequest, paramsMap);// 将新合成的Request赋给原来的request，让他继续前往后台
		}

		try {
			chain.doFilter(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig config) throws ServletException {
		characterEncoding = config.getInitParameter("encoding");
	}

}
