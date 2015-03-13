package flink.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * @File: IOUtil.java
 * 
 * @description: 输入输出工具类
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-31
 */
public abstract class IOUtil {

	static Logger logger = Logger.getLogger(IOUtil.class);
	
	/**
	 * 默认编码格式
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 上传文件到本地目录
	 * 
	 * @param file
	 *            上传的文件
	 * @param fileName
	 *            文件名
	 * @param path
	 *            目录
	 * @return
	 * @throws IOException
	 */
	public static void uploadFile(File file, String fileName, String path)
			throws IOException {
		OutputStream output = null;
		InputStream input = null;
		try {
			output = getBufferedOutputStream(getFile(path, fileName, true));
			input = getBufferedInputStream(file);

			IOUtils.copy(input, output);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}
	
	/**
	 * 将一个文件上传到本地的多个目录
	 * @param file
	 * @param fileName
	 * @param paths
	 * @throws IOException
	 */
	public static void uploadFile(File file, String fileName, String...paths) throws IOException {
		for (int i = 0; i < paths.length; i++) {
			InputStream input = null;
			OutputStream output = null;
			
			try {
				input = getBufferedInputStream(file);
				output = getBufferedOutputStream(getFile(paths[i], fileName, true));
				
				IOUtils.copy(input, output);
			} finally {
				IOUtils.closeQuietly(output);
				IOUtils.closeQuietly(input);
			}
		}
	}

	public static OutputStream getBufferedOutputStream(File file)
			throws IOException {
		return new BufferedOutputStream(FileUtils.openOutputStream(file));
	}

	public static InputStream getBufferedInputStream(File sourceFile)
			throws IOException {
		return new BufferedInputStream(FileUtils.openInputStream(sourceFile));
	}

	/**
	 * 根据目录，文件名及创建标志是否创建相应的文件
	 * 
	 * @param directoryName
	 *            目录
	 * @param fileName
	 *            文件名
	 * @param flag
	 *            是否创建标志
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String directoryName, String fileName,
			boolean flag) throws IOException {
		File dirFile = getDirectoryFile(directoryName);
		File fileInDir = new File(dirFile, fileName);

		if (fileInDir.exists()) {
			return fileInDir;
		}

		if (flag) {
			if (!fileInDir.createNewFile()) {
				throw new IOException("create File [" + fileName + "] fail");
			}
			return fileInDir;
		} else {
			return null;
		}
	}

	/**
	 * 获取文件路径
	 * 
	 * @param directoryName
	 * @return
	 */
	public static File getDirectoryFile(String directoryName) {
		File dir = new File(directoryName);
		if (dir.exists() && (dir.isDirectory())) {
			return dir;
		}
		dir.mkdirs();
		return dir;
	}

	/**
	 * 下载文件
	 * 
	 * @param filePath
	 *            路径 + 文件名（包含完整路径）
	 * @throws IOException
	 */
	public static void downloadFile(String filePath) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String name = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());

		byte[] content = null;
		FileInputStream inPut = null;
		try {
			File file = new File(filePath);
			inPut = new FileInputStream(file);
			content = new byte[inPut.available()];
			inPut.read(content);
		} finally {
			if (inPut != null) {
				inPut.close();
			}
		}

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
			name = new String(name.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器
		else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
			name = URLEncoder.encode(name, "UTF-8"); // IE浏览器
		response.reset();

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/octet-stream;charset=utf8");
		response.setHeader("Content-Disposition:attachment", "document;filename=\"" + name + "\"");
		response.setHeader("Connection", "close");
		ServletOutputStream out = response.getOutputStream();
		out.write(content);
		out.flush();
		out.close();
	}

	/**
	 * 下载文件
	 * 
	 * @param inputStream
	 * @param fileName
	 *            文件名（不包含路径）
	 */
	public static void downloadFile(InputStream inputStream, String fileName)
			throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器
		else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
			fileName = URLEncoder.encode(fileName, "UTF-8"); // IE浏览器
		response.reset();

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + fileName + "");
		response.setHeader("Connection", "close");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			IOUtils.copy(inputStream, out);
			out.flush();
			response.flushBuffer();
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 判断要下载的文件是否存在
	 * 
	 * @param fileName
	 *            路径 + 文件名（包含完整路径）
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		boolean isFileExist = true;
		try {
			File file = new File(fileName);
			if (!file.exists() || file.isDirectory()) {
				isFileExist = false;
			}
		} catch (Exception e) {
			isFileExist = false;
			logger.error("要下载的文件不存在。", e);
		}
		return isFileExist;
	}

	/**
	 * 验证后缀名
	 */
	public static boolean testFileFix(String filename, List<String> fileTypes) {
		// 得到文件尾数并进行小写转换
//		String postfix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		String postfix = StringUtils.lowerCase(StringUtils.substringAfterLast(filename, "."));
		return fileTypes.contains(postfix) ? true : false;
	}
	
	public static boolean testFileFix(String filename, String...fileTypes) {
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < fileTypes.length; i++) {
			list.add(StringUtils.lowerCase(fileTypes[i]));
		}
		
		return testFileFix(filename, list);
	}

	/**
	 * 取得一个目录下的文件及子目录的所有文件(使用递归来实现)
	 * 
	 * @param fileList
	 *            存放文件的List
	 * @param path
	 *            目录
	 * @return
	 */
	public static List<String> getFileList(List<String> fileList, String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		
		if (ArrayUtils.isEmpty(files)) {
			return null;
		}
		
		for (File file : files) {
			if (file.isDirectory()) {
				getFileList(fileList, file.getAbsolutePath());
			} else {
				fileList.add(file.getAbsolutePath());
			}
		}
		
		return fileList;
	}
	
	/**
	 * 解析文本文件，将内容放到一个list中
	 */
	public static List<String> readLines(File file) throws IOException{
		return IOUtils.readLines(new FileInputStream(file), DEFAULT_ENCODING);
	}
	
	/**
	 * 解析文本文件，将内容放到一个list中
	 */
	public static List<String> readLines(File file, String encoding) throws IOException{
		return IOUtils.readLines(new FileInputStream(file), encoding);
	}
	
	/**
	 * 读取上传文件的二进制流
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileByte(File file) throws IOException {
		byte[] fileData;
		try {
			fileData = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(e, e);
			throw new IOException("上传文件时发生FileNotFoundException");
		}
		return fileData;
	}
	
}
