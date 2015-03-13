package flink.ftp.impl;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;

/**
 * <p>将远端目录中符合条件的子目录(以及满足前后缀要求)的文件进行本地化保存</p>
 * @Project: Card
 * @File: CommonFilterDirFilesCallBackImpl.java
 * @See: CommonDirFilesCallBackImpl
 * @description： 将指定目录下有关子目录下的文件进行收集处理
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-7-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CommonFilterDirFilesCallBackImpl extends AbstractDirFilesCallBackImpl {

	private final String[] filterDirs;

	public CommonFilterDirFilesCallBackImpl(String remotePath, String[] filePrefixs, String[] fileSuffixs, String[] filterDirs) {
		super(remotePath, filePrefixs, fileSuffixs);
		this.filterDirs = filterDirs;
	}

	@Override
	protected String[] getFilterDirs() {
		return filterDirs;
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
