package gnete.card.clear2Pay;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.CommonTextUploadCallBackImpl;
import flink.ftp.impl.CommonTypeDownLoadBatCallBackImpl;
import flink.ftp.impl.CommonWriteUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.card.dao.CostRecordDAO;
import gnete.card.dao.MerchTransRmaDAO;
import gnete.card.entity.CostRecord;
import gnete.card.entity.MerchTransRma;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>网银通划账文件处理接口</p>
 * @Project: Card
 * @File: Clear2PayInfoBankFileProcessImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified: aps-lib
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-16
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Service("clear2PayBankFileProcess")
public class Clear2PayInfoBankFileProcessImpl implements IClear2PayBankFileProcess {
	private static final Logger logger = LoggerFactory.getLogger(Clear2PayInfoBankFileProcessImpl.class);

	@Autowired
	private MerchTransRmaDAO merhTransRmaDAO;
	@Autowired
	private CostRecordDAO costRecordDAO;
	@Autowired
    private IClear2PayInfoGenerate clear2PayInfoGenerate;

//	private static final String MERCH_REMIT_KEY = "busiOrderNo".toUpperCase();
//
//	private static final String RMA_MAPSEARCH_KEY = "rmaList";
//
//	private static final int SINGLEDATE_SIZE = 1;
//
//	private static final int ARRANGEDATE_SIZE = 2;
//
//	private static final String[] DEFAULT_PREFIXS =  { IClear2PayInfoGenerate.FILE_RAM };
//
//	private static final String[] DEFAULT_SUFFIXS =  { IClear2PayInfoGenerate.FILE_SUFFIX };

	private final IClear2PayFileCompondProcess compondProcess = new Clear2PayFileCompondProcessImpl();

	/**
	 * 生成网银通划付(机构划付给商户)文件
	 *  文件名:branchCode_yyyyMMdd_bankNo.txt : bankNo为前3位
	 *  按机构号生成文件,按recv_acc_no不同文件内可能有多条记录
	 */
	@SuppressWarnings("rawtypes")
	public boolean processClear2PayInfoBankBranchFile(String rmaDate) throws BizException {
		try {
			Map merchStatisResultMap = this.merhTransRmaDAO.findMerchTransMap(rmaDate, MERCH_REMIT_KEY);

			Object[] localClear2BankFileResult = this.clear2PayInfoGenerate.getClear2PayBankBranchFileResult(rmaDate,
					merchStatisResultMap);

			if (CommonHelper.checkIsEmpty(localClear2BankFileResult)) {
				logger.warn("======没有获取到针对日期[{}]网银通划付(机构划付给商户)文件!============", rmaDate);
				return false;
			}

			logger.info("获取网银通划付(机构划付给商户)支付结果处理信息{}", CommonHelper.safe2Str(localClear2BankFileResult));

			String[] remoteParams = getRemoteBranchParams();//区别于其他生成文件的方法
			return uploadClear2BankFileResult(localClear2BankFileResult, remoteParams);

		} catch (DataAccessException ex) {
			throw new BizException("获取网银通划付(机构划付给商户)支付文件异常", ex);
		} catch (CommunicationException ex) {
			throw new BizException("上传网银通划付(机构划付给商户)支付文件异常", ex);
		}

	}
	
	public boolean processClear2PayInfoBankSingleProductFile(String rmaDate) throws BizException {
		return processClear2PaySingleProductMakeCardFile(rmaDate) & processClear2PaySingleProductPlanFile(rmaDate);
	}
	
