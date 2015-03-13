package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonNameFilesCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.CommonHelper;
import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.SysLogDAO;
import gnete.card.dao.UserLogDAO;
import gnete.card.entity.BusiReport;
import gnete.card.entity.SysLog;
import gnete.card.entity.UserLog;
import gnete.card.entity.state.SysLogViewState;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;
import gnete.card.service.LogService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceImpl implements LogService {
	
	@Autowired
	private UserLogDAO userLogDAO;
	
	@Autowired
	private SysLogDAO sysLogDAO;
	
	static final Logger logger = Logger.getLogger(LogServiceImpl.class);
	
	private static final String[] prefix = new String[] {};
	private static final String[] suffix = new String[] { ".log" };

	public void addUserLog(UserLog userLog) throws BizException {
		//检查数据
		Assert.notEmpty(userLog.getUserId(), "所属用户编号不能为空");
//		Assert.notEmpty(userLog.getLimitId(), "所属模块编号不能为空");
		Assert.notEmpty(userLog.getLogType(), "日志类型不能为空");
		Assert.notEmpty(userLog.getContent(), "日志内容不能为空");
		
		if (userLog.getContent().length() >= 512) {
			userLog.setContent(StringUtils.substring(userLog.getContent(), 0, 512));
		}
		
		userLog.setLogDate(new Date());
		this.userLogDAO.insert(userLog);
	}
	
	public void readSysLog(Long sysLogId, String sessionUser)
			throws BizException {
		//检查数据
		Assert.notNull(sysLogId, "系统日志编号不能为空");
		Assert.notEmpty(sessionUser, "当前用户不能为空");
		
		SysLog sysLog = (SysLog) this.sysLogDAO.findByPk(sysLogId);
		
		sysLog.setState(SysLogViewState.READ.getValue());
		sysLog.setViewUser(sessionUser);
		sysLog.setViewDate(new Date());
		
		this.sysLogDAO.update(sysLog);
	}
	
	public void addSyslog(SysLog sysLog) throws BizException {
		Assert.notNull(sysLog, "系统日志不能为空");
		
		try {
			this.sysLogDAO.insert(sysLog);
		} catch (DataAccessException ex) {
			logger.error("系统日志记录异常", ex);
		}
	}
	
	public boolean addSysLog(List<SysLog> sysLogList) throws BizException {
		Assert.notNull(sysLogList, "系统日志不能为空");
		
		try {
			this.sysLogDAO.insertBatch(sysLogList);
		}catch(DataAccessException ex) {
			logger.error("系统日志记录异常", ex);
		}
		
		return true;
	}
	
	@Override
	public void insertSysLog(String branchNo, String merchantNo, String msg, SysLogType sysLogType,
			SysLogClass logClass) {
		SysLog syslog = new SysLog();
		syslog.setBranchNo(branchNo);
		syslog.setMerchantNo(merchantNo);
		if (msg.length() > 512) {
			syslog.setContent(msg.substring(0, 512));
		} else {
			syslog.setContent(msg);
		}
		syslog.setLogDate(new Date());
		syslog.setLogType(sysLogType.getValue());
		syslog.setState(SysLogViewState.UN_READ.getValue());
		syslog.setLogClass(logClass.getValue());

		try {
			this.sysLogDAO.insert(syslog);
		} catch (DataAccessException ex) {
			logger.error("插入系统日志异常", ex);
		}
	}

	public void readSettSysLog(Long sysLogId, String sessionUser)
			throws BizException {
		//检查数据
		Assert.notNull(sysLogId, "系统日志编号不能为空");
		Assert.notEmpty(sessionUser, "当前用户不能为空");
		
		SysLog sysLog = (SysLog) this.sysLogDAO.findByPkFromJFLink(sysLogId);
		
		sysLog.setState(SysLogViewState.READ.getValue());
		sysLog.setViewUser(sessionUser);
		sysLog.setViewDate(new Date());
		
		this.sysLogDAO.updateJFLink(sysLog);
		
	}

	public Paginater getBackLogFileList(Map<String, Object> params, int pageNumber,
			int pageSize, int bgType) throws BizException {
		
		SysparamCache mgr = SysparamCache.getInstance();
		// 默认后台1的日志
		String ftpServer = mgr.getBg1LogFtpIp();
		String user = mgr.getBg1LogFtpUser();
		String pwd = mgr.getBg1LogFtpPassword();
		if (2 == bgType) { // 后台2
			ftpServer = mgr.getBg2LogFtpIp();
			user = mgr.getBg2LogFtpUser();
			pwd = mgr.getBg2LogFtpPassword();
		}
		
		//生成的后台日志文件保存路径
		String rmaFileSavePath = mgr.getBgLogFileRemotePath(); 

		String fileName = (String) params.get("fileName");
		String fileDate = (String) params.get("fileDate");
		
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		List<String> filterFileList = new ArrayList<String>();
			
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);

		CommonNameFilesCallBackImpl callBack = new CommonNameFilesCallBackImpl(rmaFileSavePath, prefix, suffix, false);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);

		List<String> fileList = new ArrayList<String>();
		
		if (flag) {
			fileList = callBack.getNameFilesRefer();
		}
		
		// 过滤文件列表，筛选出符合条件（ fileName, fileDate）的文件列表
		filterFileList = getFileListResult(fileList, fileDate, fileName);
			
		// 把文件列表转化为BusiReport列表
		busiReportList = getBusiReportList(filterFileList);

		return CommonHelper.getListPage(busiReportList, pageNumber, pageSize);

	}
	
	/**
	 * 对后台日志文件过滤
	 * 根据查询条件返回新的list
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<String> getFileListResult(List<String> oldList, String... params) {
		
		if (ArrayUtils.isEmpty(params)) {
			return oldList;
		}
		
		List<String> list = new ArrayList<String>();
		boolean flag;
		
		for (String path : oldList) {
			flag = true;
			path = path.replace("\\", "/");
			String name = path.substring(path.lastIndexOf("/") + 1, path.length()); // 获得文件名
			name = name.split("\\.")[0]; // 去掉后缀
			
			if (StringUtils.isBlank(name)) {
				continue;
			}
			
			// 检查文件名是否包含日期
			String fileDate = params[0];
			if(StringUtils.isNotEmpty(fileDate)){
				flag = name.contains(fileDate) ? true : false;
			}
			
			// 检查文件名是否包含日期
			String fileName = params[1];
			if(flag && StringUtils.isNotEmpty(fileName)){
				fileName = fileName.split("\\.")[0];  // 去掉后缀
				flag = name.contains(fileName) ? true : false;
			}
			
			if(flag){
				list.add(path);
			}
			
		}
		return list;
	}

	/**
	 * 把文件路径列表转化为BusiReport实体
	 * 
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<BusiReport> getBusiReportList(List<String> fileList) {
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		
		for (String path : fileList) {
			path = path.replace("\\", "/");
			BusiReport busiReport = new BusiReport();

			busiReport.setFilePath(path);
			String name = StringUtils.substringAfterLast(path, "/");
			busiReport.setReportName(name);
			name = name.split("\\.")[0]; // 去掉后缀
			
			busiReportList.add(busiReport);
		}
		
		return busiReportList;
	}

	public boolean downloadBackLogFile(String fullPath, int bgType) throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		// 默认后台1的日志
		String ftpServer = mgr.getBg1LogFtpIp();
		String user = mgr.getBg1LogFtpUser();
		String pwd = mgr.getBg1LogFtpPassword();
		if (2 == bgType) { // 后台2
			ftpServer = mgr.getBg2LogFtpIp();
			user = mgr.getBg2LogFtpUser();
			pwd = mgr.getBg2LogFtpPassword();
		}
		String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1); // 文件名
		String ftpPath = fullPath.substring(0, fullPath.indexOf(fileName) - 1); // 文件路径
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);
		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不到后台日志文件[" + fileName + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取后台日志文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.warn(msg, ex);
			throw new BizException(msg);
		}
		return result;
	}
}
