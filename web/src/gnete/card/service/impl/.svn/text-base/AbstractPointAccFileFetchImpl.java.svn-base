package gnete.card.service.impl;

import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.card.entity.type.PointAccTransYype;
import gnete.card.service.IPointAccFileFetch;
import gnete.etc.Constants;
import gnete.etc.RuntimeBizException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

/**
 * <p>针对潮州移动文件获取的处理类与FTP回调模板接口关联,传入FTPClient接口</p>
 * @Project: Card
 * @File: PointAccFileFetchImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class AbstractPointAccFileFetchImpl implements IPointAccFileFetch {
	protected final Log log = LogFactory.getLog(getClass());

	protected final int MAX_SEQ = 20;

	protected final String[] MAX_ARRAY = getMaxSeqArray();

	protected final int FILE_PREFIX_LENGTH = 3;

	protected final int DATE_PREFIX_PART = 0;

	protected final int TRADE_PREFIX_PART = 1;

	protected final int SEQ_PREFIX_PART = 2;

	protected final int DEFAULT_PAGE = 25;

	protected final int ZIP_MAX_SIZE = 15 * 1024 * 1024;

	protected final int TXT_MAX_SIZE = 2 * 1024 * 1024;
	
	public Map getPointAccFileStream(FTPClient ftpClient, String date) throws IOException, 
			RuntimeBizException {
		return getTradeSuffixStreamMap(ftpClient, date);
	}

	// ------------------------------------------------根据日期提取相关交易的文件集合---------------------------------------------------------
	private Map getTradeSuffixStreamMap(FTPClient ftpClient, String date) throws IOException, RuntimeBizException {

		Map<File, Map<String, File>> pointAccFilesMap = getPointAccFilesMap(ftpClient, date);

		Map tradeSuffixStreamMap = getTradeSuffixStreamMap(pointAccFilesMap);
		
		return (CommonHelper.checkIsEmpty(tradeSuffixStreamMap)) ? Collections.emptyMap() : tradeSuffixStreamMap;
	}

	/**
	  * <p>
	  *   <li>根据得到的文件集合构造出TradeSuffixMap</li>
	  *   <li>String: tradeSuffix, List<Object[fileName,InputStream]></li>
	  * </p>  
	  * @param pointAccFilesMap
	  * @return
	  * @throws IOException  
	  * @version: 2011-4-20 下午06:26:20
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	private Map getTradeSuffixStreamMap(Map<File, Map<String, File>> pointAccFilesMap) throws IOException {

		Map tradeSuffixStreamMap = CommonHelper.createDefaultMultiMap();
		
		for(Iterator<Map.Entry<File, Map<String,File>>> iterator = pointAccFilesMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<File, Map<String,File>> entry = iterator.next();
			File tempDir = entry.getKey();
			
			Map<String,File> filesMap = entry.getValue();
			
			if(CommonHelper.checkIsEmpty(filesMap)) {
				continue;
			}
			
			try {
				for(Iterator<Map.Entry<String, File>> _iterator = filesMap.entrySet().iterator(); _iterator.hasNext();) {
					Map.Entry<String,File> _entry = _iterator.next();
					String fileName = _entry.getKey();
					File file = _entry.getValue();
					
					String tradeSuffix = getAccFilePrefixPart(fileName, TRADE_PREFIX_PART);

					tradeSuffixStreamMap.put(tradeSuffix, new Object[] {fileName,FileHelper.getBufferedInputStream(file)});
				}
			}finally {
				FileHelper.deleteFile(tempDir);
			}			
			
		}

		return tradeSuffixStreamMap;
	}

	/**
	 * 
	 * <p>处理压缩文件名对应的文件提取</p>
	 * @param zipFileName
	 * @param ftpClient
	 * @param tempDir
	 * @return
	 * @throws IOException
	 * @version: 2011-4-20 下午05:55:10
	 * @See:
	 */
	protected Map<String, File> getFilesInZip(String zipFileName, FTPClient ftpClient, File tempDir) throws IOException {
		OutputStream localOutput = FileHelper.getBufferedOutputStream(tempDir, zipFileName);

		try {
			boolean flag = ftpClient.retrieveFile(zipFileName, localOutput);

			if (flag) {
				Map<String, File> filesInZip = new HashMap<String, File>();

				File localZipFile = FileHelper.getFile(tempDir, zipFileName);

				Map<String, File> zipFileMap = FileHelper.getUnZipFileMap(localZipFile, tempDir,
						new String[] { Constants.TXT_SUFFIX });

				if (checkZipFileName(FileHelper.getFileName(zipFileName), zipFileMap.keySet())) {
					filesInZip.putAll(zipFileMap);
				}

				return filesInZip;
			}

			return Collections.<String, File> emptyMap();
		} finally {
			FileHelper.closeOutputStream(localOutput);
		}

	}

	/**
	 * 
	 * <p>处理文本文件对应的文件提取</p>
	 * @param txtFileName
	 * @param ftpClient
	 * @param tempDir
	 * @return
	 * @throws IOException
	 * @version: 2011-4-20 下午05:55:14
	 * @See:
	 */
	protected Map<String, File> getFileInDir(String txtFileName, FTPClient ftpClient, File tempDir) throws IOException {
		OutputStream localOutput = FileHelper.getBufferedOutputStream(tempDir, txtFileName);

		try {
			boolean flag = ftpClient.retrieveFile(txtFileName, localOutput);

			Map<String, File> fileInDir = new HashMap<String, File>(1);

			if (flag) {
				File localTxtFile = FileHelper.getFile(tempDir, txtFileName);

				fileInDir.put(txtFileName, localTxtFile);
				
				return fileInDir;
			}

			return Collections.<String, File> emptyMap();

		} finally {
			FileHelper.closeOutputStream(localOutput);
		}
	}

	/**
	 * 
	 * <p>获得临时文件夹目录</p>
	 * @return
	 * @version: 2011-4-14 上午11:04:29
	 * @See:
	 */
	protected File getTempOutputDir() {
	    return FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));
	}

	/**
	 * 
	 * <p>Zip格式文件中的文件需与ZIP保持一致</p>
	 * @param zipFileName
	 * @param fileNameList
	 * @return
	 * @version: 2011-4-14 上午09:11:17
	 * @See:
	 */
	protected boolean checkZipFileName(String zipFileName, Collection<String> fileNameList) {
		boolean result = true;
		for (String fileName : fileNameList) {
			if (!fileName.startsWith(zipFileName)) {
				result = false;
				break;
			}
		}
		return result;
	}

	// ---------------------------------------------------------文件后缀及其名称处理----------------------------------------------------------------------------
	/**
	 * <p> 
	 * <li>获得该交易目录当天可支持处理的文件(由后缀)</li> 
	 * <li>需检查文件的命名规则以及文件大小</li>
	 * <li>返回本地生成的临时目录及对应的文件集合</i>
	 * <li>注意下述按照分页读取的方式(提供性能)</li>
	 * </p>       
	 * @param ftpClient
	 * @param date
	 * @return
	 * @throws IOException
	 * @throws RuntimeBizException
	 * @version: 2011-4-14 上午12:39:20
	 * @See:
	 */
	private Map<File, Map<String, File>> getPointAccFilesMap(FTPClient ftpClient, String date) throws IOException,RuntimeBizException {
		String checkedPrefix = getCheckedPrefix(date);

		FTPListParseEngine engine = ftpClient.initiateListParsing();

		Map<File, Map<String, File>> pacFilesMap = new HashMap<File, Map<String, File>>();

		while (engine.hasNext()) {
			//1.1 分页读取
			FTPFile[] files = engine.getNext(DEFAULT_PAGE);

			File tempDir = getTempOutputDir();
			
			int abnormalCount = 0;
			
			Map<String, File> filesMap = new HashMap<String, File>(DEFAULT_PAGE);

			if ((files != null) && (files.length != 0)) {
				for (FTPFile pathFile : files) {
					//1.2 检查并返回文件集合
					String[] checkedReturn = getCheckedPointAccFile(pathFile, checkedPrefix);

					if (CommonHelper.checkIsEmpty(checkedReturn)) {
						abnormalCount ++;
						continue;
					}

					String fileName = checkedReturn[0];
					String fileSuffix = checkedReturn[1];

					//1.3 根据返回集合（文件名,文件后缀）
					if (fileSuffix.equals(Constants.ZIP_SUFFIX)) {
						//1.3.1 如果是压缩文件
						Map<String, File> filesInZip = getFilesInZip(fileName, ftpClient, tempDir);
						if (CommonHelper.checkIsNotEmpty(filesInZip)) {
							filesMap.putAll(filesInZip);
						}

					} else if (fileSuffix.equals(Constants.TXT_SUFFIX)) {
						//1.3.2 如果是文本文件
						Map<String, File> fileInDir = getFileInDir(fileName, ftpClient, tempDir);
						if (CommonHelper.checkIsNotEmpty(fileInDir)) {
							filesMap.putAll(fileInDir);
						}
					}

				}
			}
			
			if(abnormalCount > DEFAULT_PAGE / 2) {
				throw new RuntimeBizException("提取文件目录下存在过多问题,请仔细查看,相关日期[" + date +"]！");
			}

			pacFilesMap.put(tempDir, filesMap);

		}

		return pacFilesMap;
	}

	/**
	 * <p>
	 *   <li>20110412_01_00.txt</li>
	 *   <li>获得最基本的检查部分20110412_
	 * </p>  
	 * @param date
	 * @return
	 * @version: 2011-4-14 上午09:12:27
	 * @See:
	 */
	protected String getCheckedPrefix(String date) {
		return new StringBuilder(50).append(date).append(Constants.FILE_SPLIT).toString();
	}

	/**
	 * 
	 * <p>获得拆解文件中的组合部分值</p>
	 * @param fileName
	 * @param part
	 * @return
	 * @version: 2011-4-15 上午09:08:25
	 * @See:
	 */
	private String getAccFilePrefixPart(String fileName, int part) {
		String prefixFileName = FileHelper.getFileName(fileName);

		String[] array = prefixFileName.split(Constants.FILE_SPLIT);

		if (array.length == FILE_PREFIX_LENGTH) {
			return array[part];
		}

		return "";
	}

	/**
	 * 
	 * <p>对文件进行必要的检查</p>
	 * @param pathFile
	 * @param checkedPrefix
	 * @return
	 * @version: 2011-4-20 下午05:02:55
	 * @See:
	 */
	protected String[] getCheckedPointAccFileDefault(FTPFile pathFile, String checkedPrefix) {
		if (pathFile.isDirectory()) {
			log.warn("=====上传目录中提取的文件存在目录，该目录无法进行文件获取!======");
			return new String[] {};
		}

		String fileName = pathFile.getName();
		String filePrefix = FileHelper.getFileName(fileName);
		String fileSuffix = FileHelper.getFileExtension(fileName);

		if (!checkPointAccFilePrefix(filePrefix)) {
			log.warn("文件名前缀不符合规范,提取前缀为[" + filePrefix + "],文件名[" + fileName + "]");
			return new String[] {};
		}

		if (!checkPointAccFileSuffix(fileSuffix)) {
			log.warn("文件名后缀不符合规范,提取后缀为[" + fileSuffix + "],文件名[" + fileName + "]");
			return new String[] {};
		}

		long fileSize = pathFile.getSize();

		if (!checkPointAccFileSize(fileSuffix, fileSize)) {
			log.warn("文件大小不符合规范[" + fileSuffix + "],文件名[" + fileName + "]");
			return new String[] {};
		}

		if (CommonHelper.isNotEmpty(checkedPrefix)) {
			return (fileName.startsWith(checkedPrefix)) ? new String[] { fileName, fileSuffix } : new String[] {};
		}

		return new String[] { fileName, fileSuffix };
	}

	/**
	 * <p> 
	 *   <li>检查文件是否符合规范:日期_交易_编号</li> 
	 *   <li>如果没有压缩文件，则交易必须是01~04之间的，编号在给定范围以内的</li>
	 * </p>   
	 * @param filePrefix
	 * @return
	 * @throws RuntimeBizException
	 * @version: 2011-4-20 下午05:19:22
	 * @See:
	 */
	protected boolean checkPointAccFilePrefix(String filePrefix) throws RuntimeBizException {
		String[] array = filePrefix.split(Constants.FILE_SPLIT);

		if (array.length == FILE_PREFIX_LENGTH) {
			String trade = array[TRADE_PREFIX_PART];
			String seq = array[SEQ_PREFIX_PART];

			if (PointAccTransYype.valueOf(trade) != null) {
				if (CommonHelper.containsElement(MAX_ARRAY, seq)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * <p>检查后缀是zip或者txt文件</p>
	 * @param fileSuffix
	 * @return
	 * @version: 2011-4-20 下午05:19:53
	 * @See:
	 */
	protected boolean checkPointAccFileSuffix(String fileSuffix) {
		return (fileSuffix.equals(Constants.ZIP_SUFFIX) || fileSuffix.equals(Constants.TXT_SUFFIX));
	}

	/**
	 * 
	 * <p>检查文件大小</p>
	 * @param fileSuffix
	 * @param fileSize
	 * @return
	 * @version: 2011-4-20 下午05:20:12
	 * @See:
	 */
	protected boolean checkPointAccFileSize(String fileSuffix, long fileSize) {
		if (fileSuffix.equals(Constants.ZIP_SUFFIX)) {
			return fileSize < getMaxZipFileSize();
		}

		if (fileSuffix.equals(Constants.TXT_SUFFIX)) {
			return fileSize < getMaxTxtFileSize();
		}

		return false;
	}

	/**
	 * 
	 * <p>对文件进行检查</p>
	 * @param filePrefix
	 * @return
	 * @version: 2011-4-14 下午02:32:27
	 * @See:
	 */
	protected abstract String[] getCheckedPointAccFile(FTPFile pathFile, String checkedPrefix);

	/**
	 * 
	 * @description：<li>设定序列号的范围</li>
	 * @return
	 * @version: 2011-4-14 下午02:32:31
	 * @See:
	 */
	protected abstract String[] getMaxSeqArray();

	/**
	 * 
	 * @description：<li>设置ZIP文件的最大值</li>
	 * @return
	 * @version: 2011-4-20 下午05:59:06
	 * @See:
	 */
	protected abstract long getMaxZipFileSize();

	/**
	 * 
	 * <p>设置文本文件的最大值</p>
	 * @return
	 * @version: 2011-4-20 下午05:59:08
	 * @See:
	 */
	protected abstract long getMaxTxtFileSize();

}