	/**
	 * 生成网银通单机产品（制卡费）划付对账文件，同时上传到FTP服务器
	 * 
	 * @param rmaDate
	 * @return
	 * @throws BizException
	 */
	private boolean processClear2PaySingleProductMakeCardFile(String rmaDate) throws BizException {
		try {
			Map resultMap = this.costRecordDAO.findClear2PayMakeCardMap(rmaDate, MERCH_REMIT_KEY);
			
			Object[] localClear2BankFileResult = this.clear2PayInfoGenerate.getClear2PayBankFileTotalResult(rmaDate, 
					resultMap, IClear2PayInfoGenerate.MAKE_CARD_RAM);

			if (CommonHelper.checkIsEmpty(localClear2BankFileResult)) {
				logger.warn("======没有获取到针对日期[" + rmaDate + "]网银通单机产品（制卡费）划付对账文件!============");
				return false;
			}

			logger.info("获取网银通单机产品划账文件（制卡费）划账支付结果处理信息" + CommonHelper.safe2Str(localClear2BankFileResult));
			
			return uploadClear2BankFileSingleProductResult(rmaDate, localClear2BankFileResult);
		} catch (DataAccessException ex) {
			logger.error("获取网银通单机产品（制卡费）划付对账结果异常。", ex);
			throw new BizException("获取网银通单机产品（制卡费）划付对账结果异常,原因[" + ex.getMessage() + "]");
		} catch (CommunicationException ex) {
			logger.error("上传网银通单机产品（制卡费）划付对账文件异常。", ex);
			throw new BizException("上传网银通单机产品（制卡费）划付对账文件异常,原因[" + ex.getMessage() + "]");
		}
	}

	/**
	 * 生成网银通单机产品（套餐费）划付对账文件，同时上传到FTP服务器
	 * 
	 * @param rmaDate
	 * @return
	 * @throws BizException
	 */
	private boolean processClear2PaySingleProductPlanFile(String rmaDate) throws BizException {
		try {
			Map resultMap = this.costRecordDAO.findClear2PayPlanMap(rmaDate, MERCH_REMIT_KEY);
			
			Object[] localClear2BankFileResult = this.clear2PayInfoGenerate.getClear2PayBankFileTotalResult(rmaDate, 
					resultMap, IClear2PayInfoGenerate.PLAN_RAM);
			
			if (CommonHelper.checkIsEmpty(localClear2BankFileResult)) {
				logger.warn("======没有获取到针对日期[" + rmaDate + "]网银通单机产品（套餐费）划付对账文件!============");
				return false;
			}
			
			logger.info("获取网银通单机产品划账文件（套餐费）划账支付结果处理信息" + CommonHelper.safe2Str(localClear2BankFileResult));
			
			return uploadClear2BankFileSingleProductResult(rmaDate, localClear2BankFileResult);
		} catch (DataAccessException ex) {
			logger.error("获取网银通单机产品（套餐费）划付对账结果异常。", ex);
			throw new BizException("获取网银通单机产品（套餐费）划付对账结果异常,原因[" + ex.getMessage() + "]");
		} catch (CommunicationException ex) {
			logger.error("上传网银通单机产品（套餐费）划付对账文件异常。", ex);
			throw new BizException("上传网银通单机产品（套餐费）划付对账文件异常,原因[" + ex.getMessage() + "]");
		}
	}

