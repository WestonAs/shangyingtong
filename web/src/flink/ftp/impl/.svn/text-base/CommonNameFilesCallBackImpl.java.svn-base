package flink.ftp.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import flink.ftp.CommunicationException;
import flink.util.CommonHelper;
import flink.util.FileHelper;

/**
 * <p>根据前后缀提取远程目录下的所有文件名称</p>
 * @Project: Card
 * @File: CommonNameFilesCallBackImpl.java
 * @See:
 * @author: aps-lib
 * @modified:
 * @Date: 2011-9-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CommonNameFilesCallBackImpl extends AbstractFtpTransferCallBackImpl {
    private final String[] filePrefixs;

	private final String[] fileSuffixs;
	
	private final boolean flag;

	private final AtomicReference<List<String>> nameFilesRefer = new AtomicReference<List<String>>();

	public CommonNameFilesCallBackImpl(String remotePath, String[] filePrefixs, String[] fileSuffixs, boolean flag) {
		super(remotePath);
		this.filePrefixs = filePrefixs;
		this.fileSuffixs = fileSuffixs;
		this.flag = flag;
	}

	public boolean doTransfer(FTPClient ftpClient) throws CommunicationException {
		checkRemotePath(this.remotePath);

		List<String> nameList = new LinkedList<String>();

		try {
			loadNameFiles(this.remotePath, ftpClient, nameList, this.filePrefixs, this.fileSuffixs, this.flag);
		} catch (IOException ex) {
			throw new CommunicationException("提取目录文件[" + this.remotePath + "]处理失败,原因[" + ex.getMessage() + "]");
		}

		if (CommonHelper.checkIsEmpty(nameList)) {

			return false;
		}

		this.nameFilesRefer.set(nameList);

		return true;

	}

	/**
	 *  <p>提取远程目录下名称</p>
	  * @param remotePath
	  * @param ftpClient
	  * @param nameList
	  * @param filePrefixs
	  * @param fileSuffixs
	  * @param flag 是否获取远程目录下的子目录文件开关
	  * @throws IOException  
	  * @version: 2011-7-14 下午03:18:05
	  * @See:
	 */
	protected static void loadNameFiles(String remotePath, FTPClient ftpClient, List<String> nameList, String[] filePrefixs,
			String[] fileSuffixs, boolean flag) throws IOException {
		String directory = filterDirectory(remotePath);

		if (ftpClient.changeWorkingDirectory(directory)) {
			FTPFile[] ftpFiles = ftpClient.listFiles();

			if(flag){ // 获取远程目录所有文件（包含子目录下的文件） 
				for (FTPFile ftpFile : ftpFiles) {
					if (ftpFile.isFile()) {
						String fileName = ftpFile.getName();
						if (FileHelper.checkFileName(fileName, filePrefixs, fileSuffixs)) {
							nameList.add(directory.concat(fileName));
						}
					} else if (ftpFile.isDirectory()) {
						String currentDir = ftpFile.getName();
						String _directory = filterDirectory(directory.concat(currentDir));
						loadNameFiles(_directory, ftpClient, nameList, filePrefixs, fileSuffixs, flag);
					}
				}
			}
			else{ // 获取远程目录所有文件（不包含子目录下的文件） 
				for (FTPFile ftpFile : ftpFiles) {
					if (ftpFile.isFile()) {
						String fileName = ftpFile.getName();
						if (FileHelper.checkFileName(fileName, filePrefixs, fileSuffixs)) {
							nameList.add(directory.concat(fileName));
						}
					} 
				}
			}
			
			
		}
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		return false;
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

	public List<String> getNameFilesRefer() {
		return this.nameFilesRefer.get();
	}

}
