package flink.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 *  <p>用于下载处理</p>
  * @Project: Card
  * @File: DownLoadHelper.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-9
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public abstract class DownLoadHelper {

	public static void processDownLoad(HttpServletResponse response, String contentType, String contentHead, File file)
			throws IOException {
		processDownLoad(response, contentType, contentHead, FileHelper.getBufferedInputStream(file));
	}

	public static void processDownLoad(HttpServletResponse response, String contentType, String contentHead,
			String content) throws IOException {
		processDownLoad(response, contentType, contentHead, IOUtils.toInputStream(content));
	}

	public static void processDownLoad(HttpServletResponse response, String contentType, String contentHead,
			String content, String encoding) throws IOException {
		processDownLoad(response, contentType, contentHead, IOUtils.toInputStream(content, encoding));
	}

	public static void processDownLoad(HttpServletResponse response, String contentType, String contentHead,
			byte[] contentArray) throws IOException {
		processDownLoad(response, contentType, contentHead, new ByteArrayInputStream(contentArray));
	}

	public static void processDownLoad(HttpServletResponse response, String contentType, String contentHead,
			InputStream contentStream) throws IOException {
		if(checkInValidDownLoadParams(response,contentType,contentHead,contentStream)) {
			return ;
		}
		
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename=" + contentHead);

		OutputStream output = null;
		try {
			output = response.getOutputStream();
			FileHelper.copy(contentStream, output);
			output.flush();
			response.flushBuffer();
		} finally {
			FileHelper.closeInputStream(contentStream);
			FileHelper.closeOutputStream(output);
		}
	}
	
	private static boolean checkInValidDownLoadParams(HttpServletResponse response, String contentType, String contentHead,
			InputStream contentStream) {
		return (response == null) || (CommonHelper.checkIsEmpty(contentType)) || (CommonHelper.checkIsEmpty(contentHead))
		          || (contentStream == null);
	}
}
