package com.iweike.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodeingFilter implements Filter {
	/**
	 * 过滤POST方法乱码
	 */
	private String characterEncoding;
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		request.setCharacterEncoding(characterEncoding);
		response.setCharacterEncoding(characterEncoding);
		chain.doFilter(req, res);
	}

	public void init(FilterConfig config) throws ServletException {
		characterEncoding = config.getInitParameter("encoding");

	}

}
