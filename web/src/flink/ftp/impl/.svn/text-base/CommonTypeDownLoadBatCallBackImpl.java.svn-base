package flink.ftp.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.net.ftp.FTPClient;

import flink.ftp.CommunicationException;
import flink.util.FileHelper;
import flink.util.CommonHelper;

/**
 * 批量下载指定文件列表的文件,文件存储到缓存目录
 * 针对网银通的划付文件列表
 * 
 * @Project: CardWeb
 * @File: CommonTypeDownLoadBatCallBackImpl.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-3-12下午5:38:54
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class CommonTypeDownLoadBatCallBackImpl extends AbstractFtpTransferCallBackImpl {

	private final List<String> remoteFileNames;
	
	private final Integer fileType;

	private final AtomicReference<Object[]> localFilesRefer = new AtomicReference<Object[]>();
	
	public CommonTypeDownLoadBatCallBackImpl(String remotePath, List<String> remoteFileNames, Integer fileType) {
		super(remotePath);
		this.remoteFileNames = remoteFileNames;
		this.fileType = fileType;
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		try {
			//缓存文件
			File tempDir = FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));
			List<Object[]> filesList = new ArrayList<Object[]>();
			
			for(String remoteFileName: remoteFileNames){
				//1.1 查看远端是否有该远程文件
				if(checkHasRemoteFile(ftpClient,remoteFileName)) {
					
					//1.2 如果设置了符合的文件传输类型则进行设置
					if(CommonHelper.containsElement(CHECKED_FILETYPES, this.fileType)) {
						ftpClient.setFileType(this.fileType.intValue());
					}
					
				    //1.3 保存到本地结构中
					Object[] fileInfos = getLocalFileInDir(remoteFileName,ftpClient,tempDir);
					if (CommonHelper.checkIsNotEmpty(fileInfos)) {
						filesList.add(fileInfos);
					}
				}else{
					throw new CommunicationException("远程目录"+remotePath+",文件("+remoteFileName+")不存在!");
				}
			}
			localFilesRefer.set(new Object[]{tempDir,filesList});
			return true;
		} catch (Exception ex) {
			logger.error("文件下载出现异常",ex);
			throw new CommunicationException("文件下载出现异常,目录["+ remotePath + "],文件名[" + remotePath + "],原因[" + ex.getMessage() + "]!");
		} 
	}
	
	/**
	 * 
	 * <p>切换到该目录且生成本地的文件并保存到数组结构中</p>
	 * @param fileName
	 * @param ftpClient
	 * @param tempDir
	 * @return
	 * @throws IOException
	 * @version: 2011-7-8 上午10:37:18
	 * @See:
	 */
	protected static Object[] getLocalFileInDir(String fileName, FTPClient ftpClient, File tempDir) throws IOException {
		OutputStream localOutput = FileHelper.getBufferedOutputStream(tempDir, fileName);

		try {
			boolean flag = ftpClient.retrieveFile(fileName, localOutput);

			if (flag) {
				File localFile = FileHelper.getFile(tempDir, fileName);

				return new Object[] { fileName, localFile };
			}

			return new Object[] {};
		} finally {
			FileHelper.closeOutputStream(localOutput);
		}
	}
	
	public Object[] getLocalFilesRefer() {
		return this.localFilesRefer.get();
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

}
