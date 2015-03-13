package flink.ftp.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;
import flink.util.CommonHelper;
import flink.util.FileHelper;

/**
 * <p>用于数字证书上传等常见即不能以上传文件关联的名称作为名称来保存(文件名称由参数传递进来)</p>
 * @Project: CardSecond
 * @File: CompondUploadCallBackImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-13
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CompondUploadCallBackImpl extends AbstractFtpTransferCallBackImpl {

	private final File uploadFile;

	private final String fileName;

	/**
	 * @param remotePath
	 * @param uploadFile 上传文件
	 * @param fileName   上传指定保存文件名称
	 */
	public CompondUploadCallBackImpl(String remotePath, File uploadFile, String fileName) {
		super(remotePath);
		this.uploadFile = uploadFile;
		this.fileName = fileName;
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		if(CommonHelper.checkIsEmpty(fileName)) {
			return false;
		}
		
		InputStream uploadInputStream = null;
		try {
			uploadInputStream = FileHelper.getBufferedInputStream(uploadFile);

			if (needCreateRemotePath() && checkHasRemoteFile(ftpClient, fileName)) {
				return false;
			}

			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

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

}
