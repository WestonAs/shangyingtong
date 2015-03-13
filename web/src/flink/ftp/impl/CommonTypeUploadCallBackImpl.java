package flink.ftp.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;
import flink.util.CommonHelper;
import flink.util.FileHelper;

/**
  * <p>抽象基于传输类型的上传回调处理类</li>
  * @Project: Card
  * @File: CommonTypeUploadCallBackImpl.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-7-12
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public abstract class CommonTypeUploadCallBackImpl extends AbstractFtpTransferCallBackImpl{
	private final File uploadFile;
	
	private final String uploadFileName;
	
	private final Integer fileType;

	protected CommonTypeUploadCallBackImpl(String remotePath, File uploadFile, Integer fileType) {
		super(remotePath);
		this.uploadFile = uploadFile;
		this.uploadFileName = null;
		this.fileType = fileType;
	}
	
	protected CommonTypeUploadCallBackImpl(String remotePath, File uploadFile, String uploadFileName, Integer fileType) {
		super(remotePath);
		this.uploadFile = uploadFile;
		this.uploadFileName = uploadFileName;
		this.fileType = fileType;
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		InputStream uploadInputStream = null;
		try {
			uploadInputStream = FileHelper.getBufferedInputStream(uploadFile);
			
			String fileName = this.uploadFileName==null ? FileHelper.getFullFileName(uploadFile) : this.uploadFileName;
			
			if(needRemoteCheck()) {
				if(checkHasRemoteFile(ftpClient,fileName)) {
					return false;
				}
			}
			
			if(CommonHelper.containsElement(CHECKED_FILETYPES, this.fileType)) {
				ftpClient.setFileType(this.fileType.intValue());
			}
			
			return ftpClient.storeFile(fileName, uploadInputStream);

		} catch (Exception ex) {
			throw new CommunicationException("上传文件处理失败,原因[" + ex.getMessage() + "]");
		} finally {
			FileHelper.closeInputStream(uploadInputStream);
		}
	}

	@Override
	protected boolean needCreateRemotePath() {
	    return true;
	}
	
	/**
	 * <p>判断是否需要进行上传目录文件检查</p>
	 */
	protected abstract boolean needRemoteCheck();
}
