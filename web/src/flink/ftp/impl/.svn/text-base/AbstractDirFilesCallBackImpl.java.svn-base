package flink.ftp.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import flink.ftp.CommunicationException;
import flink.util.CommonHelper;
import flink.util.FileHelper;

/**
  * <p>抽象远端目录文件下载回调处理</p>
  * <p>将远端目录符合条件的文件(包括子目录)进行本地保存(支持通过文件的前后缀检查处理)</p>
  * <p>本地保存的结构为[本地创建文件夹,所有符合条件的文件集合]</p>
  * @Project: Card
  * @File: AbstractDirFilesCallBackImpl.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-7-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public abstract class AbstractDirFilesCallBackImpl extends AbstractFtpTransferCallBackImpl {
    private final String[] filePrefixs;

	private final String[] fileSuffixs;

	/** tempDir, List<Object[]{fileName, localFile }> */
	private final AtomicReference<Object[]> localFilesRefer = new AtomicReference<Object[]>();

	protected AbstractDirFilesCallBackImpl(String remotePath, String[] filePrefixs, String[] fileSuffixs) {
		super(remotePath);
		this.filePrefixs = filePrefixs;
		this.fileSuffixs = fileSuffixs;
	}

	public boolean doTransfer(FTPClient ftpClient) throws CommunicationException {
		checkRemotePath(this.remotePath);

		File tempDir = FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));

		List<Object[]> filesList = new ArrayList<Object[]>();

		try {
			loadDirFiles(this.remotePath, ftpClient, tempDir, filesList, this.filePrefixs, this.fileSuffixs,getFilterDirs());
		} catch (IOException ex) {
			throw new CommunicationException("提取目录文件[" + this.remotePath + "]处理失败,原因[" + ex.getMessage() + "]");
		}

		if (CommonHelper.checkIsEmpty(filesList)) {
			FileHelper.deleteFile(tempDir);
			
			return false;
		}

		localFilesRefer.set(new Object[] { tempDir, filesList });

		return true;
	}
	
	
	/**
	 * 
	 * <p>通过迭代的方式将文件放入的数组中</p>
	 * @param remotePath
	 * @param ftpClient
	 * @param localTempDir
	 * @param filesMap
	 * @param filePrefixs
	 * @param fileSuffixs
	 * @throws IOException
	 * @version: 2011-7-7 下午08:38:47
	 * @See:
	 */
	protected static void loadDirFiles(String remotePath, FTPClient ftpClient, File localTempDir, List<Object[]> filesList,
			String[] filePrefixs, String[] fileSuffixs,String[] filterDirs) throws IOException {
		String directory = filterDirectory(remotePath);
		
		boolean checkFilterDirs = CommonHelper.checkIsEmpty(filterDirs);

		if (ftpClient.changeWorkingDirectory(directory)) {
			FTPFile[] ftpFiles = ftpClient.listFiles();

			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile.isFile()) {
					String fileName = ftpFile.getName();

					if (FileHelper.checkFileName(fileName, filePrefixs, fileSuffixs)) {
						Object[] localFileDir = getLocalFileInDir(fileName, ftpClient, localTempDir);

						if (CommonHelper.checkIsNotEmpty(localFileDir)) {
							filesList.add(localFileDir);
						}
					}
				} else if (ftpFile.isDirectory()) {
					String currentDir = ftpFile.getName();
					if(checkCurrentDir(checkFilterDirs,currentDir,filterDirs)) {
						String _directory = filterDirectory(directory.concat(currentDir));
						loadDirFiles(_directory, ftpClient, localTempDir, filesList, filePrefixs, fileSuffixs,filterDirs);	
					}					
				}
			}
		}
	}
	
	/**
	 * 
	  *<p>检查目录是否在包含范围之内</p>
	  * @param checkFilterDirs
	  * @param currentDir
	  * @param filterDirs
	  * @return  
	  * @version: 2011-7-8 下午02:39:12
	  * @See:
	 */
	private static boolean checkCurrentDir(boolean checkFilterDirs, String currentDir, String[] filterDirs) {
		if(checkFilterDirs) {
			return true;
		}
		
		return CommonHelper.containsElement(filterDirs, currentDir);
	}
	
	/**
	 * 
	 * <p>切换到该目录且生成本地的文件并保存到数组结构中</p>
	 * @param fileName
	 * @param ftpClient
	 * @param tempDir
	 * @return
	 * @throws IOException
	 * @version: 2011-7-8 上午10:37:18
	 * @See:
	 */
	protected static Object[] getLocalFileInDir(String fileName, FTPClient ftpClient, File tempDir) throws IOException {
		OutputStream localOutput = FileHelper.getBufferedOutputStream(tempDir, fileName);

		try {
			boolean flag = ftpClient.retrieveFile(fileName, localOutput);

			if (flag) {
				File localFile = FileHelper.getFile(tempDir, fileName);

				return new Object[] { fileName, localFile };
			}

			return new Object[] {};
		} finally {
			FileHelper.closeOutputStream(localOutput);
		}
	}

	/** tempDir, List<Object[]{fileName, localFile }> */
	public Object[] getLocalFilesRefer() {
		return localFilesRefer.get();
	}
	
	/**
	 * 
	  * <p>获得需包含处理的目录</p>
	  * @return  
	  * @version: 2011-7-8 下午02:39:53
	  * @See:
	 */
	protected abstract String[] getFilterDirs() ;

}
