package flink.ftp.impl;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;
import flink.util.FileHelper;
import flink.util.CommonHelper;

/**
  * <p>抽象基于传输类型的上传回调处理类</p>
  * <p>保存下载的文件输入流</p>
  * @Project: Card
  * @File: CommonTypeDownLoadCallBackImpl.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-7-11
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class CommonTypeDownLoadCallBackImpl extends AbstractFtpTransferCallBackImpl {

	private final String remoteFile;
	
	private final Integer fileType;

	private final AtomicReference<InputStream> remoteRefer = new AtomicReference<InputStream>();
	
	protected CommonTypeDownLoadCallBackImpl(String remotePath,String remoteFile, Integer fileType) {
		super(remotePath);
		this.remoteFile = remoteFile;
		this.fileType = fileType;
	}

//	@Override
//	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
//		InputStream remoteInput = null;
//		try {
//			//1.1 查看远端是否有该远程文件
//			if (checkNoRemoteFile(ftpClient, this.remoteFile)) {
//				return false;
//			}
//			
//			//1.2 如果设置了符合的文件传输类型则进行设置
//			if(CommonHelper.containsElement(CHECKED_FILETYPES, this.fileType)) {
//				ftpClient.setFileType(this.fileType.intValue());
//			}
//			
//		    //1.3 获得传输中的STREAM流然后保存到本地结构中
//			remoteInput = ftpClient.retrieveFileStream(remoteFile);
//
//			remoteRefer.set(FileHelper.getByteArrayInputStream(remoteInput));
//
//		} catch (Exception ex) {
//			throw new CommunicationException("文件下载出现异常,目录["
//					+ this.remoteFile + "],文件名[" + this.remoteFile + "],原因[" + ex.getMessage() + "]!");
//		} finally {
//			FileHelper.closeInputStream(remoteInput);
//		}
//
//		return true;
//	}
	
	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		InputStream remoteInput = null;
		
		try {
			//1.1 查看远端是否有该远程文件
			if(checkHasRemoteFile(ftpClient,this.remoteFile)) {
				
				//1.2 如果设置了符合的文件传输类型则进行设置
				if(CommonHelper.containsElement(CHECKED_FILETYPES, this.fileType)) {
					ftpClient.setFileType(this.fileType.intValue());
				}
				
			    //1.3 获得传输中的STREAM流然后保存到本地结构中
				remoteInput = ftpClient.retrieveFileStream(remoteFile);

				remoteRefer.set(FileHelper.getByteArrayInputStream(remoteInput));
				
				return true;
			}
			return false;
		} catch (Exception ex) {
			logger.error("文件下载出现异常",ex);
			throw new CommunicationException("文件下载出现异常,目录["
					+ this.remoteFile + "],文件名[" + this.remoteFile + "],原因[" + ex.getMessage() + "]!");
		} finally {
			FileHelper.closeInputStream(remoteInput);
		}
	}
	
	public InputStream getRemoteReferInputStream() {
		return this.remoteRefer.get();
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

}