	/**
	 * 合并选中的指定日期(范围)划付文件,并进行上传
	 * 针对 网银通划付文件下载
	 * @Date 2013-3-12下午5:01:52
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String processRmaFileCompondAndUpload(List<String> fileFullPaths) throws CommunicationException, BizException {
		// 1.1 解释路径信息
		Object[] ftpPathInfos = parseFilePathInfo(fileFullPaths);
		String filePath =(String) ftpPathInfos[0];
		List<String> fileNames =(List<String>)ftpPathInfos[1];
		String compondFileName = (String) ftpPathInfos[2];

		// 1.2 构造回调处理(用于根据目录和范围得到相关数据)
		String[] remoteParams = getRemoteBranchParams();
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		//1.3 FTP下载文件
		CommonTypeDownLoadBatCallBackImpl callBack = new CommonTypeDownLoadBatCallBackImpl(filePath,fileNames,Integer.valueOf(FTP.BINARY_FILE_TYPE));

		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);

		if (flag) {
			Object[] localFileRefer = callBack.getLocalFilesRefer();

			if (CommonHelper.checkIsEmpty(localFileRefer)) {
				return "";
			}

			// 1.3 根据返回值将下载的文件进行提炼并合并成本地文件
			File tempDir = (File) localFileRefer[0];

			try {
				List<Object[]> fileArrayList = (List<Object[]>) localFileRefer[1];
				String compondResult = compondProcess.getClear2PayFileCompondResult(tempDir, compondFileName, fileArrayList);

				if (CommonHelper.checkIsEmpty(compondResult)) {
					throw new BizException("无法合并[" + compondFileName + "]划账文件");
				}

				// 1.4 将合并的本地的文件进行上传处理
				remoteParams[0] = filePath;//修正合并文件存放目录
				boolean upload = uploadLocalCompondFile(tempDir, compondResult, remoteParams);

				return upload ? FileHelper.getFilePath(remoteParams[0], compondResult) : "";
			} finally {
				FileHelper.deleteFile(tempDir);
			}
		}

		return "";
	}
	
	/**
	 * 解释生成 文件路径, 文件名列表, 本地合并文件名
	 * 文件名格式 : branchCode_yyyyMMdd_bankNo.txt
	 * @Date 2013-3-12下午5:18:11
	 * @return Object[]
	 */
	private Object[]  parseFilePathInfo(List<String> fileFullPaths){
		
		String filePath = null;
		List<String> fileNameList = new ArrayList<String>(fileFullPaths.size());
		String compondFileName = null;
		
		for(String fileFullPath: fileFullPaths){
			if(CommonHelper.isEmpty(filePath)){
				filePath = FilenameUtils.getFullPath(fileFullPath);
				compondFileName = FilenameUtils.getName(fileFullPath);
			}
			fileNameList.add(FilenameUtils.getName(fileFullPath));
		}
		compondFileName = compondFileName.substring(0,compondFileName.lastIndexOf("_")+1)  
				.concat(fileFullPaths.get(fileFullPaths.size()-1).split("_")[1])   //添加结束日期到文件名
				.concat(IClear2PayInfoGenerate.FILE_SEPARATOR)
				.concat(compondFileName.substring(compondFileName.lastIndexOf("_")+1));
		
		return new Object[]{filePath,fileNameList,compondFileName};
	}
	

	private boolean uploadLocalCompondFile(File tempDir, String compondResult, String[] remoteParams)
			throws CommunicationException, BizException {
		try {
			File localCompondFile = FileHelper.getFile(tempDir, compondResult);

			IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2],remoteParams[3]);

			IFtpTransferCallback uploadCallBack = new CommonWriteUploadCallBackImpl(remoteParams[0], localCompondFile);

