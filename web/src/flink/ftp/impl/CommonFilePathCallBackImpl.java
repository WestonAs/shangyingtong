package flink.ftp.impl;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import flink.ftp.CommunicationException;
import flink.util.FileHelper;

/**
 * <p>获取下载文件的全路径></p>
 * <p>保存上述路径到本地结构中</p>
 * @Project: Card
 * @File: CommonFilePathCallBackImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-7-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CommonFilePathCallBackImpl extends AbstractFtpTransferCallBackImpl {
	private final String remoteFile;
	
	private final AtomicReference<String> filePathRefer = new AtomicReference<String>();

	public CommonFilePathCallBackImpl(String remotePath, String remoteFile) {
		super(remotePath);
		this.remoteFile = remoteFile;
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		try {
			if (checkNoRemoteFile(ftpClient, this.remoteFile)) {
				return false;
			}
			
			filePathRefer.set(FileHelper.getFilePath(this.remotePath,this.remoteFile));
		}catch(IOException ex) {
			throw new CommunicationException("路径获取出现异常,目录["
					+ this.remoteFile + "],文件名[" + this.remoteFile + "],原因[" + ex.getMessage() + "]!");
		}
		
		return true;
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

	public String getFilePathRefer() {
		return this.filePathRefer.get();
	}

}
