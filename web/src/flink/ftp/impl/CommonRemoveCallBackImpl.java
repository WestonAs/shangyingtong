package flink.ftp.impl;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;
import flink.util.CommonHelper;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
/**
 * <p>将远程文件从工作目录中移除掉</p>
 * <p>保存移除文件的返回结果[未移除的文件列表,成功移除的文件列表]</p>
 * @Project: Card
 * @File: CommonRemoveCallBackImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-26
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CommonRemoveCallBackImpl extends AbstractFtpTransferCallBackImpl {

	private final String[] removeFileNames;

	private final AtomicReference<Object[]> removeResultRefer = new AtomicReference<Object[]>();

	public CommonRemoveCallBackImpl(String remotePath, String[] removeFileNames) {
		super(remotePath);
		this.removeFileNames = removeFileNames;
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		if (CommonHelper.checkIsEmpty(this.removeFileNames)) {
			logger.warn("====待移除的文件名不能为空!======");
			return false;
		}

		List<String> failRemoveList = new ArrayList<String>();
		List<String> successRemoveList = new ArrayList<String>();
        try {

			for (String removeFile : removeFileNames) {
				if (CommonHelper.checkIsEmpty(removeFile)) {
					continue;
				}

				if (!super.checkHasRemoteFile(ftpClient, removeFile)) {
					failRemoveList.add(removeFile);
					continue;
				}

				boolean result = ftpClient.deleteFile(removeFile);

				if (result) {
					successRemoveList.add(removeFile);
				} else {
					failRemoveList.add(removeFile);
				}
			}

			this.removeResultRefer.set(new Object[] {failRemoveList,successRemoveList});
		} catch (IOException ex) {
			throw new CommunicationException("删除文件处理失败,原因[" + ex.getMessage() + "]");
		}
		
		return failRemoveList.isEmpty();
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

	public Object[] getFileRemoveResult() {
		return this.removeResultRefer.get();
	}

}
