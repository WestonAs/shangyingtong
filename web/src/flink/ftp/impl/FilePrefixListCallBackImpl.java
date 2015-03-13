package flink.ftp.impl;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPClient;
import flink.util.CommonHelper;

import java.util.concurrent.atomic.AtomicReference;
import flink.ftp.CommunicationException;

/**
 * <p>获得跟文件关联绑定的某目录下的文件</p>
 * @Project: Card
 * @File: FileListDownloadCallBackImpl.java
 * @See: 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-1-11
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class FilePrefixListCallBackImpl extends AbstractFtpTransferCallBackImpl {

	private String filePrefix;

	private String fileSuffix;

	private boolean prefixFlag;

	private boolean suffixFlag;
	
	private final AtomicReference<List<String>> fileNamesRefer = new AtomicReference<List<String>>();

	public FilePrefixListCallBackImpl(String remotePath, String filePrefix, String fileSuffix) {
		this(remotePath);
		this.filePrefix = filePrefix;
		this.fileSuffix = fileSuffix;
		this.prefixFlag = CommonHelper.isNotEmpty(this.filePrefix);
		this.suffixFlag = CommonHelper.isNotEmpty(this.fileSuffix);
	}

	protected FilePrefixListCallBackImpl(String remotePath) {
		super(remotePath);
	}

	@Override
	protected boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {
		try {
			String[] listFileNames = super.listPathFileNames(ftpClient);
			
			if(CommonHelper.checkIsEmpty(listFileNames)) {
				return false;
			}

			fileNamesRefer.set(filterFileList(listFileNames));

		} catch (java.io.IOException ex) {
			throw new CommunicationException("查找目录[" + this.remotePath + "]下文件失败,原因[" + ex.getMessage() + "]");
		}

		return true;
	}

	public List<String> getListFiles() {
		return this.fileNamesRefer.get();
	}

	private List<String> filterFileList(String[] listFileNames) {
		if (CommonHelper.checkIsEmpty(listFileNames)) {
			return Collections.<String>emptyList();
		}

		List<String> fileNamesList = new ArrayList<String>(listFileNames.length);

		for (String fileName : listFileNames) {
			if (checkFileName(fileName, this.prefixFlag, this.suffixFlag)) {
				fileNamesList.add(fileName);
			}
		}

		return fileNamesList;
	}

	/**
	 *  <p>根据传入的前后缀来判别要提取的文件是否合法</p>
	 *  <p>当传入的前后缀不为空的时候执行相应的前缀或者后缀与文件名是否一致</p>
	  * @param fileName
	  * @param prefixFlag
	  * @param suffixFlag
	  * @return  
	  * @version: 2011-7-12 下午02:42:46
	  * @See:
	 */
	private boolean checkFileName(String fileName, boolean prefixFlag, boolean suffixFlag) {

		if (CommonHelper.checkIsEmpty(fileName)) {
			return false;
		}

		if (prefixFlag && suffixFlag) {
			return (fileName.startsWith(this.filePrefix)) && (fileName.endsWith(this.fileSuffix));
		}

		if (prefixFlag) {
			return fileName.startsWith(this.filePrefix);
		}

		if (suffixFlag) {
			return fileName.endsWith(this.fileSuffix);
		}

		return true;
	}

	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}

}
