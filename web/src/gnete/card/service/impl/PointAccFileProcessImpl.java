package gnete.card.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import flink.event.FlinkAppEvent;
import flink.event.IFlinkAppEventCreate;
import flink.event.impl.FlinkAppEventPublisher;
import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.schedule.TaskException;
import flink.util.CommonHelper;
import flink.util.ITaskCall;
import flink.util.TaskCallHelper;
import gnete.card.entity.PointAccReg;
import gnete.card.entity.SysLog;
import gnete.card.entity.state.SysLogViewState;
import gnete.card.entity.type.PointAccTransYype;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;
import gnete.card.file.BrandChgAccFileResolveImpl;
import gnete.card.file.CardIdChgAccFileResovleImpl;
import gnete.card.file.CardStatusChgAccFileResoleImpl;
import gnete.card.file.IPointAccFileResolve;
import gnete.card.file.PointPresentAccFileResoleImpl;
import gnete.card.service.IPointAccFileFetch;
import gnete.card.service.LogService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @Project: Card
 * @File: PointAccFileProcessImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified: aps-lib
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Service("pointAccFileProcess")
public class PointAccFileProcessImpl implements gnete.card.service.IPointAccFileProcess, InitializingBean {
	private static final Log log = LogFactory.getLog(PointAccFileProcessImpl.class);

	private final ITaskCall<PointAccReg> pointAccRegTask = new TaskCallHelper<PointAccReg>();

	private final Map<String, IPointAccFileResolve> tradeAccFileResolveMap = new ConcurrentHashMap<String, IPointAccFileResolve>();

	@Autowired
	private LogService logService;

	@Autowired
	@Qualifier("msgRegAppEventCreate")
	private IFlinkAppEventCreate<List<PointAccReg>> msgRegAppEventCreate;

	public List<PointAccReg> getPointAccRegList(String branchCode, String date) throws BizException {

		String[] remoteParams = getRemoteParams();

		// 1.1 构造模板类及其回调函数
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		String remotePath = getRemotePath(remoteParams[0], branchCode, date);
		FileContentResolveCallBackImpl fileContentCallBack = new FileContentResolveCallBackImpl(remotePath, branchCode, date,
				new PointAccFileFetchImpl());

		// 1.2 进行文件下载处理
		boolean result = ftpCallBackTemplate.processFtpCallBack(fileContentCallBack);

		if (result) {
			// 1.3 下载成功后进行文件解析处理
			return getPointAccRegList(fileContentCallBack.getTradeFileStream(), remotePath, branchCode, date);
		}

		return Collections.<PointAccReg> emptyList();
	}

	@SuppressWarnings("unchecked")
	private List<PointAccReg> getPointAccRegList(Map dateFileStreamMap, String remotePath, String branchCode, String date)
			throws BizException {
		List<PointAccReg> pointAccRegList = new ArrayList<PointAccReg>();
		List<SysLog> sysLogList = new ArrayList<SysLog>();
		
		for(Iterator iterator = dateFileStreamMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<Object,List<Object[]>> entry = (Map.Entry<Object,List<Object[]>>)iterator.next();
			Object trade = entry.getKey();
			IPointAccFileResolve tradePointAccFileResolve = tradeAccFileResolveMap.get(trade.toString());

			if (tradePointAccFileResolve == null) {
				continue;
			}
			
			List<Object[]> fileNameStreamList =  entry.getValue();
			
			// 1.1 得到结果以及错误日志
			Object[] pointAccRegResultArray = getPointAccRegList(remotePath, fileNameStreamList, branchCode,
					tradePointAccFileResolve);

			List<PointAccReg> _pointAccRegList = (List<PointAccReg>) pointAccRegResultArray[0];
			List<SysLog> _sysLogList = (List<SysLog>) pointAccRegResultArray[1];

			// 1.2 对结果进行保存
			if (CommonHelper.checkIsNotEmpty(_pointAccRegList)) {
				pointAccRegList.addAll(_pointAccRegList);
			}

			if (CommonHelper.checkIsNotEmpty(_sysLogList)) {
				sysLogList.addAll(_sysLogList);
			}
			
		}

		// 对可能存在的异常日志进行记录
		checkPointAccRegResult(sysLogList, branchCode, date, dateFileStreamMap.keySet().size());

		return pointAccRegList;
	}

