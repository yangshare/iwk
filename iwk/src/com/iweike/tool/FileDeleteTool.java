package com.iweike.tool;

import java.io.File;

import org.apache.struts2.ServletActionContext;

/**
 * 文件删除工具
 * 
 * @author Administrator
 * 
 */
public class FileDeleteTool  {
	
	public static void deleteFile(String tomcatUrl) {
		// 得到工程保存图片的路径
		@SuppressWarnings("deprecation")
		String root = ServletActionContext.getRequest().getRealPath(tomcatUrl);
		// localFileName = localFileName.replace("\\", "/");
		File localFile = new File(root);
		boolean flag = false;
		if (localFile.isFile() && localFile.exists()) {
			System.out.println("localFile-Path-" + localFile.getPath() + "Ab "
					+ localFile.getAbsolutePath());
			System.out.println("localFileName--" + tomcatUrl);
			flag = localFile.getAbsoluteFile().delete();
		}
		System.out.println("文件名：" + tomcatUrl + "，是否删除成功：" + flag);
	}
}
