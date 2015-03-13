package flink.ftp.impl;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;

/**
 * <p>将远端目录符合前后缀的文件均进行本地化保存</p>
 * @Project: Card
 * @File: CommonDirFilesCallBackImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-7-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CommonDirFilesCallBackImpl extends AbstractDirFilesCallBackImpl {

	public CommonDirFilesCallBackImpl(String remoteFoldPath, String[] filePrefixs, String[] fileSuffixs) {
		super(remoteFoldPath, filePrefixs, fileSuffixs);
	}
	
	/** 不过滤 */
	public CommonDirFilesCallBackImpl(String remoteFoldPath) {
		super(remoteFoldPath, new String[]{}, new String[]{});
	}

	@Override
	protected String[] getFilterDirs() {
		return new String[] {};
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		return false;
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

}