	/**
	 * 
	 * <p>对返回数据和涉及日志进行检查处理</p>
	 * @param pointAccRegList
	 * @param sysLogList
	 * @throws BizException
	 * @version: 2011-4-15 下午12:50:06
	 * @See:
	 */
	private void checkPointAccRegResult(List<SysLog> sysLogList, String branchCode, String date, int size) {
		if (CommonHelper.checkIsEmpty(sysLogList)) {
			return;
		}

		try {
			if (size == sysLogList.size()) {
				throw new BizException("文件解析均存在异常,机构编号[" + branchCode + "],目录日期[" + date + "]");
			}
		} catch (BizException e) {
			log.error(e.getMessage(), e);
		}
		finally {
//			this.logService.addSysLog(sysLogList);
			try {
				this.logService.addSysLog(sysLogList);
			} catch (BizException e) {
				log.error("记录系统日志发生异常。" + e.getMessage(), e);
			}
		}
	}
	
	private Object[] getPointAccRegList(final String remotePath, List<Object[]> fileNameStreamList, final String branchCode,
			final IPointAccFileResolve tradePointAccFileResolve) {
		List<PointAccReg> pointAccRegList = new ArrayList<PointAccReg>();
		List<SysLog> sysLogList = new ArrayList<SysLog>();
		for (final Object[] fileNameStream : fileNameStreamList) {
			SysLog sysLog = null;
			try {
				PointAccReg pointAccReg = pointAccRegTask.getFromCallable(new Callable<PointAccReg>() {

					public PointAccReg call() throws Exception {
						InputStream fileStream = (InputStream) fileNameStream[1];
						return tradePointAccFileResolve.getPointAccRegResolve(getFileName(), branchCode, fileStream);
					}

					/**
					 * 
					 * <p>给传入的文件名增加绝对路径,跟传入的时间保持一致</p>
					 * @return
					 * @version: 2011-4-15 下午05:21:08
					 * @See:
					 */
					private String getFileName() {
						String fileName = (String) fileNameStream[0];
						return remotePath.endsWith(FILE_SEPARATOR) ? 
								new StringBuilder(remotePath).append(fileName).toString()
							  : new StringBuilder(remotePath).append(FILE_SEPARATOR).append(fileName).toString();
					}
				});

				if (pointAccReg != null) {
					pointAccRegList.add(pointAccReg);
				}
			} catch (TaskException ex) {
				// 记录错误日志
				log.error("文件[" + fileNameStream[0].toString() + "]解析异常", ex);
				String errMsg = "文件[" + fileNameStream[0].toString() + "]解析异常,原因[" + ex.getMessage() + "]";
				sysLog = getPointAccSysLog(errMsg, branchCode);
			} finally {
				if (sysLog != null) {
					sysLogList.add(sysLog);
				}
			}
		}

		return new Object[] { pointAccRegList, sysLogList };
	}

	private SysLog getPointAccSysLog(String errMsg, String branchCode) {
		SysLog sysLog = new SysLog();
		sysLog.setBranchNo(branchCode);
		sysLog.setLogClass(SysLogClass.INFO.getValue());
		sysLog.setLogDate(CommonHelper.getCalendarDate());
		sysLog.setContent(errMsg);
		sysLog.setLogType(SysLogType.ERROR.getValue());
		sysLog.setState(SysLogViewState.UN_READ.getValue());
		return sysLog;
	}

	/**
	 * 
	 * <p>根据机构、日期以及根路径得到要访问的目录</p>
	 * @param parentPath
	 * @param branchCode
	 * @param date
	 * @return
	 * @version: 2011-4-14 下午06:45:10
	 * @See:
	 */
	private String getRemotePath(String parentPath, String branchCode, String date) {
		return (parentPath.endsWith(FILE_SEPARATOR)) ? 
				new StringBuilder(50).append(parentPath).append(branchCode).append(FILE_SEPARATOR).append(date).toString()
			  : new StringBuilder(50).append(parentPath).append(FILE_SEPARATOR).append(branchCode).append(FILE_SEPARATOR).append(date).toString();
	}

	/**
	 * <p>容器加载以后引入各个交易别名的文件处理类</p>
	 */
	public void afterPropertiesSet() throws Exception {
		tradeAccFileResolveMap.put(PointAccTransYype.POINT_PRESENT.getValue(), new PointPresentAccFileResoleImpl());
		tradeAccFileResolveMap.put(PointAccTransYype.CARD_STATUS_CHG.getValue(), new CardStatusChgAccFileResoleImpl());
		tradeAccFileResolveMap.put(PointAccTransYype.CARD_ID_CHG.getValue(), new CardIdChgAccFileResovleImpl());
		tradeAccFileResolveMap.put(PointAccTransYype.BRAND_CHG.getValue(), new BrandChgAccFileResolveImpl());
	}

