package flink.ftp.impl;

import java.io.File;

import org.apache.commons.net.ftp.FTP;

/**
 * 
  * @Project: Card
  * @File: CommonWriteUploadCallBackImpl.java
  * @See:
  * @description：
  *     <li>缺省采用binary的传输方式</li>
  *    <li>检查远端路径下是否存在该文件(存在与否均进行上传)</li>
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-7-11
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class CommonWriteUploadCallBackImpl extends CommonTypeUploadCallBackImpl {

	public CommonWriteUploadCallBackImpl(String remotePath, File uploadFile) {
		super(remotePath, uploadFile, Integer.valueOf(FTP.BINARY_FILE_TYPE));
	}
	public CommonWriteUploadCallBackImpl(String remotePath, File uploadFile, String uploadFileName) {
		super(remotePath, uploadFile, uploadFileName, Integer.valueOf(FTP.BINARY_FILE_TYPE));
	}

	@Override
	protected boolean needRemoteCheck() {
		return false;
	}

}
