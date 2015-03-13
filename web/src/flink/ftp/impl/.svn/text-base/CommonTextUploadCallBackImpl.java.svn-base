package flink.ftp.impl;

import java.io.File;

import org.apache.commons.net.ftp.FTP;

/**
 * HTML及文本文件上传
 * 
 * @Project: Card
 * @File: CommonTextUploadCallBackImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2012-8-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class CommonTextUploadCallBackImpl extends CommonTypeUploadCallBackImpl {

	public CommonTextUploadCallBackImpl(String remotePath, File uploadFile) {
		super(remotePath, uploadFile, Integer.valueOf(FTP.ASCII_FILE_TYPE));
	}

	@Override
	protected boolean needRemoteCheck() {
		return false;
	}

}
