package com.iweike.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.struts2.ServletActionContext;

public class FileUploadTool {
	
	// 上传图片
	public String uploadImg(File file, String fileFileName,String content){
		// 得到工程保存图片的路径
		@SuppressWarnings("deprecation")
		String root = ServletActionContext.getRequest().getRealPath("/upload/"+content);

		InputStream is;
		OutputStream os;
		// 获取时间戳
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		try {
			
			// 用时间戳命名图片
			fileFileName = str
					+ fileFileName.substring(fileFileName.lastIndexOf("."));
			is = new FileInputStream(file);
			// 保存头像
			fileFileName = System.currentTimeMillis()+"_" + fileFileName;

			// 得到图片保存的位置(根据root来得到图片保存的路径在Tomcat下的该工程里)
			File destFile = new File(root, fileFileName);

			// 把图片写入到上面设置的路径里
			os = new FileOutputStream(destFile);
			byte[] buffer = new byte[400];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.close();
			is.close();
//			System.out.println("返回文件路径=="+destFile.getPath());
			return destFile.getPath();
		} catch (Exception e) {
			System.out.println("文件上传工具类异常：" + e.getMessage());
			e.printStackTrace();
			return "默认图片";
		}
		
	}

}
