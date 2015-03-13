package flink.ftp.impl;

import org.apache.commons.net.ftp.FTP;
import java.io.File;

/**
 * <p>缺省基于binary的上传回调处理类</p>
 * <p>如果上传目录下存在同名文件则不上传</p>
 * @Project: MyCard
 * @File: CommonUploadCallBackImpl.java
 * @See:

 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class CommonUploadCallBackImpl extends CommonTypeUploadCallBackImpl {

	public CommonUploadCallBackImpl(String remotePath, File uploadFile) {
		super(remotePath, uploadFile, Integer.valueOf(FTP.BINARY_FILE_TYPE));
	}

	@Override
	protected boolean needRemoteCheck() {
		return true;
	}

}