			return ftpCallBackTemplate.processFtpCallBack(uploadCallBack);
		} catch (IOException ex) {
			throw new BizException("获取本地上传文件[" + compondResult + "]异常,原因[" + ex.getMessage() + "]");
		}
	}


	/**
	 * 
	 * <p>将本地文件上传并删除掉临时产生的文件夹</p>
	 * @param localClear2BankFileMap
	 * @return
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2011-6-16 下午09:04:07
	 * @See:
	 */
	@SuppressWarnings("unchecked")
	private boolean uploadClear2BankFileResult(Object[] localClear2BankFileResult,String[] remoteParams) throws CommunicationException, BizException {
		boolean result = false;

		File tempLocalDir = (File) localClear2BankFileResult[0];

		Map<String, File> localClear2BankFileMap = (Map<String, File>) (localClear2BankFileResult[1]);

		Map<String, Collection<String>> localClear2BankKeyMap = (Map<String, Collection<String>>) (localClear2BankFileResult[2]);

		try {
			for (Iterator<Map.Entry<String, File>> iterator = localClear2BankFileMap.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, File> entry = iterator.next();
				String bankNo = entry.getKey();
				String tmpBankNo = bankNo;
				tmpBankNo = tmpBankNo.split(FILENAME_SEPARATOR)[0];//为了复用此方法的操作,分割后最终得到机构号
				File localFile = entry.getValue();
				
				String uploadFileName = getUploadClear2PayBankFile(tmpBankNo, localFile,remoteParams);

				// 1.1 检查划付文件(对应一组产生的记录)
				if (CommonHelper.checkIsEmpty(uploadFileName)) {
					logger.warn("=======网银通针对银行ID[" + bankNo + "]划付文件上传未成功!=========");
					continue;
				}

				// 1.2 查找需进行更新的记录
				Collection<String> localClear2BankKeys = (Collection<String>) localClear2BankKeyMap.get(bankNo);
				result = updateClear2BankFileList(uploadFileName, localClear2BankKeys);
			}

		} finally {
			FileHelper.deleteFile(tempLocalDir);
		}

		return result;
	}
	
	private boolean uploadClear2BankFileSingleProductResult(String rmaDate, 
			Object[] localClear2BankFileResult) throws CommunicationException, BizException {
		boolean result = false;

		File tempLocalDir = (File) localClear2BankFileResult[0];

		File localClear2BankFile = (File) (localClear2BankFileResult[1]);

		try {
			String[] remoteParams = getRemoteParams();
			String uploadFileName = getUploadClear2PayBankFile(rmaDate, localClear2BankFile,remoteParams);

			// 1.1 检查划付文件(对应一组产生的记录)
			if (CommonHelper.checkIsEmpty(uploadFileName)) {
				logger.warn("=======网银通针对日期[" + localClear2BankFile + "]单机产品划付文件上传未成功!=========");

				return false;
			}

			Collection<String> localClear2BankKeys = (Collection<String>) (localClear2BankFileResult[2]);

			// 1.2 查找需进行更新的记录
			result = updateClear2BankSingleProductFileList(uploadFileName, localClear2BankKeys);

		} finally {
			FileHelper.deleteFile(tempLocalDir);
		}

		return result;
	}
	
	private boolean updateClear2BankSingleProductFileList(String uploadFileName, 
			Collection<String> localClear2BankKeys) throws BizException {
		List<CostRecord> updateClear2FileBankList = getUpdateCostRecordFileList(uploadFileName, localClear2BankKeys);

		if (CommonHelper.checkIsEmpty(updateClear2FileBankList)) {
			return false;
		}

		return this.costRecordDAO.updateCostRmaFileBatch(updateClear2FileBankList);
	}
	
	private List<CostRecord> getUpdateCostRecordFileList(String uploadFileName, 
			Collection<String> localClear2BankKeys) throws BizException {
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			logger.debug("localClear2BankKeys:" + localClear2BankKeys);
//			paraMap.put(RMA_MAPSEARCH_KEY, localClear2BankKeys);
			
			String rmaDate = "";
			String type = "";
			List<String> chlCodeList = new ArrayList<String>();
			for (String clear2BankKey : localClear2BankKeys) {
				rmaDate = StringUtils.substring(clear2BankKey, 0, 8);
				String chlCode = StringUtils.substring(clear2BankKey, 8, 16);
				chlCodeList.add(chlCode);
				type = StringUtils.substring(clear2BankKey, 16, 17);
			}
			paraMap.put("rmaDate", rmaDate);
			paraMap.put("chlCodeList", chlCodeList);
			paraMap.put("type", type);
		
			List<CostRecord> clearFileBankSearchList = this.costRecordDAO.findList(paraMap);
			logger.debug("CostRecord条数：" + clearFileBankSearchList.size());
		
			if (CommonHelper.checkIsEmpty(clearFileBankSearchList)) {
				return Collections.<CostRecord> emptyList();
			}
		
			return getUpdateCostRecordList(uploadFileName, clearFileBankSearchList);
		} catch (Exception ex) {
			throw new BizException("获得待更新的单机产品付款记录列表出现异常,原因[" + ex.getMessage() + "]");
		}
	}
	
	/**
	 * 
	 * <p>构造更新的LIST</p>
	 * @param uploadFileName
	 * @param clearFileBankSearchList
	 * @return
	 * @version: 2011-6-17 下午03:57:02
	 * @See:
	 */
	private static List<CostRecord> getUpdateCostRecordList(String uploadFileName, List<CostRecord> clearFileBankSearchList) {
		List<CostRecord> updateClear2FileBankList = new LinkedList<CostRecord>();

		for (CostRecord costRecord : clearFileBankSearchList) {
			costRecord.setFilePath(uploadFileName);

			updateClear2FileBankList.add(costRecord);
		}

		return updateClear2FileBankList;
	}


	/**
	 * 
	 * <p>更新MerchTransRemitAccount表</p>
	 * @param uploadFileName
	 * @param localClear2BankKeys
	 * @return
	 * @throws BizException
	 * @version: 2011-6-17 下午03:25:22
	 * @See:
	 */
	private boolean updateClear2BankFileList(String uploadFileName, Collection<String> localClear2BankKeys) throws BizException {
		List<MerchTransRma> updateClear2FileBankList = getUpdateClear2BankFileList(uploadFileName, localClear2BankKeys);

		if (CommonHelper.checkIsEmpty(updateClear2FileBankList)) {
			return false;
		}

		return this.merhTransRmaDAO.updateMerchTrans(updateClear2FileBankList);
	}

	private List<MerchTransRma> getUpdateClear2BankFileList(String uploadFileName, Collection<String> localClear2BankKeys)
			throws BizException {
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put(RMA_MAPSEARCH_KEY, localClear2BankKeys);

			List<MerchTransRma> clearFileBankSearchList = this.merhTransRmaDAO.findMerchTransRma(paraMap);

			if (CommonHelper.checkIsEmpty(clearFileBankSearchList)) {
				return Collections.<MerchTransRma> emptyList();
			}

			return getUpdateClear2FileBankList(uploadFileName, clearFileBankSearchList);
		} catch (Exception ex) {
			throw new BizException("获得待更新的划账记录列表出现异常,原因[" + ex.getMessage() + "]");
		}

	}

	/**
	 * 
	 * <p>构造更新的LIST</p>
	 * @param uploadFileName
	 * @param clearFileBankSearchList
	 * @return
	 * @version: 2011-6-17 下午03:57:02
	 * @See:
	 */
	private static List<MerchTransRma> getUpdateClear2FileBankList(String uploadFileName, List<MerchTransRma> clearFileBankSearchList) {
		List<MerchTransRma> updateClear2FileBankList = new LinkedList<MerchTransRma>();

		for (MerchTransRma remitAccount : clearFileBankSearchList) {
			remitAccount.setFilePath(uploadFileName);

			updateClear2FileBankList.add(remitAccount);
		}

		return updateClear2FileBankList;
	}

	/**
	 * 
	 * <p>进行文件的上传处理</p>
	 * @param bankNo
	 * @param uploadFile
	 * @return
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2011-6-16 下午09:03:54
	 * @See:
	 */
	private String getUploadClear2PayBankFile(String bankNo, File uploadFile, String[] remoteParams) throws CommunicationException, BizException {
//		String[] remoteParams = getRemoteParams();

		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		String remotePath = getUploadClear2BankFilePath(remoteParams[0], bankNo);

		// 构造面向uploadFile的回调处理类
		IFtpTransferCallback uploadCallBack = new CommonTextUploadCallBackImpl(remotePath, uploadFile);

		// 处理文件上传
		boolean flag = ftpCallBackTemplate.processFtpCallBack(uploadCallBack);

		if (flag) {
			String fileName = FileHelper.getFullFileName(uploadFile);
			logger.info("网银通文件上传处理成功,上传文件名===[{}]", fileName);


			return getUploadClear2PayBankFile(remotePath, fileName);
		}

		return "";
	}

	private static String getUploadClear2PayBankFile(String remotePath, String fileName) {
		return remotePath.endsWith(FILE_SEPARATOR) ?
				 new StringBuilder(remotePath).append(fileName).toString()
		       : new StringBuilder(remotePath).append(FILE_SEPARATOR).append(fileName).toString();
	}

	/**
	 * 
	 * <p>上传文件路径</p>
	 * @param remotePath
	 * @param bankNo
	 * @return
	 * @version: 2011-6-16 下午09:00:10
	 * @See:
	 */
	private static String getUploadClear2BankFilePath(String remotePath, String bankNo) {
		return new StringBuilder(remotePath).append(FILE_SEPARATOR).append(bankNo).toString();
	}

	/**
	 * 
	 * <p>构造网银通上传文件查询参数</p>
	 * @return
	 * @throws BizException
	 * @version: 2010-12-9 下午12:45:15
	 * @See:
	 */
	private String[] getRemoteParams() throws BizException {
		try {
			SysparamCache paraMgr = SysparamCache.getInstance();
			return new String[] { paraMgr.getClear2PathBankSavePath(), paraMgr.getParamValue(Constants.WYT_REMOTE_SERVER_CODE),
					               paraMgr.getParamValue(Constants.WYT_REMOTE_USER_CODE), paraMgr.getParamValue(Constants.WYT_REMOTE_PWD_CODE)
			};
		} catch (Exception ex) {
			throw new BizException("获取参数表指定参数异常,原因[" + ex.getMessage() + "]");
		}
	}
	
	/**
	 * 
	 * <p>构造网银通文件(发卡机构划付)参数</p>
	 * @return
	 * @throws BizException
	 * @version: 2010-12-9 下午12:45:15
	 * @See:
	 */
	private String[] getRemoteBranchParams() throws BizException {
		try {
			SysparamCache paraMgr = SysparamCache.getInstance();
			return new String[] { paraMgr.getClear2PathBankBranchSavePath(), paraMgr.getParamValue(Constants.WYT_REMOTE_SERVER_CODE),
					               paraMgr.getParamValue(Constants.WYT_REMOTE_USER_CODE), paraMgr.getParamValue(Constants.WYT_REMOTE_PWD_CODE)
			};
		} catch (Exception ex) {
			throw new BizException("获取参数表指定参数异常,原因[" + ex.getMessage() + "]");
		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------------
	protected static interface IClear2PayFileCompondProcess {
		String getClear2PayFileCompondResult(File tempDir, String arrangDateFileName, List<Object[]> fileArrayList) throws BizException;
	}

	protected static class Clear2PayFileCompondProcessImpl implements IClear2PayFileCompondProcess {
		private static final int STATIS_DESCRIBE_INDEX = 0;

		private static final int STATIS_RESULT_INDEX = 1;

		private static final int PROPERTY_DESCRIBE_INDEX = 2;
		
		private static final int STATIS_CHECK_LENGTH = 2;		

		public String getClear2PayFileCompondResult(File tempDir, String arrangDateFileName, List<Object[]> fileArrayList)
				throws BizException {
			OutputStream localOutput = null;
			
			try {
				localOutput = FileHelper.getBufferedOutputStream(tempDir, arrangDateFileName);

				List<Object[]> filterContentsList = new LinkedList<Object[]>();

				for (Object[] fileArray : fileArrayList) {

					File file = (File) fileArray[1];

					List<String> fileContents = FileHelper.readLines(file, IClear2PayInfoGenerate.FILE_ENCODING);

					Object[] filterContents = getFilterFileContents(fileContents);

					if (CommonHelper.checkIsEmpty(filterContents)) {
						continue;
					}

					filterContentsList.add(filterContents);
				}

				return getClear2PayFileCompondResult(localOutput, filterContentsList, arrangDateFileName);
			} catch (Exception ex) {
				logger.error("文件合并处理异常", ex);
				throw new BizException("文件合并处理异常,原因[" + ex.getMessage() + "]");
			} finally {
				FileHelper.closeOutputStream(localOutput);
			}
		}

		/**
		 * 
		 * <p>将获取的信息进行过滤合并并写入到本地文件中</p>
		 * @param localOutput
		 * @param filterContentsList
		 * @return
		 * @throws Exception
		 * @version: 2011-7-8 下午05:15:47
		 * @See:
		 */
		private String getClear2PayFileCompondResult(OutputStream localOutput, List<Object[]> filterContentsList,
				String arrangDateFileName) throws Exception {
			Collection<String> compondContents = getCompondContents(filterContentsList);

			if (CommonHelper.checkIsEmpty(compondContents)) {
				return "";
			}

			boolean persist = FileHelper.persistFile(localOutput, IClear2PayInfoGenerate.FILE_ENCODING, compondContents);

			if (persist) {
				return arrangDateFileName;
			}

			return "";
		}

		@SuppressWarnings("unchecked")
		private Collection<String> getCompondContents(List<Object[]> filterContentsList) {
			if (CommonHelper.checkIsEmpty(filterContentsList)) {
				return Collections.<String> emptyList();
			}

			List<String> rowContents = new LinkedList<String>();

			String statisDescribe = null;

			String propertyDescribe = null;

			AtomicInteger statisCount = new AtomicInteger(0);

			BigDecimal statisDecimal = BigDecimal.ZERO;

			for (Object[] filterContents : filterContentsList) {
				if (statisDescribe == null) {
					statisDescribe = (String) filterContents[0];
				}

				if (propertyDescribe == null) {
					propertyDescribe = (String) filterContents[2];
				}

				String statisResult = (String) filterContents[1];

				String[] statisArray = statisResult.split("\\" + IClear2PayInfoGenerate.FILE_CONNECT);
				
				if(checkInValidStatisArray(statisArray)) {
					continue;
				}

				String count = statisArray[0];

				if (CommonHelper.checkIsNotEmpty(count)) {
					statisCount.getAndAdd(CommonHelper.str2Int(count));
				}

				String decimal = statisArray[1];

				if (CommonHelper.checkIsNotEmpty(decimal)) {
					statisDecimal = statisDecimal.add(CommonHelper.getDecimalFromStr(decimal));
				}

				List<String> rowList = (List<String>) filterContents[3];
				rowContents.addAll(rowList);
			}
			
			Long seqNo = 1L;//用于修正序号
			List<String> tmpRowContents = new LinkedList<String>();
			int start;
			for(String row: rowContents){
				start = row.indexOf("|");
				if(start >0){
					row = new StringBuffer(seqNo.toString()).append(row.substring(start)).toString();
					tmpRowContents.add(row);
					seqNo++;
				}
			}
			rowContents = tmpRowContents;

			return getCompondContents(statisDescribe,statisCount.get(),statisDecimal,propertyDescribe,rowContents);
		}
		
		/**
		 * 
		  * <p>构造合并文件内容</p>
		  * @param statisDescribe
		  * @param statisCount
		  * @param statisDecimal
		  * @param propertyDescribe
		  * @param rowContents
		  * @return  
		  * @version: 2011-7-10 上午09:40:59
		  * @See:
		 */
		private Collection<String> getCompondContents(String statisDescribe, int statisCount, BigDecimal statisDecimal,
				String propertyDescribe, List<String> rowContents) {
			List<String> compondContents = new LinkedList<String>();
			
			String statis = new StringBuilder().append(statisCount).append(IClear2PayInfoGenerate.FILE_CONNECT)
			                                   .append(CommonHelper.getCommonFormatAmt(statisDecimal)).toString();

			compondContents.add(statisDescribe);
			compondContents.add(statis);
			compondContents.add(propertyDescribe);
			compondContents.addAll(rowContents);
			
			return compondContents;
		}
		
		/**
		 * 
		  * <p>检查统计行的部分(防止空指针以及indexOutBoundException)</p>
		  * @param statisArray
		  * @return  
		  * @version: 2011-7-10 上午09:36:08
		  * @See:
		 */
		private boolean checkInValidStatisArray(String[] statisArray) {
			return (statisArray == null) || (statisArray.length < STATIS_CHECK_LENGTH);
		}

		private Object[] getFilterFileContents(List<String> fileContents) {
			if (CommonHelper.checkIsEmpty(fileContents)) {
				return new Object[] {};
			}

			String statisDescribe = fileContents.get(STATIS_DESCRIBE_INDEX);

			String statisResult = fileContents.get(STATIS_RESULT_INDEX);

			String propertyDescribe = fileContents.get(PROPERTY_DESCRIBE_INDEX);

			String[] removedArray = new String[] { statisDescribe, statisResult, propertyDescribe };

		    return new Object[] { statisDescribe, statisResult, propertyDescribe, getFilterContents(fileContents,removedArray) };
		}

		/**
		 * 
		  * <p>获得过滤的数据行</p>
		  * @param fileContents
		  * @param removedArray
		  * @return  
		  * @version: 2011-7-8 下午06:23:13
		  * @See:
		 */
		private List<String> getFilterContents(List<String> fileContents, String[] removedArray) {
			int size = removedArray.length;
			
			synchronized (fileContents) {
				int count = 0;
				for (Iterator<String> iterator = fileContents.iterator(); iterator.hasNext();) {
					String contents = iterator.next();

					if (CommonHelper.containsElement(removedArray, contents)) {
						iterator.remove();
						count ++ ;
						
						if(count == size) {
							break;
						}
					}

				}
			}

			return fileContents;
		}
	}

}