	/**
	 * 
	 * <p>从系统参数表中得到跟FTP关联的参数</p>
	 * @return
	 * @throws BizException
	 * @version: 2010-12-9 下午12:45:15
	 * @See:
	 */
	public String[] getRemoteParams() throws BizException {
		try {
			SysparamCache paraMgr = SysparamCache.getInstance();
			return new String[] { paraMgr.getParamValue(Constants.ENT_JF_PARENTPATH), paraMgr.getFtpServerIP(),
					                         paraMgr.getFtpServerUser(), paraMgr.getFtpServerPwd()
			};
		} catch (Exception ex) {
			throw new BizException("获取参数表指定参数异常,原因[" + ex.getMessage() + "]");
		}
	}

	/**
	 * <p>内部处理类包装回调函数处理类</p>
	 * @Project: Card
	 * @File: FileContentResolveCallBackImpl.java
	 * @See:	 
	 * @author: aps-zbw
	 * @modified:
	 * @Email: aps-zbw@cnaps.com.cn
	 * @Date: 2011-4-14
	 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
	 * @version: V1.0
	 */
	protected static class FileContentResolveCallBackImpl implements IFtpTransferCallback {

		private final AtomicReference<Map> tradeFileStreamsRefer = new AtomicReference<Map>();

		private final String remotePath;

		private final String branchCode;

		private final String date;

		protected final IPointAccFileFetch pointAccFileFetch;

		protected FileContentResolveCallBackImpl(String remotePath, String branchCode, String date,
				IPointAccFileFetch pointAccFileFetch) {
			this.remotePath = remotePath;
			this.branchCode = branchCode;
			this.date = date;
			this.pointAccFileFetch = pointAccFileFetch;
		}

		public boolean doTransfer(FTPClient ftpClient) throws CommunicationException {
			Date parseDate = getFormatDate(this.date);
			Date yesterDate = CommonHelper.getYesterDayDate();
			/*String preWorkDate = ParaMgr.getInstance().getPreWorkDate();
			Date yesterDate = DateUtil.formatDate(preWorkDate, "yyyyMMdd");*/

			// 检查传入的日期是否是昨天
			if (CommonHelper.compareDate(parseDate, yesterDate)) {
				try {
					// 1.1 尝试切换到昨天目录
					boolean chgYesterPath = ftpClient.changeWorkingDirectory(this.remotePath);

					if (!chgYesterPath) {
						// 1.2 如果没有切换成功则创建昨天目录
						/*boolean makeYesterDayPath = ftpClient.makeDirectory(this.remotePath);
						if (makeYesterDayPath) {
							log.info("====服务端针对昨天目录[" + this.remotePath + "]创建成功!======");
						}*/
					} else {
						//ftpClient.makeDirectory(getDayFile(1, this.remotePath));
					    
						// 1.3 如果切换成功则进行文件提取处理
						return ftpProcess(ftpClient);
					}
				} catch (IOException ex) {
					throw new CommunicationException("服务端目录切换处理失败,原因[" + ex.getMessage() + "]");
				}
			}

			return false;
		}

		private boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {

			try {
				Map dateFileStreamMap = pointAccFileFetch.getPointAccFileStream(ftpClient, this.date);

				tradeFileStreamsRefer.set(dateFileStreamMap);
			} catch (IOException ex) {
				throw new CommunicationException("文件处理发生异常,机构[" + branchCode + "],日期[" + this.date + "],原因[" + ex.getMessage()
						+ "]");
			} catch (RuntimeBizException ex) {
				throw new CommunicationException(ex);
			}
			return true;
		}

		public Map getTradeFileStream() {
			return tradeFileStreamsRefer.get();
		}

		private Date getFormatDate(String date) {
			try {
				return CommonHelper.getCommonParseDate(date);
			} catch (ParseException ex) {
				throw new CommunicationException(ex);
			}
		}
		
