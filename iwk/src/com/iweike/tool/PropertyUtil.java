package com.iweike.tool;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


/**
 * 读取配置文件
 */
public class PropertyUtil {

	private static String url;

	private static Properties properties = new Properties();

	@SuppressWarnings("unused")
	private PropertyUtil() {
	}// 屏蔽无参构造

	public PropertyUtil(String url) {
		PropertyUtil.url = url;
		loadProperty();
	}

	public void loadProperty() {
		try {
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(url));
		} catch (IOException e) {
			System.out.println("读取配置文件" + url + "出错：" + e.getMessage());
		}
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 *            配置文件的键
	 * @return 配置文件的值
	 */
	public String getPropertyValue(String key) {
		String value = (String) properties.get(key);
		if (StringUtils.isNotEmpty(value)) {
			return value;
		} else {
			return "";
		}

	}
}