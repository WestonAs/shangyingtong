package flink.util.ftp;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FileTransferClient;

/**
 * @File: FtpList.java
 *
 * @description: FTP相关处理类
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-4-20
 */
public class FtpUtil {

	private static final Logger logger = Logger.getLogger(FtpUtil.class);
	private FileTransferClient client = null;

	public FileTransferClient getClient() {
		return client;
	}

	public void setClient(FileTransferClient client) {
		this.client = client;
	}
	
	/**
	 * 构造方法
	 * @param remoteHost FTP服务器IP
	 * @param user 用户名
	 * @param pwd 密码
	 * @param port 端口
	 * @throws FTPException
	 */
	public FtpUtil(String remoteHost, String user, String pwd, Integer port) throws FTPException{
		client = new FileTransferClient();
		client.setRemoteHost(remoteHost);
		client.setUserName(user);
		client.setPassword(pwd);
		client.getAdvancedSettings().setControlEncoding("GBK");
		client.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
	}
	
	/**
	 * 建立FTP连接
	 * @throws FTPException
	 * @throws IOException
	 */
	public void connect() throws FTPException, IOException{
		client.connect();
	}
	
	/**
	 * 释放连接
	 * @throws FTPException
	 * @throws IOException
	 */
	public void disconnect() throws FTPException, IOException {
		if (client.isConnected()) {
			client.disconnect();
		}
	}
	
	/**
	 * 取得指定目录及其子目录下的文件和目录列表
	 * @param path 指定的目录
	 * @param ftpFiles 文件和目录的列表
	 * @param home FTP服务器用户的根目录
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 * @throws ParseException
	 */
	public List<FtpFile> getFileList(String path, List<FtpFile> ftpFiles,  String home) throws IOException, FTPException, ParseException {
		String currDir = client.getRemoteDirectory();
		try {
			this.changeToParentDirectory(currDir, home);
		} catch (FTPException e) {
			logger.error("目录[" + home + "/" + currDir + "]不存在");
			return Collections.<FtpFile>emptyList();
		}
		
		if (CollectionUtils.isEmpty(ftpFiles)) {
			ftpFiles = new ArrayList<FtpFile>();
		}
		if (StringUtils.isEmpty(path)) {
			return ftpFiles;
		}
		
		String[] temps = path.split("/");
		for (String dir : temps) {
			if (StringUtils.isNotEmpty(dir)) {
				try {
					client.changeDirectory(dir);
				} catch (FTPException e) {
					logger.error("目录[" + home + path + "]不存在");
					return Collections.<FtpFile>emptyList();
				}
			}
		}
		
		FTPFile[] files = client.directoryList();
		for (FTPFile file : files) {
			FtpFile ftpFile = new FtpFile();
			
			ftpFile.setFileName(file.getName());
			if (StringUtils.equals(path, "/")) {
				ftpFile.setFilePath(path + file.getName());
			} else {
				ftpFile.setFilePath(path + "/" + file.getName());
			}
			if (file.isDir()) {
				ftpFile.setDirectory(true);
				ftpFiles.add(ftpFile);
				
				// 递归取得文件列表
				getFileList(ftpFile.getFilePath(), ftpFiles, home);
			} else {
				ftpFile.setDirectory(false);
				ftpFile.setSize(file.size());
				ftpFiles.add(ftpFile);
			}
		}
		
		return ftpFiles;
	}
	
	/**
	 * 切换目录
	 * @param currDir
	 * @param home
	 * @throws FTPException
	 * @throws IOException
	 */
	private void changeToParentDirectory(String currDir, String home) throws FTPException, IOException {
		if (!StringUtils.equals(currDir, home)) {
			String up = currDir.substring(currDir.indexOf(home) + home.length());
			for (int i = 0; i < up.length(); i++) {
				if (up.charAt(i) == '/') {
					client.changeToParentDirectory();
				}
			}
		}
	}
	
	/**
	 * 上传文件到FTP服务器的指定目录
	 * @param localFileName 本地要上传的文件名(包含全路径)
	 * @param remotePath  FTP服务器上的目标路径
	 * @param remoteFileName 上传到FTP服务器的文件名
	 * @return
	 * @throws FTPException
	 * @throws IOException
	 */
	public String upload(String localFileName, String remotePath, String remoteFileName) throws FTPException, IOException {
		String name = "";
		try {
			this.connect();
			client.setContentType(FTPTransferType.BINARY);
			
			// 切换工作目录
			client.changeDirectory(remotePath);
			
			name = client.uploadFile(localFileName, remoteFileName);
		} finally {
			this.disconnect();
		}
		
		return name;
	}
	
	public static void main(String[] args) throws Exception{
		FtpUtil util = new FtpUtil("172.168.9.20", "card", "card", 21);
//			String name = util.upload("D:/opt/301_304.txt", "/home/card/uploadCardImg", "301_304.txt");
//			System.out.println("上传的文件名为:" + name);
		util.connect();
		List<FtpFile> ftpFiles = util.getFileList("/uploadCardImg", null, util.getClient().getRemoteDirectory());
		for (FtpFile ftpFile : ftpFiles) {
			if (!ftpFile.isDirectory()) {
				String path = util.getClient().getRemoteDirectory() + ftpFile.getFilePath();
				path = path.substring(0, path.indexOf(ftpFile.getFileName())-1);
				System.out.println("文件名:" + ftpFile.getFileName());
				System.out.println("文件路径:" + ftpFile.getFilePath());
				System.out.println("path:" + path);
				System.out.println("home:" + util.getClient().getRemoteDirectory());
			}

		}
		util.disconnect();
	}
}