		/**
		 * 获得remotePath的前一天和后一天的路径
		 * @param num
		 * @param remotePath
		 * @return
		 */
		/*private String getDayFile(int num, String remotePath){
			
			String[] splitPath = remotePath.split("/");
			
			if(splitPath.length != 0){
				if(DateUtil.chkDateFormat(splitPath[splitPath.length-1])){
					String date = DateUtil.addDays(splitPath[splitPath.length-1], num, "yyyyMMdd");
					String preFix = remotePath.substring(0, remotePath.length()-8);
						
					return preFix.concat(date);
				}
			}
			return null;
		}*/
		
	}
	
	
	/**
	  * <p>将原来容器注入的改为内部类</p>
	  * @Project: Card
	  * @File: PointAccFileProcessImpl.java
	  * @See:	 
	  * @author: aps-zbw
	  * @modified:
	  * @Email: aps-zbw@cnaps.com.cn
	  * @Date: 2011-4-21
	  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
	  * @version:  V1.0
	 */
	protected static class PointAccFileFetchImpl extends AbstractPointAccFileFetchImpl {
		@Override
		protected String[] getMaxSeqArray() {
			return CommonHelper.getFormatMaxArray(super.MAX_SEQ);
		}

		@Override
		protected String[] getCheckedPointAccFile(FTPFile pathFile, String checkedPrefix) {
			return super.getCheckedPointAccFileDefault(pathFile, checkedPrefix);
		}

		@Override
		protected long getMaxTxtFileSize() {
			return super.TXT_MAX_SIZE;
		}

		@Override
		protected long getMaxZipFileSize() {
			return super.ZIP_MAX_SIZE;
		}
	}

	// ---------------------------------------------------触发登记簿对应命令表的处理--------------------------------------------------------------------
	public void processPointAccRegList(String branchCode, String date) throws BizException {
		// 1.1 获得待处理的信息
		List<PointAccReg> pointAccRegList = getPointAccRegList(branchCode, date);
		if (CommonHelper.checkIsEmpty(pointAccRegList)) {
			return;
		}

		// 1.3 触发命令表写入处理
		FlinkAppEvent waitsInfoApp = msgRegAppEventCreate.getFlinkAppEvent(pointAccRegList, CommonHelper.createDefaultMap());
		new FlinkAppEventPublisher(waitsInfoApp).publishFlinkEvent();
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------

	public String[] getFileList() throws BizException {
		
		String[] remoteParams = getRemoteParams();
		// 1.1 构造模板类及其回调函数
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);
		FileListCallBackImpl fileContentCallBack = new FileListCallBackImpl(remoteParams[0]);
		// 1.2 进行文件下载处理
		boolean result = ftpCallBackTemplate.processFtpCallBack(fileContentCallBack);
		if (result) {
			// 1.3 下载成功后进行文件解析处理,取得指定目录下的文件列表
			return fileContentCallBack.getFileListRefer();
		}
		return null;
	}
	
	/**
	 * <p>内部处理类包装回调函数处理类</p>
	 * @Project: Card
	 * @File: FileListCallBackImpl.java
	 * @See:	 
	 * @author: aps-lib
	 * @modified:
	 * @Date: 2011-4-19
	 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
	 * @version: V1.0
	 */
	protected static class FileListCallBackImpl implements IFtpTransferCallback {

		private final AtomicReference<String[]> fileListRefer = new AtomicReference<String[]>();

		private final String remotePath;

		protected FileListCallBackImpl(String remotePath) {
			this.remotePath = remotePath;
		}

		public boolean doTransfer(FTPClient ftpClient) throws CommunicationException {

			try {
				// 1.1 尝试切换到指定目录
				boolean chgRemotePath = ftpClient.changeWorkingDirectory(this.remotePath);

				if (!chgRemotePath) {
					// 1.2 如果没有切换成功
					log.info("目录[" + this.remotePath + "]不存在。");
				} else {
					// 1.3 如果切换成功则进行处理
					return ftpProcess(ftpClient);
				}
			} catch (IOException ex) {
				throw new CommunicationException("服务端目录切换处理失败,原因["
						+ ex.getMessage() + "]");
			}

			return false;
		}

		private boolean ftpProcess(FTPClient ftpClient) throws CommunicationException {

			try {
				String[] remoteFileList = ftpClient.listNames(this.remotePath);
				int length = this.remotePath.length();
				String path = "";
				
				for(int i = 0; i < remoteFileList.length; i++){
					path = remoteFileList[i];
					path = path.substring(length + 1, path.length());
					remoteFileList[i] = path;
				}
				
				fileListRefer.set(remoteFileList);
			} catch (IOException ex) {
				throw new CommunicationException("文件处理发生异常,原因[" + ex.getMessage()
						+ "]");
			} catch (RuntimeBizException ex) {
				throw new CommunicationException(ex);
			}
			return true;
		}

		public String[] getFileListRefer() {
			return fileListRefer.get();
		}

	}
}
