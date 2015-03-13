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
 * @File: CommonNameDirFilesCallBackImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-7-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CommonNameDirFilesCallBackImpl extends AbstractFtpTransferCallBackImpl {
    private final String[] filePrefixs;

	private final String[] fileSuffixs;

	private final AtomicReference<List<String>> nameFilesRefer = new AtomicReference<List<String>>();

	public CommonNameDirFilesCallBackImpl(String remotePath, String[] filePrefixs, String[] fileSuffixs) {
		super(remotePath);
		this.filePrefixs = filePrefixs;
		this.fileSuffixs = fileSuffixs;
	}

	public boolean doTransfer(FTPClient ftpClient) throws CommunicationException {
		checkRemotePath(this.remotePath);

		List<String> nameList = new LinkedList<String>();

		try {
			loadNameFiles(this.remotePath, ftpClient, nameList, this.filePrefixs, this.fileSuffixs);
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
	  * @throws IOException  
	  * @version: 2011-7-14 下午03:18:05
	  * @See:
	 */
	protected static void loadNameFiles(String remotePath, FTPClient ftpClient, List<String> nameList, String[] filePrefixs,
			String[] fileSuffixs) throws IOException {
		String directory = filterDirectory(remotePath);

		if (ftpClient.changeWorkingDirectory(directory)) {
			FTPFile[] ftpFiles = ftpClient.listFiles();

			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile.isFile()) {
					String fileName = ftpFile.getName();
					if (FileHelper.checkFileName(fileName, filePrefixs, fileSuffixs)) {
						nameList.add(directory.concat(fileName));
					}
				} else if (ftpFile.isDirectory()) {
					String currentDir = ftpFile.getName();
					String _directory = filterDirectory(directory.concat(currentDir));
					loadNameFiles(_directory, ftpClient, nameList, filePrefixs, fileSuffixs);
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
