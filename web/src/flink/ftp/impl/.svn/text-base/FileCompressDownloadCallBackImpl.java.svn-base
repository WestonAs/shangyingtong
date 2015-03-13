package flink.ftp.impl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;
import flink.util.CommonHelper;
import flink.util.FileHelper;

/**
 * <p>将远程目录下的文件进行压缩下载(到本地指定目录)</p>
 * <ul
 * <li>1. 将下载的文件压缩成.zip进行输出</li> 
 * <li>2. 在临时目录中生成一个同名的文件名,再生成一个同名后缀为.zip的文件,然后获得输入流</li>
 * </ul>
 * @Project: Card
 * @File: FileCompressDownloadCallBackImpl.java
 * @See:    
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-1-11
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class FileCompressDownloadCallBackImpl extends AbstractFtpTransferCallBackImpl {

	private final String remoteFile;

	private final String localTempPath;

	private final AtomicReference<Object[]> compressInputRefer = new AtomicReference<Object[]>();

	public FileCompressDownloadCallBackImpl(String remotePath, String localTempPath, String remoteFile) {
		super(remotePath);
		this.localTempPath = localTempPath;
		this.remoteFile = remoteFile;
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		try {
			Object[] compressArray = getCompressInput(ftpClient, this.localTempPath, this.remoteFile);

			if (CommonHelper.checkIsEmpty(compressArray)) {
				return false;
			}

			compressInputRefer.set(compressArray);
		} catch (Exception ex) {
			logger.error("远程文件下载并压缩处理失败,原因[" + ex.getMessage()+"]");
			throw new CommunicationException("将远程文件[" + this.remoteFile + "]进行压缩下载处理失败,原因[" + ex.getMessage() + "]");
		}

		return true;

	}

	public Object[] getCompressInput() {
		return this.compressInputRefer.get();
	}

	/**
	 * 
	 * <p>将远程文件进行本地化并得到本地</p>
	 * @param ftpClient
	 * @param localTempPath   本地保存文件目录
	 * @param remoteFile      远程文件名
	 * @return
	 * @throws Exception
	 * @version: 2011-1-11 下午04:11:26
	 * @See:
	 */
	private Object[] getCompressInput(FTPClient ftpClient, String localTempPath, String remoteFile)
			throws Exception {
		boolean retriveFlag = ftpClient.retrieveFile(remoteFile, FileHelper.getBufferedOutputStream(localTempPath,remoteFile));

		Object[] compressInput = null;
		if (retriveFlag) {
			File sourceFile = FileHelper.getFile(localTempPath, remoteFile);
			try {
				File zipFile = getLocaleZipFile(localTempPath, remoteFile);
				FileHelper.copyFile2Zip(sourceFile, zipFile);
				compressInput = new Object[] {FileHelper.getFullFileName(zipFile),FileHelper.getBufferedInputStream(zipFile)};
			} finally {	
				try {
				   FileHelper.forceDeleteFile(sourceFile);	
				}catch(IOException ex) {
					logger.error("删除临时文件[" + sourceFile.getAbsolutePath() + "]失败 ，原因[" + ex.getMessage() + "]");
				}				
			}
		}

		return compressInput;
	}

	/**
	 * 
	 * <p>根据远程文件名，创建本地压缩文件</p>
	 * @param localTempPath  本地目录
	 * @param remoteFile     远程文件名
	 * @return
	 * @version: 2011-1-11 下午03:39:28
	 * @See:
	 */
	private File getLocaleZipFile(String localTempPath, String remoteFile) throws Exception {
		return FileHelper.getFile(this.localTempPath, FileHelper.getCreateFileName(remoteFile, "", "", ".zip"));
	}

	@Override
	protected boolean needCreateRemotePath() {
	    return false;
	}

}
